package test.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.taskmanager.manager.HistoryManager;
import ru.yandex.practicum.taskmanager.manager.InMemoryHistoryManager;
import ru.yandex.practicum.taskmanager.manager.Manager;
import ru.yandex.practicum.taskmanager.manager.TaskManager;
import ru.yandex.practicum.taskmanager.model.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    private HistoryManager historyManager;
    private Task task;

    @BeforeEach
    public void beforeEach() {
        historyManager = new InMemoryHistoryManager();
        task = new Task("Задача 1", "Описание задачи 1", 1,
                LocalDateTime.of(2022,7,1,9,0),
                Duration.of(1, ChronoUnit.DAYS));
    }

    @Test
    void addTest() {
        historyManager.add(task);
        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "Неверное количество задач в истории.");
    }

    @Test
    void getHistoryTest() {
        historyManager.add(task);
        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "Неверное количество задач в истории.");
    }

    @Test
    void removeTest() {
        historyManager.add(task);
        historyManager.remove(task.getId());
        List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(0, history.size(), "История не пустая.");

        historyManager.add(task);
        historyManager.add(new Task("Задача 2", "Описание задачи 2", 2,
                LocalDateTime.of(2022,7,2,9,0),
                Duration.of(1, ChronoUnit.DAYS)));
        historyManager.add(new Task("Задача 3", "Описание задачи 3", 3,
                LocalDateTime.of(2022,7,3,9,0),
                Duration.of(1, ChronoUnit.DAYS)));
        historyManager.add(new Task("Задача 4", "Описание задачи 4", 4,
                LocalDateTime.of(2022,7,4,9,0),
                Duration.of(1, ChronoUnit.DAYS)));

        // удаление из истории с начала
        historyManager.remove(task.getId());
        history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(3, history.size(), "История не пустая.");

        // удаление из истории с середины
        historyManager.remove(3);
        history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(2, history.size(), "История не пустая.");

        // удаление из истории с начала
        historyManager.remove(4);
        history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История не пустая.");
    }
}