package ru.yandex.practicum.taskmanager.manager;

import ru.yandex.practicum.taskmanager.exceptions.ManagerSaveException;
import ru.yandex.practicum.taskmanager.exceptions.ValidationTimeException;
import ru.yandex.practicum.taskmanager.model.*;

import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    Comparator<Task> comparator = new Comparator<Task>() {
        @Override
        public int compare(Task o1, Task o2) {
            return o1.getStartTime().compareTo(o2.getStartTime());
        }
    };

    public Map<Integer, Task> tasks = new HashMap();
    public Map<Integer, Subtask> subTasks = new HashMap();
    public Map<Integer, Epic> epics = new HashMap();
    static protected HistoryManager historyManager;
    protected Set<Task> tasksSet = new TreeSet(comparator);


    public InMemoryTaskManager() {
        historyManager = new InMemoryHistoryManager();
    }

    // 2.1 Получение списка всех задач.
    @Override
    public Collection<Task> getAllTasks() {
        return tasks.values();
    }

    @Override
    public Collection<Subtask> getAllSubtasks() {
        return subTasks.values();
    }

    @Override
    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    // 2.2 Удаление всех задач.
    @Override
    public void delAllTasks() {
        for (Task task: getAllTasks()) {
            historyManager.remove(task.getId());
        }
        tasks.clear();
    }

    @Override
    public void delAllSubtasks() {
        for (Subtask subtask: getAllSubtasks()) {
            historyManager.remove(subtask.getId());
        }
        subTasks.clear();
    }

    @Override
    public void delAllEpics() {
        for (Epic epic: getAllEpics()) {
            historyManager.remove(epic.getId());
        }
        for (Subtask subtask: getAllSubtasks()) {
            historyManager.remove(subtask.getId());
        }
        epics.clear();
        subTasks.clear();
    }

    // 2.3 Получение по идентификатору.
    @Override
    public Task getTask(int id) {
        Task task = tasks.get(id);
        historyManager.add(task);
        return task;
    }

    @Override
    public Subtask getSubtask(int id) {
        Subtask subTask = subTasks.get(id);
        historyManager.add(subTask);
        return subTask;
    }

    @Override
    public Epic getEpic(int id) {
        Epic epic = epics.get(id);
        historyManager.add(epic);
        return epic;
    }

    /** Проверьте пересечения
     *
     * @return  true - есть пересечение
     */
    boolean getValidateTime(){
        Task task = null;
        for (Task t: tasksSet ) {
            if (task != null) {
                LocalDateTime l1 = task.getStartTime();
                LocalDateTime l2 = t.getEndTime();
                int i =  task.getStartTime().compareTo(t.getEndTime());
                LocalDateTime l3 = task.getEndTime();
                LocalDateTime l4 = t.getStartTime();
                int i2 = task.getEndTime().compareTo(t.getStartTime());
                if ((task.getStartTime().compareTo(t.getEndTime())<=0)
                           && (task.getEndTime().compareTo(t.getStartTime()) > 0)){
                    return true;
                }
            }
            task = t;
        }
        return false;
    }

    // 2.4 Создание. Сам объект должен передаваться в качестве параметра.
    @Override
    public int newTask(Task task) throws ValidationTimeException {
        tasks.put(task.getId(), task);
        tasksSet.add(task);
        if (getValidateTime()) {
            tasks.remove(task);
            tasksSet.remove(task);
            throw new ValidationTimeException("Время не корректно");
        }
        return task.getId();
    }

    @Override
    public Subtask newSubtask(Subtask subtask) {
        subTasks.put(subtask.getId(), subtask);
        tasksSet.add(subtask);
        return subtask;
    }

    @Override
    public int newEpic(Epic epic) {
        epics.put(epic.getId(), epic);
        return epic.getId();
    }

    // 2.5 Обновление. Новая версия объекта с верным идентификатор передаются в виде параметра.
    @Override
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        subTasks.put(subtask.getId(), subtask);
    }

    @Override
    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    // 2.6 Удаление по идентификатору.
    @Override
    public void delTask(int id) {
        Task task = getTask(id);
        historyManager.remove(id);
        tasks.remove(id);
        tasksSet.remove(task);
    }

    @Override
    public void delSubtask(int id) {
        Subtask subtask = getSubtask(id);
        historyManager.remove(id);
        subTasks.remove(id);
        tasksSet.remove(subtask);
    }

    @Override
    public void delEpic(int id) {
        // Collection не везде подходит, у него нет метода get(index)
        List<Integer> ids = getEpic(id).getSubtaskIds();
        for (int i = 0; i < ids.size(); i++) {
            historyManager.remove(ids.get(i));
            subTasks.remove(ids.get(i));
        }
        historyManager.remove(id);
        epics.remove(id);
    }

    // 3.1 Получение списка всех подзадач определённого эпика.
    @Override
    public Collection<Subtask> getSubtasks(Epic epic) {
        return epic.getSubtasks();
    }

    // 4.1 Менеджер сам не выбирает статус для задачи. Информация о нём приходит менеджеру вместе с информацией
    // о самой задаче. По этим данным в одних случаях он будет сохранять статус, в других будет рассчитывать.
    @Override
    public void setStatusTask(Task task, Status status) {
        task.setStatus(status);
    }

    @Override
    public void setStatusSubtask(Subtask subtask, Status status) {
        subtask.setStatus(status);
        setStatusEpic(subtask);
    }

    /**
     * Рассчитываем и устанавливаем статус эпика
     */
    void setStatusEpic(Subtask subtask) {
        Epic epic = getEpic(subtask.getParentId());
        int newStatus = 0;
        int doneStatus = 0;
        int count = getSubtasks(epic).size();
        for (Subtask lSubtask : getSubtasks(epic)) {
            switch (lSubtask.getStatus()) {
                case NEW:
                    newStatus++;
                    break;
                case DONE:
                    doneStatus++;
                    break;
            }
        }
        if (count == 0) {
            epic.setStatus(Status.NEW);
        } else if (newStatus == count) {
            epic.setStatus(Status.NEW);
        } else if (doneStatus == count) {
            epic.setStatus(Status.DONE);
        } else epic.setStatus(Status.IN_PROGRESS);
    }

    public HistoryManager getHistoryManager() {
        return historyManager;
    }

    @Override
    public Collection<Task> getPrioritizedTasks() {
        return tasksSet;
    }
}