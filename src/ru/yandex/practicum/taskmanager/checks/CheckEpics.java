package ru.yandex.practicum.taskmanager.checks;

import ru.yandex.practicum.taskmanager.manager.FileBackedTasksManager;
import ru.yandex.practicum.taskmanager.manager.TaskManager;

public class CheckEpics {

    /**
     * проверка Epics в старом и новом менеждерах
     * @param manager
     * @param fileBackedTasksManager
     */
    public static void chekEpics(TaskManager manager, FileBackedTasksManager fileBackedTasksManager) {
        String str ="";
        for (var epic:manager.getAllEpics()) {
            if (str.isEmpty()){
                str += String.format("%s",epic.getId());
            } else {
                str += String.format(", %s",epic.getId());
            }
        }
        System.out.println("Id эпиков (Epic) в старом менеджере: " + str);
        str ="";
        for (var epic:fileBackedTasksManager.getAllEpics()) {
            if (str.isEmpty()){
                str += String.format("%s",epic.getId());
            } else {
                str += String.format(", %s",epic.getId());
            }
        }
        System.out.println("Id эпиков (Epic) в новом менеджере: " + str);

    }
}
