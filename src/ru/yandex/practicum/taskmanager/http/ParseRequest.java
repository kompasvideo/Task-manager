package ru.yandex.practicum.taskmanager.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.yandex.practicum.taskmanager.exceptions.ValidationTimeException;
import ru.yandex.practicum.taskmanager.manager.HistoryManager;
import ru.yandex.practicum.taskmanager.manager.TaskManager;
import ru.yandex.practicum.taskmanager.model.Epic;
import ru.yandex.practicum.taskmanager.model.Subtask;
import ru.yandex.practicum.taskmanager.model.Task;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static jdk.internal.util.xml.XMLStreamWriter.DEFAULT_CHARSET;

public class ParseRequest {
    static String response;
    static Gson gson;
    static GsonBuilder gsonBuilder = new GsonBuilder();

    public static String parseGetRequest(TaskManager manager, String query, String path) {
        gson = new GsonBuilder().
                registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).create();
        switch (path) {
            case "/tasks/task/":
                if (query != null) {
                    String[] mas = query.split("=");
                    int id = Integer.parseInt(mas[1]);
                    Task task = manager.getTask(id);
                    response = gson.toJson(task);
                } else {
                    Collection<Task> tasks = manager.getAllTasks();
                    response = gson.toJson(tasks);
                }
                break;
            case "/tasks/subtask/":
                if (query != null) {
                    String[] mas = query.split("=");
                    int id = Integer.parseInt(mas[1]);
                    Subtask subtask = manager.getSubtask(id);
                    response = gson.toJson(subtask);
                } else {
                    Collection<Subtask> subtasks = manager.getAllSubtasks();
                    response = gson.toJson(subtasks);
                }
                break;
            case "/tasks/epic/":
                if (query != null) {
                    String[] mas = query.split("=");
                    int id = Integer.parseInt(mas[1]);
                    Epic epic = manager.getEpic(id);
                    response = gson.toJson(epic);
                } else {
                    List<Epic> epics = manager.getAllEpics();
                    response = gson.toJson(epics);
                }
                break;
            case "/tasks/subtask/epic/":
                if (query != null) {
                    String[] mas = query.split("=");
                    int id = Integer.parseInt(mas[1]);
                    Collection<Subtask> subtasks = manager.getSubtasks(manager.getEpic(id));
                    response = gson.toJson(subtasks);
                }
                break;
            case "/tasks/history":
                List<Task> tasksH = manager.getHistoryManager().getHistory();
                response =gson.toJson(tasksH);
                break;
            case "/tasks/":
                Collection<Task> tasksC =  manager.getPrioritizedTasks();
                response = gson.toJson(tasksC);
                break;
            default:
                response ="error";
                break;
        }
        return response;
    }
    public static String parsePostRequest(TaskManager manager,InputStream body, String query, String path) throws IOException, ValidationTimeException {
        String answer = "";
        String bodyS = "";
        gson = new GsonBuilder().
                registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).create();
        switch (path) {
            case "/tasks/task/":
                bodyS = new String(body.readAllBytes(), DEFAULT_CHARSET);
                Task task = gson.fromJson(bodyS, Task.class);
                if (query == null) {
                    manager.newTask(task);
                    answer = gson.toJson(task.getId());
                }
                break;
            case "/tasks/subtask/":
                bodyS = new String(body.readAllBytes(), DEFAULT_CHARSET);
                Subtask subtask = gson.fromJson(bodyS, Subtask.class);
                if (query == null) {
                    manager.newSubtask(subtask);
                    answer = gson.toJson(subtask.getId());
                }
                break;
            case "/tasks/epic/":
                bodyS = new String(body.readAllBytes(), DEFAULT_CHARSET);
                Epic epic = gson.fromJson(bodyS, Epic.class);
                if (query == null) {
                    manager.newEpic(epic);
                    answer = gson.toJson(epic.getId());
                }
                break;
        }
        return answer;
    }
    public static String parseDeleteRequest(TaskManager manager, String query, String path) {
        String answer = "";
        switch (path) {
            case "/tasks/task/":
                if (query == null) {
                    manager.delAllTasks();
                } else {
                    String[] mas = query.split("=");
                    int id = Integer.parseInt(mas[1]);
                    manager.delTask(id);
                }
                break;
            case "/tasks/subtask/":
                if (query == null) {
                    manager.delAllSubtasks();
                } else {
                    String[] mas = query.split("=");
                    int id = Integer.parseInt(mas[1]);
                    manager.delSubtask(id);
                }
                break;
            case "/tasks/epic/":
                if (query == null) {
                    manager.delAllEpics();
                } else {
                    String[] mas = query.split("=");
                    int id = Integer.parseInt(mas[1]);
                    manager.delEpic(id);
                }
                break;
        }
        return answer;
    }
}
