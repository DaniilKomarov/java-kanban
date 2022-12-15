package ru.yandex.taskTracker;
import ru.yandex.taskTracker.model.*;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();

        Task task1 = new Task("Task1","gfg",TaskStatus.NEW,30,null);
        Epic epic1 = new Epic("Задача 2 ","Пам2 Пам2",0,
                null);
        SubTask subTask1 = new SubTask("Задача 3 ","Пам1 Пам1", TaskStatus.NEW,2,50,
                LocalDateTime.of(2122,1,1,0,0));
        Epic epic2 = new Epic("Задача 4 ","Пам4 Пам4",0,
                null);
        SubTask subtask2 = new SubTask("Задача 5 ","Пам5 Пам5", TaskStatus.IN_PROGRESS,4,
                50, LocalDateTime.of(2032,2,2,0,20));
        SubTask subtask3 = new SubTask("Задача 6 ","Пам6 Пам6", TaskStatus.DONE,4,50,
                LocalDateTime.of(2022,2,2,0,20));
        SubTask subtask4 = new SubTask("Задача 7 ","Пам7 Пам7", TaskStatus.DONE,4,40,
                LocalDateTime.of(2022,2,2,0,21));
        SubTask subtask5 = new SubTask("Задача 7 ","Пам7 Пам7", TaskStatus.DONE,4,40,
                LocalDateTime.of(2222,2,2,0,21));

        manager.createTask(task1);
        manager.getTaskById(1);
        manager.createEpic(epic1);
        manager.createSubTask(subTask1);
        manager.createEpic(epic2);
        manager.createSubTask(subtask2);
        manager.createSubTask(subtask3);
        manager.createSubTask(subtask4);
        manager.getEpicById(2);
        System.out.println(manager.getHistory());
        manager.getSubTaskById(3);
        System.out.println(manager.getHistory());
        manager.getEpicById(4);
        System.out.println(manager.getHistory());
        manager.getSubTaskById(5);
        System.out.println(manager.getHistory());
        manager.getSubTaskById(6);
        System.out.println(manager.getHistory());
        System.out.println(manager.getHistory());
        manager.getSubTaskById(3);
        System.out.println(manager.getHistory());
        manager.getSubTaskById(6);
        System.out.println(manager.getHistory());


        System.out.println(manager.getPrioritizedTasks());





        FileBackedTasksManager manager1 = FileBackedTasksManager.loadFromFile(new File("resources/pam"));
        System.out.println(manager1.getAllListTask());
        System.out.println(manager1.getHistory());
        manager1.createSubTask(subtask5);
        System.out.println(manager1.getAllListEpic());
        System.out.println(manager1.getSubTaskById(6));







    }
}
