package ru.yandex.practicum.taskmanager.manager;

import ru.yandex.practicum.taskmanager.exceptions.ValidationTimeException;
import ru.yandex.practicum.taskmanager.model.*;

import java.util.*;

public interface TaskManager {
    // 2.1 Получение списка всех задач.
    Collection<Task> getAllTasks();

    Collection<Subtask> getAllSubtasks();

    List<Epic> getAllEpics();

    // 2.2 Удаление всех задач.
    void delAllTasks();

    void delAllSubtasks();

    void delAllEpics();

    // 2.3 Получение по идентификатору.
    Task getTask(int id);

    Subtask getSubtask(int id);

    Epic getEpic(int id);

    // 2.4 Создание. Сам объект должен передаваться в качестве параметра.
    int newTask(Task task) throws ValidationTimeException;

    Subtask newSubtask(Subtask subtask);

    int newEpic(Epic epic);

    // 2.5 Обновление. Новая версия объекта с верным идентификатор передаются в виде параметра.
    void updateTask(Task task);

    void updateSubtask(Subtask subtask);

    void updateEpic(Epic epic);

    // 2.6 Удаление по идентификатору.
    void delTask(int id);

    void delSubtask(int id);

    void delEpic(int id);

    // 3.1 Получение списка всех подзадач определённого эпика.
    Collection<Subtask> getSubtasks(Epic epic);

    // 4.1 Менеджер сам не выбирает статус для задачи. Информация о нём приходит менеджеру вместе с информацией
    // о самой задаче. По этим данным в одних случаях он будет сохранять статус, в других будет рассчитывать.
    void setStatusTask(Task task, Status status);

    void setStatusSubtask(Subtask subtask, Status status);

    HistoryManager getHistoryManager();

    Collection<Task> getPrioritizedTasks();
}
