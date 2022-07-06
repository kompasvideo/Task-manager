package ru.yandex.practicum.taskmanager.model;


import ru.yandex.practicum.taskmanager.manager.TaskManager;

import java.time.Duration;
import java.time.LocalDateTime;

public class Subtask extends Task {
    private int epicId;

    public Subtask(String title, String description, int id, LocalDateTime startTime, Duration duration) {
        super(title, description, id, startTime, duration);
        this.enumTask = EnumTask.SUBTASK;
    }

    public Subtask(int id, EnumTask enumTask, String title, Status status, String description, int epicId,
            LocalDateTime startTime, Duration duration) {
        super(id, enumTask, title, status, description, startTime, duration);
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "\n\t title= " + title +
                "\n\t description= " + description +
                "\n\t id= " + id +
                "\n\t status= " + status +
                "\n\t startTime= " + startTime +
                "\n\t duration= " + duration +
                "\n\t}";
    }

    public int getParentId() {
        return epicId;
    }

    public void setParent(Epic parent) {
        this.epicId = parent.getId();
    }

    // Напишите метод сохранения задачи в строку String toString(Task task) или переопределите базовый.
    @Override
    public String toString(Task task) {
        return String.format("%d,%s,%s,%s,%s,%s,%s,%s\r\n", task.id,task.enumTask, task.title,task.status,
                task.description,((Subtask)task).getParentId(), task.startTime, task.duration);
    }

    // Напишите метод создания задачи из строки Task fromString(String value).
    @Override
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
                System.out.println("Ошибка 1 в Task: public Task fromString(String value) ");
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
                System.out.println("Ошибка 2 в Task: public Task fromString(String value) ");
        }
        int iParent = -1;
        try
        {
            iParent = Integer.parseInt(strArray[5].trim());
        }
        catch (NumberFormatException nfe)
        {
            System.out.println("NumberFormatException 2: " + nfe.getMessage());
        }
        LocalDateTime startTime =  LocalDateTime.parse(strArray[6]);
        Duration duration = Duration.parse(strArray[7]);
        return new Subtask(id, enumTask, strArray[2], status,strArray[4], iParent, startTime, duration);
    }
}
