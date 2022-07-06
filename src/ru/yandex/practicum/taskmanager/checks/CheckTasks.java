package ru.yandex.practicum.taskmanager.checks;

import ru.yandex.practicum.taskmanager.manager.FileBackedTasksManager;
import ru.yandex.practicum.taskmanager.manager.TaskManager;

public class CheckTasks {

    /**
     * проверка Tasks в старом и новом менеждерах
     * @param manager
     * @param fileBackedTasksManager
     */
    public static void chekTasks(TaskManager manager, FileBackedTasksManager fileBackedTasksManager) {
        String str ="";
        for (var task:manager.getAllTasks()) {
            if (str.isEmpty()){
                str += String.format("%s",task.getId());
            } else {
                str += String.format(", %s",task.getId());
            }
        }
        System.out.println("Id задач (Task) в старом менеджере: " + str);
        str ="";
        for (var task:fileBackedTasksManager.getAllTasks()) {
            if (str.isEmpty()){
                str += String.format("%s",task.getId());
            } else {
                str += String.format(", %s",task.getId());
            }
        }
        System.out.println("Id задач (Task) в новом менеджере: " + str);
    }
}
