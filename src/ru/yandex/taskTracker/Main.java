package ru.yandex.taskTracker;
import ru.yandex.taskTracker.model.*;
public class Main {

    public static void main(String[] args) {
        ManagerTusk manager = new ManagerTusk();
        Task task = new Task("Задача 1 ","Пам Пам", TaskStatus.NEW);
        Epic tusk1 = new Epic("Задача 2 ","Пам2 Пам2");
        SubTask tusk2 = new SubTask("Задача 1 ","Пам1 Пам1", TaskStatus.NEW);
        Epic tusk3 = new Epic("Задача 4 ","Пам4 Пам4");
        SubTask subtask = new SubTask("Задача 5 ","Пам5 Пам5", TaskStatus.IN_PROGRESS);
        SubTask tusk4 = new SubTask("Задача 7 ","Пам7 Пам7", TaskStatus.DONE);

        manager.createTask(task);
        manager.createEpic(tusk1);
        manager.createSubTask(tusk2);
        manager.createEpic(tusk3);
        manager.createSubTask(subtask);
        manager.createSubTask(tusk4);
        manager.removeSubTaskById(5);
        System.out.println(manager.getAllListEpic());
        System.out.println(manager.getSubTaskEpic(tusk1));
        System.out.println(manager.getSubTaskEpic(tusk3));
        System.out.println(manager.getAllListEpic());
    }
}
