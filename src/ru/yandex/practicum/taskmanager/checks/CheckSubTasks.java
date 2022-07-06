package ru.yandex.practicum.taskmanager.checks;

import ru.yandex.practicum.taskmanager.manager.FileBackedTasksManager;
import ru.yandex.practicum.taskmanager.manager.TaskManager;

public class CheckSubTasks {

    /**
     *  проверка SubTasks в старом и новом менеждерах
     * @param manager
     * @param fileBackedTasksManager
     */
    public static void chekSubTasks(TaskManager manager, FileBackedTasksManager fileBackedTasksManager) {
        String str ="";
        for (var subTask:manager.getAllSubtasks()) {
            if (str.isEmpty()){
                str += String.format("%s",subTask.getId());
            } else {
                str += String.format(", %s",subTask.getId());
            }
        }
        System.out.println("Id подзадач (SubTask) в старом менеджере: " + str);
        str ="";
        for (var subTask : fileBackedTasksManager.getAllSubtasks()) {
            if (str.isEmpty()){
                str += String.format("%s",subTask.getId());
            } else {
                str += String.format(", %s",subTask.getId());
            }
        }
        System.out.println("Id подзадач (SubTask) в новом менеджере: " + str);
    }
}
