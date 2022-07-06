package ru.yandex.practicum.taskmanager.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import ru.yandex.practicum.taskmanager.manager.FileBackedTasksManager;
import ru.yandex.practicum.taskmanager.model.Epic;
import ru.yandex.practicum.taskmanager.model.Subtask;
import ru.yandex.practicum.taskmanager.model.Task;

import java.io.*;
import java.net.URISyntaxException;
import java.time.LocalDateTime;

public class HTTPTaskManager extends FileBackedTasksManager {
    private final KVTaskClient kvTaskClient;
    private final Gson gson;

    public HTTPTaskManager(String url) throws URISyntaxException, IOException, InterruptedException {
        super(url);
        this.kvTaskClient = new KVTaskClient(url);
        gson = new GsonBuilder().
                registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).create();
    }
    @Override
    public void save() {
        String json;
        for (Task task : tasks.values()) {
            json = gson.toJson(task);
            kvTaskClient.put(String.valueOf(task.getId()), json);
        }

        for (Subtask subtask : subTasks.values()) {
            json = gson.toJson(subtask);
            kvTaskClient.put(String.valueOf(subtask.getId()), json);
        }

        for (Epic epic : epics.values()) {
            json = gson.toJson(epic);
            kvTaskClient.put(String.valueOf(epic.getId()), json);
        }
        json = gson.toJson( historyManager.toString(historyManager));
        kvTaskClient.put("history", json);
    }

    public String load(String key) throws NumberFormatException{
        String json = kvTaskClient.load(key);
        if (json != null) {
            try {
                int i = Integer.parseInt(key);
            }
            catch (NumberFormatException ex) {
                return json;
            }
            return readJsonString(json, getSimpleName(Integer.parseInt(key))).toString();
        } else
            return null;
    }

    private Task readJsonString(String json, String simpleNameTask) {
        Gson gson = new GsonBuilder().
                registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).create();
        if (simpleNameTask.equals("Task")) {
            Task task  = gson.fromJson(json, new TypeToken<Task>() {
            }.getType());
            return task;
        } else if (simpleNameTask.equals("Subtask")){
            return gson.fromJson(json, new TypeToken<Subtask>() {
            }.getType());
        } else{
            Epic epic = gson.fromJson(json, new TypeToken<Epic>() {
            }.getType());
            return epic;
        }
    }

    private String getSimpleName(int id){
        if (tasks.containsKey(id))
            return tasks.get(id).getClass().getSimpleName();
        else if (subTasks.containsKey(id))
            return subTasks.get(id).getClass().getSimpleName();
        else
            return epics.get(id).getClass().getSimpleName();
    }
}
