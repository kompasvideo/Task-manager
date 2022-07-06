package ru.yandex.practicum.taskmanager.manager;


import ru.yandex.practicum.taskmanager.http.HTTPTaskManager;

import java.io.IOException;
import java.net.URISyntaxException;

public class Manager  {
    private static InMemoryTaskManager inMemoryTaskManager;
    public static HistoryManager historyManager;

    /** Метод getDefault() будет без параметров. Он должен возвращать объект-менеджер,
     * поэтому типом его возвращаемого значения будет TaskManager. */
    public static TaskManager getDefault(){
        historyManager = new InMemoryHistoryManager();
        inMemoryTaskManager = new InMemoryTaskManager();
        return inMemoryTaskManager;
    }

    /** Добавьте в служебный класс Managers статический метод HistoryManager getDefaultHistory().
     * Он должен возвращать объект InMemoryHistoryManager — историю просмотров. */
    public static HistoryManager getDefaultHistory() {
        return historyManager;
    }

    public static TaskManager getDefault(String fileName) throws URISyntaxException, IOException, InterruptedException {
        historyManager = new InMemoryHistoryManager();
        inMemoryTaskManager = new HTTPTaskManager(fileName);
        return inMemoryTaskManager;
    }

}
