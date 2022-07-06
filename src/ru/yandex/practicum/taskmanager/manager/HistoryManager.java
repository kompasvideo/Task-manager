package ru.yandex.practicum.taskmanager.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import ru.yandex.practicum.taskmanager.model.*;

public interface HistoryManager {
    /** Первый add(Task task) должен помечать задачи как просмотренные */
    void add(Task task);

    /**  а второй getHistory() — возвращать их список. */
    // List оставлен по подсказке в задании
    List<Task> getHistory();

    /** удаляет задачу из истории */
    void remove(int taskId);

    Map<Integer, Node<Task>> map = new HashMap<>();

    String toString(HistoryManager manager);
}
