package test.manager;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.taskmanager.exceptions.ValidationTimeException;
import ru.yandex.practicum.taskmanager.manager.TaskManager;
import ru.yandex.practicum.taskmanager.model.Epic;
import ru.yandex.practicum.taskmanager.model.Status;
import ru.yandex.practicum.taskmanager.model.Subtask;
import ru.yandex.practicum.taskmanager.model.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public abstract class TaskManagerTest<T extends TaskManager> {
    protected TaskManager manager;

    void createTasks() throws ValidationTimeException {
        // Создаём 2 задач
        manager.newTask(new Task("Задача 1", "Описание задачи 1", 1,
                LocalDateTime.of(2022,7,1,8,0),
                Duration.of(1, ChronoUnit.DAYS)));
        manager.newTask(new Task("Задача 2", "Описание задачи 2",2,
                LocalDateTime.of(2022,7,2,9,0),
                Duration.of(1, ChronoUnit.DAYS)));
    }

    void createSubtasks(){
        // создание 3 подзадач
        manager.newSubtask(new Subtask("Подзадача 1","Описание подзадачи 1",3,
                LocalDateTime.of(2022,7,3,9,0),
                Duration.of(1, ChronoUnit.DAYS)));
        manager.newSubtask(new Subtask("Подзадача 2","Описание подзадачи 2",4,
                LocalDateTime.of(2022,7,4,9,0),
                Duration.of(1, ChronoUnit.DAYS)));
        manager.newSubtask(new Subtask("Подзадача 3","Описание подзадачи 3",5,
                LocalDateTime.of(2022,7,5,9,0),
                Duration.of(1, ChronoUnit.DAYS)));
    }

    void createEpics() {
        List<Subtask> subtasks = new ArrayList<>();
        // создание эпика с 3 подзадачами
        subtasks.add(manager.newSubtask(new Subtask("Подзадача 1","Описание подзадачи 1",3,
                LocalDateTime.of(2022,7,3,9,0),
                Duration.of(1, ChronoUnit.DAYS))));
        subtasks.add(manager.newSubtask(new Subtask("Подзадача 2","Описание подзадачи 2",4,
                LocalDateTime.of(2022,7,4,9,0),
                Duration.of(1, ChronoUnit.DAYS))));
        subtasks.add(manager.newSubtask(new Subtask("Подзадача 3","Описание подзадачи 3",5,
                LocalDateTime.of(2022,7,5,9,0),
                Duration.of(1, ChronoUnit.DAYS))));
        manager.newEpic(new Epic("Эпик 1","Описание эпика 1", 6, subtasks));

        // создание эпика без подзадач
        subtasks.clear();
        manager.newEpic(new Epic("Эпик 2","Описание эпика 2", 7, subtasks));
    }

    void createEpic() {
        List<Subtask> subtasks = new ArrayList<>();
        // создание эпика с 3 подзадачами
        subtasks.add(manager.newSubtask(new Subtask("Подзадача 1","Описание подзадачи 1",3,
                LocalDateTime.of(2022,7,3,9,0),
                Duration.of(1, ChronoUnit.DAYS))));
        subtasks.add(manager.newSubtask(new Subtask("Подзадача 2","Описание подзадачи 2",4,
                LocalDateTime.of(2022,7,4,9,0),
                Duration.of(1, ChronoUnit.DAYS))));
        subtasks.add(manager.newSubtask(new Subtask("Подзадача 3","Описание подзадачи 3",5,
                LocalDateTime.of(2022,7,5,9,0),
                Duration.of(1, ChronoUnit.DAYS))));
        manager.newEpic(new Epic("Эпик 1","Описание эпика 1", 6, subtasks));
    }

    @Test
    void getAllTasksTest() throws ValidationTimeException {
        List<Task> tasksPrepare = new ArrayList<>();
        tasksPrepare.add(new Task("Задача 1", "Описание задачи 1", 1,
                LocalDateTime.of(2022,7,1,9,0),
                Duration.of(1, ChronoUnit.DAYS)));
        tasksPrepare.add(new Task("Задача 2", "Описание задачи 2", 2,
                LocalDateTime.of(2022,7,2,9,0),
                Duration.of(1, ChronoUnit.DAYS)));

        createTasks();
        Collection<Task> tasks = manager.getAllTasks();
        assertNotNull(tasks, "Задачи не возвращяются.");
        assertEquals(2,tasks.size(), "Неверное количество задач.");
        assertArrayEquals(tasksPrepare.toArray(), tasks.toArray(), "Задачи не совпадают.");
    }

    @Test
    void getAllSubtasksTest() {
        List<Subtask> subTasksPrepare = new ArrayList<>();
        subTasksPrepare.add(new Subtask("Подзадача 1","Описание подзадачи 1",3,
                LocalDateTime.of(2022,7,3,9,0),
                Duration.of(1, ChronoUnit.DAYS)));
        subTasksPrepare.add(new Subtask("Подзадача 2","Описание подзадачи 2",4,
                LocalDateTime.of(2022,7,4,9,0),
                Duration.of(1, ChronoUnit.DAYS)));
        subTasksPrepare.add(new Subtask("Подзадача 3","Описание подзадачи 3",5,
                LocalDateTime.of(2022,7,5,9,0),
                Duration.of(1, ChronoUnit.DAYS)));

        createSubtasks();
        Collection<Subtask> subtasks = manager.getAllSubtasks();
        assertNotNull(subtasks, "Подзадачи не возвращяются.");
        assertEquals(3,subtasks.size(), "Неверное количество подзадач.");
        assertArrayEquals(subTasksPrepare.toArray(), subtasks.toArray(), "Подзадачи не совпадают.");
    }

    @Test
    void getAllEpicsTest() {
        List<Epic> epicsPrepare = new ArrayList<>();
        List<Subtask> subtasks = new ArrayList<>();
        subtasks.add(new Subtask("Подзадача 1","Описание подзадачи 1",3,
                LocalDateTime.of(2022,7,3,9,0),
                Duration.of(1, ChronoUnit.DAYS)));
        subtasks.add(new Subtask("Подзадача 2","Описание подзадачи 2",4,
                LocalDateTime.of(2022,7,4,9,0),
                Duration.of(1, ChronoUnit.DAYS)));
        subtasks.add(new Subtask("Подзадача 3","Описание подзадачи 3",5,
                LocalDateTime.of(2022,7,5,9,0),
                Duration.of(1, ChronoUnit.DAYS)));
        epicsPrepare.add(new Epic("Эпик 1","Описание эпика 1", 6, subtasks));
        subtasks.clear();
        epicsPrepare.add(new Epic("Эпик 2","Описание эпика 2", 7, subtasks));

        createEpics();
        Collection<Epic> epics = manager.getAllEpics();
        assertNotNull(epics, "Эпики не возвращяются.");
        assertEquals(2,epics.size(), "Неверное количество эпиов.");
        assertArrayEquals(epicsPrepare.toArray(), epics.toArray(), "Эпики не совпадают.");
    }

    @Test
    void delAllTasksTest() throws ValidationTimeException {
        createTasks();
        manager.delAllTasks();
        assertEquals(0,manager.getAllTasks().size(), "Неверное количество задач.");
        assertTrue(manager.getAllTasks().isEmpty());
    }

    @Test
    void delAllSubtasksTest() {
        createSubtasks();
        manager.delAllSubtasks();
        assertEquals(0,manager.getAllSubtasks().size(), "Неверное количество подзадач.");
        assertTrue(manager.getAllSubtasks().isEmpty());
    }

    @Test
    void delAllEpicsTest() {
        createEpics();
        manager.delAllEpics();
        assertEquals(0,manager.getAllEpics().size(), "Неверное количество эпиов.");
        assertTrue(manager.getAllEpics().isEmpty());
    }

    @Test
    void getTaskTest() throws ValidationTimeException {
        Task taskPrepare = new Task("Задача 1", "Описание задачи 1", 1,
                LocalDateTime.of(2022,7,1,9,0),
                Duration.of(1, ChronoUnit.DAYS));

        manager.newTask(new Task("Задача 1", "Описание задачи 1", 1,
                LocalDateTime.of(2022,7,1,9,0),
                Duration.of(1, ChronoUnit.DAYS)));
        Task task =  manager.getTask(1);
        assertNotNull(task, "Задачи не возвращяются.");
        assertEquals(task, taskPrepare);
    }

    @Test
    void getSubtaskTest() {
        Subtask subtaskPrepare = new Subtask("Подзадача 2","Описание подзадачи 2",4,
                LocalDateTime.of(2022,7,4,9,0),
                Duration.of(1, ChronoUnit.DAYS));

        manager.newSubtask(new Subtask("Подзадача 2","Описание подзадачи 2",4,
                LocalDateTime.of(2022,7,4,9,0),
                Duration.of(1, ChronoUnit.DAYS)));
        Subtask subtask =  manager.getSubtask(4);
        assertNotNull(subtask, "Подзадачи не возвращяются.");
        assertEquals(subtask, subtaskPrepare);
    }

    @Test
    void getEpicTest() {
        List<Subtask> subtasks = new ArrayList<>();
        subtasks.add(new Subtask("Подзадача 1","Описание подзадачи 1",3,
                LocalDateTime.of(2022,7,3,9,0),
                Duration.of(1, ChronoUnit.DAYS)));
        subtasks.add(new Subtask("Подзадача 2","Описание подзадачи 2",4,
                LocalDateTime.of(2022,7,4,9,0),
                Duration.of(1, ChronoUnit.DAYS)));
        subtasks.add(new Subtask("Подзадача 3","Описание подзадачи 3",5,
                LocalDateTime.of(2022,7,5,9,0),
                Duration.of(1, ChronoUnit.DAYS)));
        Epic epicPrepare = new Epic("Эпик 1","Описание эпика 1", 6, subtasks);

        createEpic();
        Epic epic =  manager.getEpic(6);
        assertNotNull(epic, "Эпики не возвращяются.");
        assertEquals(epic, epicPrepare);
    }

    @Test
    void newTaskTest() throws ValidationTimeException {
        Task taskPrepare = new Task("Задача 1", "Описание задачи 1", 1,
                LocalDateTime.of(2022,7,1,9,0),
                Duration.of(1, ChronoUnit.DAYS));
        manager.newTask(taskPrepare);

        Task task = manager.getTask(taskPrepare.getId());
        assertNotNull(task, "Задача не найдена.");
        assertEquals(taskPrepare, task, "Задачи не совпадают.");

        List<Task> tasks = new ArrayList<>( manager.getAllTasks());
        assertNotNull(tasks, "Задачи не возвращяются.");
        assertEquals(1,tasks.size(), "Неверное количество задач.");
        assertEquals(taskPrepare, tasks.get(0), "Задачи не совпадают.");
    }

    @Test
    void newSubtaskTest() {
        Subtask subtaskPrepare = new Subtask("Подзадача 1","Описание подзадачи 1",3,
                LocalDateTime.of(2022,7,3,9,0),
                Duration.of(1, ChronoUnit.DAYS));
        manager.newSubtask(subtaskPrepare);

        manager.newSubtask(new Subtask("Подзадача 1","Описание подзадачи 1",3,
                LocalDateTime.of(2022,7,3,9,0),
                Duration.of(1, ChronoUnit.DAYS)));
        Subtask subtask = manager.getSubtask(subtaskPrepare.getId());
        assertNotNull(subtask, "Подзадача не найдена.");
        assertEquals(subtaskPrepare, subtask, "Подзадачи не совпадают.");

        List<Subtask> subtasks = new ArrayList<>( manager.getAllSubtasks());
        assertNotNull(subtasks, "Подзадачи не возвращяются.");
        assertEquals(1,subtasks.size(), "Неверное количество подзадач.");
        assertEquals(subtaskPrepare, subtasks.get(0), "Подзадачи не совпадают.");
    }

    @Test
    void newEpicTest() {
        Epic epicPrepare = new Epic("Эпик 1","Описание эпика 1", 6, null);
        manager.newEpic(epicPrepare);

        createEpic();
        Epic epic = manager.getEpic(epicPrepare.getId());
        assertNotNull(epic, "Эпик не найден.");
        assertEquals(epicPrepare, epic, "Эпики не совпадают.");

        List<Epic> epics = new ArrayList<>( manager.getAllEpics());
        assertNotNull(epics, "Эпики не возвращяются.");
        assertEquals(1,epics.size(), "Неверное количество эпиов.");
        assertEquals(epicPrepare, epics.get(0), "Эпики не совпадают.");
    }

    @Test
    void updateTaskTest() throws ValidationTimeException {
        Task taskPrepare = new Task("Задача 3", "Описание задачи 3", 1,
                LocalDateTime.of(2022,7,1,9,0),
                Duration.of(1, ChronoUnit.DAYS));
        manager.newTask(taskPrepare);

        manager.newTask(new Task("Задача 1", "Описание задачи 1", 1,
                LocalDateTime.of(2022,7,1,9,0),
                Duration.of(1, ChronoUnit.DAYS)));
        manager.updateTask(taskPrepare);
        Task task = manager.getTask(taskPrepare.getId());
        assertNotNull(task, "Задача не найдена.");
        assertEquals(taskPrepare, task, "Задачи не совпадают.");

        List<Task> tasks = new ArrayList<>( manager.getAllTasks());
        assertNotNull(tasks, "Задачи не возвращяются.");
        assertEquals(1,tasks.size(), "Неверное количество задач.");
        assertEquals(taskPrepare, tasks.get(0), "Задачи не совпадают.");
    }

    @Test
    void updateSubtaskTest() {
        Subtask subtaskPrepare = new Subtask("Подзадача 4","Описание подзадачи 4",3,
                LocalDateTime.of(2022,7,3,9,0),
                Duration.of(1, ChronoUnit.DAYS));

        manager.newSubtask(new Subtask("Подзадача 1","Описание подзадачи 1",3,
                LocalDateTime.of(2022,7,3,9,0),
                Duration.of(1, ChronoUnit.DAYS)));
        manager.updateSubtask(subtaskPrepare);
        Subtask subtask = manager.getSubtask(subtaskPrepare.getId());
        assertNotNull(subtask, "Подзадача не найдена.");
        assertEquals(subtaskPrepare, subtask, "Подзадачи не совпадают.");

        List<Subtask> subtasks = new ArrayList<>( manager.getAllSubtasks());
        assertNotNull(subtasks, "Подзадачи не возвращяются.");
        assertEquals(1,subtasks.size(), "Неверное количество подзадач.");
        assertEquals(subtaskPrepare, subtasks.get(0), "Подзадачи не совпадают.");
    }


    @Test
    void updateEpicTest() {
        Epic epicPrepare = new Epic("Эпик 3","Описание эпика 3", 6, null);
        manager.newEpic(epicPrepare);

        createEpic();
        manager.updateEpic(epicPrepare);
        Epic epic = manager.getEpic(epicPrepare.getId());
        assertNotNull(epic, "Эпик не найден.");
        assertEquals(epicPrepare, epic, "Эпики не совпадают.");

        List<Epic> epics = new ArrayList<>( manager.getAllEpics());
        assertNotNull(epics, "Эпики не возвращяются.");
        assertEquals(1,epics.size(), "Неверное количество эпиов.");
        assertEquals(epicPrepare, epics.get(0), "Эпики не совпадают.");
    }

    @Test
    void delTaskTest() throws ValidationTimeException {
        createTasks(); // создание 2 задач
        manager.delTask(1);

        List<Task> tasks = new ArrayList<>( manager.getAllTasks());
        assertEquals(1,tasks.size(), "Неверное количество задач.");
    }

    @Test
    public void delSubtaskTest() {
        createSubtasks();
        manager.delSubtask(3);

        List<Subtask> subtasks = new ArrayList<>( manager.getAllSubtasks());
        assertEquals(2,subtasks.size(), "Неверное количество подзадач.");
    }

    @Test
    public void delEpicTest() {
        createEpics();
        manager.delEpic(6);

        List<Epic> epics = new ArrayList<>( manager.getAllEpics());
        assertEquals(1,epics.size(), "Неверное количество эпиов.");
    }

    @Test
    void getSubtasksTest(){
        List<Subtask> subTasksPrepare = new ArrayList<>();
        subTasksPrepare.add(new Subtask("Подзадача 1","Описание подзадачи 1",3,
                LocalDateTime.of(2022,7,3,9,0),
                Duration.of(1, ChronoUnit.DAYS)));
        subTasksPrepare.add(new Subtask("Подзадача 2","Описание подзадачи 2",4,
                LocalDateTime.of(2022,7,4,9,0),
                Duration.of(1, ChronoUnit.DAYS)));
        subTasksPrepare.add(new Subtask("Подзадача 3","Описание подзадачи 3",5,
                LocalDateTime.of(2022,7,5,9,0),
                Duration.of(1, ChronoUnit.DAYS)));

        createEpics();
        Epic epic = manager.getEpic(6);
        Collection<Subtask> subtasks = manager.getSubtasks(epic);
        assertNotNull(subtasks, "Подзадачи не возвращяются.");
        assertEquals(3,subtasks.size(), "Неверное количество подзадач.");
        assertArrayEquals(subTasksPrepare.toArray(), subtasks.toArray(), "Подзадачи не совпадают.");
    }

    @Test
    void setStatusTaskTest() throws ValidationTimeException {
        Task taskPrepare = new Task("Задача 1", "Описание задачи 1", 1,
                LocalDateTime.of(2022,7,1,9,0),
                Duration.of(1, ChronoUnit.DAYS));
        manager.newTask(new Task("Задача 1", "Описание задачи 1", 1,
                LocalDateTime.of(2022,7,1,9,0),
                Duration.of(1, ChronoUnit.DAYS)));
        Task task =  manager.getTask(1);

        manager.setStatusTask(task, Status.IN_PROGRESS);
        Status status = manager.getTask(1).getStatus();
        assertEquals(status, Status.IN_PROGRESS);
    }

    @Test
    void setStatusSubtaskTest() {
        createEpics();
        Subtask subtask = manager.getSubtask(3);
        manager.setStatusSubtask(subtask,Status.IN_PROGRESS);

        Status status = manager.getSubtask(3).getStatus();
        assertEquals(status, Status.IN_PROGRESS);
    }

    @Test
    void getTaskTimeTest() throws ValidationTimeException {
        Task taskPrepare = new Task("Задача 1", "Описание задачи 1", 1,
                LocalDateTime.of(2022,7,1,9,0),
                Duration.of(1, ChronoUnit.DAYS));

        manager.newTask(new Task("Задача 1", "Описание задачи 1", 1,
                LocalDateTime.of(2022,7,1,9,0),
                Duration.of(1, ChronoUnit.DAYS)));
        Task task =  manager.getTask(1);
        assertEquals(task.getStartTime(), LocalDateTime.of(2022,7,1,9,0),
                "Время окончания задачи не совпадает.");
        assertEquals(task.getDuration(), Duration.of(1, ChronoUnit.DAYS),
                "Duration не совпадает.");
        assertEquals(task.getEndTime(), LocalDateTime.of(2022,7,2,9,0),
                "Время окончания задачи не совпадает.");
    }

    @Test
    void getSubtaskTimeTest() {
        Subtask subtaskPrepare = new Subtask("Подзадача 2","Описание подзадачи 2",4,
                LocalDateTime.of(2022,7,4,9,0),
                Duration.of(1, ChronoUnit.DAYS));

        manager.newSubtask(new Subtask("Подзадача 2","Описание подзадачи 2",4,
                LocalDateTime.of(2022,7,4,9,0),
                Duration.of(1, ChronoUnit.DAYS)));
        Subtask subtask =  manager.getSubtask(4);
        assertEquals(subtask.getStartTime(), LocalDateTime.of(2022,7,4,9,0),
                "Время окончания подзадачи не совпадает.");
        assertEquals(subtask.getDuration(), Duration.of(1, ChronoUnit.DAYS),
                "Duration не совпадает.");
        assertEquals(subtask.getEndTime(), LocalDateTime.of(2022,7,5,9,0),
                "Время окончания подзадачи не совпадает.");
    }

    @Test
    void getEpicTimeTest() {
        List<Subtask> subtasks = new ArrayList<>();
        subtasks.add(new Subtask("Подзадача 1","Описание подзадачи 1",3,
                LocalDateTime.of(2022,7,3,9,0),
                Duration.of(1, ChronoUnit.DAYS)));
        subtasks.add(new Subtask("Подзадача 2","Описание подзадачи 2",4,
                LocalDateTime.of(2022,7,4,9,0),
                Duration.of(1, ChronoUnit.DAYS)));
        subtasks.add(new Subtask("Подзадача 3","Описание подзадачи 3",5,
                LocalDateTime.of(2022,7,5,9,0),
                Duration.of(1, ChronoUnit.DAYS)));
        Epic epicPrepare = new Epic("Эпик 1","Описание эпика 1", 6, subtasks);

        createEpic();
        Epic epic =  manager.getEpic(6);
        assertEquals(epic.getStartTime(), LocalDateTime.of(2022,7,3,9,0),
                "Время окончания эпика не совпадает.");
        assertEquals(epic.getDuration(), Duration.of(3, ChronoUnit.DAYS),
                "Duration не совпадает.");
        assertEquals(epic.getEndTime(), LocalDateTime.of(2022,7,6,9,0),
                "Время окончания эпика не совпадает.");

    }
}
