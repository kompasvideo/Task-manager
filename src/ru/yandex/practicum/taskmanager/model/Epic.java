package ru.yandex.practicum.taskmanager.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Epic extends Task {
    private List<Subtask> subtasks = new ArrayList<>();

    @Override
    public String toString() {
        return "Epic{" +
                "\n\t title= " + title +
                "\n\t description= " + description +
                "\n\t id= " + id +
                "\n\t status= " + status +
                "\n\t subtasks= " + subtasks +
                "\n\t startTime= " + startTime +
                "\n\t duration= " + duration +
                "\n\t}";
    }

    public Epic(String title, String description, int id){
        super(title, description, id, LocalDateTime.MIN, Duration.ZERO);
        this.enumTask = EnumTask.EPIC;
        calculateStartTime();
        calculateDuration();
    }

    // Collection не везде подходит, у него нет метода get(index)
    public Epic(String title, String description, int id, List<Subtask> subtasks){
        super(title, description, id, LocalDateTime.MIN, Duration.ZERO);
        this.enumTask = EnumTask.EPIC;
        if (subtasks != null) {
            for (int i = 0; i < subtasks.size(); i++) {
                Subtask subtask = subtasks.get(i);
                subtask.setParent(this);
                this.subtasks.add(subtask);
            }
        }
        calculateStartTime();
        calculateDuration();
    }

    public Epic(int id, EnumTask enumTask, String title, Status status, String description, LocalDateTime startTime, Duration duration ) {
        super(id,enumTask,title,status,description,startTime,duration);
        this.enumTask = EnumTask.EPIC;
        if (subtasks != null) {
            for (int i = 0; i < subtasks.size(); i++) {
                Subtask subtask = subtasks.get(i);
                subtask.setParent(this);
                this.subtasks.add(subtask);
            }
        }
    }

    void calculateStartTime() {
        LocalDateTime startTime = LocalDateTime.MIN;
        for (Subtask subtask: subtasks ) {
            if (startTime == LocalDateTime.MIN) {
                startTime = subtask.startTime;
            } else {
                if ((subtask.startTime).compareTo(startTime) == -1) {
                    startTime = subtask.startTime;
                }
            }
        }
        this.startTime = startTime;
    }

    void calculateDuration(){
        LocalDateTime startTimeMore = LocalDateTime.MIN;
        Duration duration = Duration.ZERO;
        if (subtasks.size() == 0)
            return;
        for (Subtask subtask: subtasks ) {
            if (startTimeMore == LocalDateTime.MIN) {
                startTimeMore = subtask.startTime;
            } else {
                //int i = (subtask.startTime).compareTo(startTimeMore);
                if ((subtask.startTime).compareTo(startTimeMore) == 1) {
                    startTimeMore = subtask.startTime;
                    duration = subtask.duration;
                }
            }
        }
        startTimeMore = startTimeMore.plus(duration);
        this.duration = Duration.between(startTime, startTimeMore);
    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    /**
     * Возвращяет id всех подзадач
     * @return
     */
    public List<Integer> getSubtaskIds() {
        // Collection не везде подходит, у него нет метода get(index)
        List<Integer> ids = new ArrayList<>();
        for (Subtask subtask: subtasks){
            ids.add(subtask.getId());
        }
        return ids;
    }

    // Напишите метод сохранения задачи в строку String toString(Task task) или переопределите базовый.
    public String toString(Task task) {
        return String.format("%d,%s,%s,%s,%s,%s,%s,%s\r\n", task.id,task.enumTask, task.title,task.status,
                task.description,"",task.startTime,task.duration);
    }

    // Напишите метод создания задачи из строки Task fromString(String value).
    public Task fromString(String value) {
        String[] strArray = value.split(",");
        int id = -1;
        try
        {
            id = Integer.parseInt(strArray[0].trim());
        }
        catch (NumberFormatException nfe)
        {
            System.out.println("NumberFormatException: " + nfe.getMessage());
        }
        EnumTask enumTask = EnumTask.TASK;
        switch (strArray[1]) {
            case "TASK":
                enumTask = EnumTask.TASK;
                break;
            case "SUBTASK":
                enumTask = EnumTask.SUBTASK;
                break;
            case "EPIC":
                enumTask = EnumTask.EPIC;
                break;
            default:
                System.out.println("Ошибка 1 в Epic: public Task fromString(String value) ");
        }
        Status status = Status.NEW;
        switch (strArray[3]) {
            case "NEW":
                status = Status.NEW;
                break;
            case "IN_PROGRESS":
                status = Status.IN_PROGRESS;
                break;
            case "EPIC":
                status = Status.DONE;
                break;
            default:
                System.out.println("Ошибка 2 в Epic: public Task fromString(String value) ");
        }
        LocalDateTime startTime =  LocalDateTime.MIN;
        if (strArray[5] == "null") {
            startTime = null;
        } else {
            startTime = LocalDateTime.parse(strArray[5]);
        }
        Duration duration = Duration.ZERO;
        if (strArray[6] == "null") {
            duration = null;
        } else {
            duration = Duration.parse(strArray[6]);
        }
        return new Task(id, enumTask, strArray[2], status,strArray[4], startTime, duration);
    }

    public void addSubtask( Subtask subtask) {
        if (subtasks == null) {
            subtasks = new ArrayList<>();
        }
        subtasks.add(subtask);
    }
}
