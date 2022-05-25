package ru.yandex.taskTracker;
import ru.yandex.taskTracker.model.*;
public class Main {

    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();
        Task task = new Task("Задача 1 ","Пам Пам", TaskStatus.NEW);
        Epic epic1 = new Epic("Задача 2 ","Пам2 Пам2");
        SubTask subTask1 = new SubTask("Задача 3 ","Пам1 Пам1", TaskStatus.NEW,2);
        Epic epic2 = new Epic("Задача 4 ","Пам4 Пам4");
        SubTask subtask2 = new SubTask("Задача 5 ","Пам5 Пам5", TaskStatus.IN_PROGRESS,4);
        SubTask subtask3 = new SubTask("Задача 7 ","Пам7 Пам7", TaskStatus.DONE,4);

        manager.createTask(task);
        manager.createEpic(epic1);
        manager.createSubTask(subTask1);
        manager.createEpic(epic2);
        manager.createSubTask(subtask2);
        manager.createSubTask(subtask3);
        manager.getEpicById(2);
        manager.getTaskById(1);
        manager.getSubTaskById(3);
        System.out.println(manager.getHistory());
        System.out.println(manager.getSubTaskEpic(epic2));
        System.out.println(manager.getEpicById(2));
        System.out.println(manager.getEpicById(4));
        System.out.println(manager.getHistory());
    }
}
