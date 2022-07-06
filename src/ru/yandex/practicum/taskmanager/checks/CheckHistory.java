package ru.yandex.practicum.taskmanager.checks;

import ru.yandex.practicum.taskmanager.manager.FileBackedTasksManager;
import ru.yandex.practicum.taskmanager.manager.TaskManager;

public class CheckHistory {

    /**
     * проверка Epics в старом и новом менеждерах
     * @param manager
     * @param fileBackedTasksManager
     */
    public static void chekHistory(TaskManager manager, FileBackedTasksManager fileBackedTasksManager) {
        String str ="";
        for (var task : manager.getHistoryManager().getHistory()) {
            if (str.isEmpty()){
                str += String.format("%s",task.getId());
            } else {
                str += String.format(", %s",task.getId());
            }
        }
        System.out.println("Id задач в старом HistoryManager: " + str);
        str ="";
        for (var task:fileBackedTasksManager.getHistoryManager().getHistory()) {
            if (str.isEmpty()){
                str += String.format("%s",task.getId());
            } else {
                str += String.format(", %s",task.getId());
            }
        }
        System.out.println("Id задач в новом HistoryManager: " + str);
    }
}
