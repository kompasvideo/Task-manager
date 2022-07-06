package ru.yandex.practicum.taskmanager.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task {
    protected String title;
    protected String description;
    protected int id;
    protected Status status;
    protected EnumTask enumTask;

    /** продолжительность задачи, оценка того, сколько времени она займёт
     */
    protected Duration duration;

    /** дата, когда предполагается приступить к выполнению задачи
     *
     */
    protected LocalDateTime startTime;

    public Task(String title, String description, int id, LocalDateTime startTime, Duration duration) {
        this.title = title;
        this.description = description;
        this.id = id;
        this.status = Status.NEW;
        this.enumTask = EnumTask.TASK;
        this.startTime = startTime;
        this.duration= duration;
    }

    public Task(int id, EnumTask enumTask, String title, Status status, String description, LocalDateTime startTime, Duration duration ) {
        this.id = id;
        this.enumTask = enumTask;
        this.title = title;
        this.status = status;
        this.description = description;
        this.startTime = startTime;
        this.duration= duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {

        return "Task{" +
                "\n\t title= " + title +
                "\n\t description= " + description +
                "\n\t id= " + id +
                "\n\t status= " + status +
                "\n\t startTime= " + startTime +
                "\n\t duration= " + duration +
                "\n\t}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Напишите метод сохранения задачи в строку String toString(Task task) или переопределите базовый.
    public String toString(Task task) {
        return String.format("%d,%s,%s,%s,%s,%s,%s,%s\r\n", task.id,task.enumTask, task.title,task.status,
                task.description,"",task.startTime, task.duration);
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
        LocalDateTime startTime =  LocalDateTime.parse(strArray[4]);
        Duration duration = Duration.parse(strArray[5]);
        return new Task(id, enumTask, strArray[2], status,strArray[4], startTime, duration);
    }

    /** время завершения задачи, которое рассчитывается исходя из startTime и duration
     * @return LocalDateTime
     */
    public  LocalDateTime getEndTime() {
        return  startTime.plus(duration);
    }

    public  LocalDateTime getStartTime() {
        return  startTime;
    }

    public  Duration getDuration() {
        return  duration;
    }
}
