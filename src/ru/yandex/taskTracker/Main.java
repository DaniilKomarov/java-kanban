package ru.yandex.taskTracker;
import ru.yandex.taskTracker.model.*;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();


        Epic epic1 = new Epic("Задача 2 ","Пам2 Пам2");
        SubTask subTask1 = new SubTask("Задача 3 ","Пам1 Пам1", TaskStatus.NEW,1);
        Epic epic2 = new Epic("Задача 4 ","Пам4 Пам4");
        SubTask subtask2 = new SubTask("Задача 5 ","Пам5 Пам5", TaskStatus.IN_PROGRESS,3);
        SubTask subtask3 = new SubTask("Задача 6 ","Пам6 Пам6", TaskStatus.DONE,3);
        SubTask subtask4 = new SubTask("Задача 7 ","Пам7 Пам7", TaskStatus.DONE,3);

        manager.createEpic(epic1);
        manager.createSubTask(subTask1);
        manager.createEpic(epic2);
        manager.createSubTask(subtask2);
        manager.createSubTask(subtask3);
        manager.createSubTask(subtask4);
        manager.getEpicById(1);
        System.out.println(manager.getHistory());
        manager.getSubTaskById(2);
        System.out.println(manager.getHistory());
        manager.getEpicById(3);
        System.out.println(manager.getHistory());
        manager.getSubTaskById(4);
        System.out.println(manager.getHistory());
        manager.getSubTaskById(5);
        System.out.println(manager.getHistory());
        manager.getSubTaskById(6);
        System.out.println(manager.getHistory());
        manager.getSubTaskById(2);
        System.out.println(manager.getHistory());
        manager.getSubTaskById(5);
        System.out.println(manager.getHistory());
        manager.removeSubTaskById(5);
        System.out.println(manager.getHistory());
        manager.removeSubTaskById(2);
        System.out.println(manager.getHistory());

        FileBackedTasksManager manager1 = FileBackedTasksManager.loadFromFile(new File("resources/pam"));







    }
}
