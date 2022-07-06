package test.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.taskmanager.manager.Manager;
import ru.yandex.practicum.taskmanager.manager.TaskManager;
import ru.yandex.practicum.taskmanager.model.Epic;
import ru.yandex.practicum.taskmanager.model.Subtask;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import ru.yandex.practicum.taskmanager.model.*;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    private TaskManager manager;
    private int id;

    @BeforeEach
    public void beforeEach() {
        manager = Manager.getDefault();
        id = manager.hashCode();
    }

    /**
     * Для расчёта статуса Epic. Граничные условия: a. Пустой список подзадач.
     */
    @Test
    void shouldReturnNullSubtask() {
        assertTrue(manager.getAllSubtasks().isEmpty());
    }

    /**
     * Для расчёта статуса Epic. Граничные условия: b. Все подзадачи со статусом NEW
     */
    @Test
    void shouldReturnSubtaskIsStatusNew() {
        List<Subtask> subtasks = new ArrayList<>();
        subtasks.add(manager.newSubtask(new Subtask("Подзадача 1","Описание подзадачи 1",++id,
                LocalDateTime.of(2022,7,4,9,0),
                Duration.of(1, ChronoUnit.DAYS))));
        subtasks.add(manager.newSubtask(new Subtask("Подзадача 2","Описание подзадачи 2",++id,
                LocalDateTime.of(2022,7,4,9,0),
                Duration.of(1, ChronoUnit.DAYS))));
        subtasks.add(manager.newSubtask(new Subtask("Подзадача 3","Описание подзадачи 3",++id,
                LocalDateTime.of(2022,7,5,9,0),
                Duration.of(1, ChronoUnit.DAYS))));
        int epicId = manager.newEpic(new Epic("Эпик 1","Описание эпика 1", ++id, subtasks));
        List<Status> statusPrepare = new ArrayList<>();
        statusPrepare.add(Status.NEW);
        statusPrepare.add(Status.NEW);
        statusPrepare.add(Status.NEW);

        var subTasks = manager.getAllSubtasks();
        List<Status> statusExec = new ArrayList<>();
        for (Subtask subtask: subTasks) {
            statusExec.add(subtask.getStatus());
        }
        assertArrayEquals(statusPrepare.toArray(), statusExec.toArray(), "Array Equal Test");
    }

    /**
     * Для расчёта статуса Epic. Граничные условия: c. Все подзадачи со статусом DONE
     */
    @Test
    void shouldReturnSubtaskIsStatusDone() {
        List<Subtask> subtasks = new ArrayList<>();
        subtasks.add(manager.newSubtask(new Subtask("Подзадача 1","Описание подзадачи 1",++id,
                LocalDateTime.of(2022,7,3,9,0),
                Duration.of(1, ChronoUnit.DAYS))));
        subtasks.add(manager.newSubtask(new Subtask("Подзадача 2","Описание подзадачи 2",++id,
                LocalDateTime.of(2022,7,4,9,0),
                Duration.of(1, ChronoUnit.DAYS))));
        subtasks.add(manager.newSubtask(new Subtask("Подзадача 3","Описание подзадачи 3",++id,
                LocalDateTime.of(2022,7,5,9,0),
                Duration.of(1, ChronoUnit.DAYS))));
        int epicId = manager.newEpic(new Epic("Эпик 1","Описание эпика 1", ++id, subtasks));
        List<Status> statusPrepare = new ArrayList<>();
        statusPrepare.add(Status.DONE);
        statusPrepare.add(Status.DONE);
        statusPrepare.add(Status.DONE);
        var subTasks = manager.getAllSubtasks();
        for (Subtask subtask: subTasks) {
            subtask.setStatus(Status.DONE);
        }

        List<Status> statusExec = new ArrayList<>();
        for (Subtask subtask: subTasks) {
            statusExec.add(subtask.getStatus());
        }
        assertArrayEquals(statusPrepare.toArray(), statusExec.toArray(), "Array Equal Test");
    }

    /**
     * Для расчёта статуса Epic. Граничные условия: d. Подзадачи со статусами NEW и DONE
     */
    @Test
    void shouldReturnSubtaskIsStatusNewAndDone() {
        List<Subtask> subtasks = new ArrayList<>();
        subtasks.add(manager.newSubtask(new Subtask("Подзадача 1","Описание подзадачи 1",++id,
                LocalDateTime.of(2022,7,3,9,0),
                Duration.of(1, ChronoUnit.DAYS))));
        subtasks.add(manager.newSubtask(new Subtask("Подзадача 2","Описание подзадачи 2",++id,
                LocalDateTime.of(2022,7,4,9,0),
                Duration.of(1, ChronoUnit.DAYS))));
        subtasks.add(manager.newSubtask(new Subtask("Подзадача 3","Описание подзадачи 3",++id,
                LocalDateTime.of(2022,7,5,9,0),
                Duration.of(1, ChronoUnit.DAYS))));
        int epicId = manager.newEpic(new Epic("Эпик 1","Описание эпика 1", ++id, subtasks));
        List<Status> statusPrepare = new ArrayList<>();
        statusPrepare.add(Status.NEW);
        statusPrepare.add(Status.DONE);
        statusPrepare.add(Status.NEW);
        var subTasks = manager.getAllSubtasks();
        int i = 0;
        for (Subtask subtask: subTasks) {
            if (i == 1)
                subtask.setStatus(Status.DONE);
            i++;
        }

        List<Status> statusExec = new ArrayList<>();
        for (Subtask subtask: subTasks) {
            statusExec.add(subtask.getStatus());
        }
        assertArrayEquals(statusPrepare.toArray(), statusExec.toArray(), "Array Equal Test");
    }

    /**
     * Для расчёта статуса Epic. Граничные условия: c. Все подзадачи со статусом DONE
     */
    @Test
    void shouldReturnSubtaskIsStatusIn_Progress() {
        List<Subtask> subtasks = new ArrayList<>();
        subtasks.add(manager.newSubtask(new Subtask("Подзадача 1","Описание подзадачи 1",++id,
                LocalDateTime.of(2022,7,3,9,0),
                Duration.of(1, ChronoUnit.DAYS))));
        subtasks.add(manager.newSubtask(new Subtask("Подзадача 2","Описание подзадачи 2",++id,
                LocalDateTime.of(2022,7,4,9,0),
                Duration.of(1, ChronoUnit.DAYS))));
        subtasks.add(manager.newSubtask(new Subtask("Подзадача 3","Описание подзадачи 3",++id,
                LocalDateTime.of(2022,7,5,9,0),
                Duration.of(1, ChronoUnit.DAYS))));
        int epicId = manager.newEpic(new Epic("Эпик 1","Описание эпика 1", ++id, subtasks));
        List<Status> statusPrepare = new ArrayList<>();
        statusPrepare.add(Status.IN_PROGRESS);
        statusPrepare.add(Status.IN_PROGRESS);
        statusPrepare.add(Status.IN_PROGRESS);
        var subTasks = manager.getAllSubtasks();
        for (Subtask subtask: subTasks) {
            subtask.setStatus(Status.IN_PROGRESS);
        }

        List<Status> statusExec = new ArrayList<>();
        for (Subtask subtask: subTasks) {
            statusExec.add(subtask.getStatus());
        }
        assertArrayEquals(statusPrepare.toArray(), statusExec.toArray(), "Array Equal Test");
    }

    @Test
    void shouldGetEndTime() {
        List<Subtask> subtasks = new ArrayList<>();
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
        LocalDateTime localDateTimeEnd = LocalDateTime.of(2022,7,6,9,0);

        LocalDateTime localDateTime = manager.getEpic(6).getEndTime();
        assertNotNull(localDateTime,"Время окончания задачи не возвращено.");
        assertEquals(localDateTimeEnd,localDateTime,"Время окончания задачи не совпадает.");
    }

}