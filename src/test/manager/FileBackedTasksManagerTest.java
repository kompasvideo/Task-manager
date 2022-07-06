package test.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.taskmanager.exceptions.ValidationTimeException;
import ru.yandex.practicum.taskmanager.manager.Manager;

import java.io.IOException;
import java.net.URISyntaxException;

class FileBackedTasksManagerTest extends TaskManagerTest {

    @BeforeEach
    public void beforeEach() throws URISyntaxException, IOException, InterruptedException {
        String fileName = "file.csv";

        manager = Manager.getDefault(fileName);
    }

    @Test
    void getAllTasksTest() throws ValidationTimeException {
        super.getAllTasksTest();
    }

    @Test
    void getAllSubtasksTest() {
        super.getAllSubtasksTest();
    }

    @Test
    void getAllEpicsTest() {
        super.getAllEpicsTest();
    }

    @Test
    void delAllTasksTest() throws ValidationTimeException {
        super.delAllTasksTest();
    }

    @Test
    void delAllSubtasksTest() {
        super.delAllSubtasksTest();
    }

    @Test
    void delAllEpicsTest() {
        super.delAllEpicsTest();
    }

    @Test
    void getTaskTest() throws ValidationTimeException {
        super.getTaskTest();
    }

    @Test
    void getSubtaskTest() {
        super.getSubtaskTest();
    }

    @Test
    void getEpicTest() {
        super.getEpicTest();
    }

    @Test
    void newTaskTest() throws ValidationTimeException {
        super.newTaskTest();
    }

    @Test
    void newSubtaskTest() {
        super.newSubtaskTest();
    }

    @Test
    void newEpicTest() {
        super.newEpicTest();
    }

    @Test
    void updateTaskTest() throws ValidationTimeException {
        super.updateTaskTest();
    }

    @Test
    void updateSubtaskTest() {
        super.updateSubtaskTest();
    }

    @Test
    void updateEpicTest() {
        super.updateEpicTest();
    }

    @Test
    void delTaskTest() throws ValidationTimeException {
        super.delTaskTest();
    }

    @Test
    public void delSubtaskTest() {
        super.delSubtaskTest();
    }

    @Test
    public void delEpicTest() {
        super.delEpicTest();
    }

    @Test
    void getSubtasksTest(){
        super.getSubtasksTest();
    }

    @Test
    void setStatusTaskTest() throws ValidationTimeException {
        super.setStatusTaskTest();
    }

    @Test
    void setStatusSubtaskTest() {
        super.setStatusSubtaskTest();
    }

    @Test
    void getTaskTimeTest() throws ValidationTimeException {
        super.getTaskTimeTest();
    }

    @Test
    void getSubtaskTimeTest() {
        super.getSubtaskTimeTest();
    }

    @Test
    void getEpicTimeTest() {
        super.getEpicTimeTest();
    }
}