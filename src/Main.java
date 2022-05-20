public class Main {

    public static void main(String[] args) {
        ManagerTusk manager = new ManagerTusk();
        Tusk tusk= new Tusk("Задача 1 ","Пам Пам", TaskStatus.NEW);
        EpicTusk tusk1 = new EpicTusk("Задача 2 ","Пам2 Пам2");
        SubTask tusk2 = new SubTask("Задача 1 ","Пам1 Пам1", TaskStatus.NEW);
        EpicTusk tusk3 = new EpicTusk("Задача 4 ","Пам4 Пам4");
        SubTask subtask = new SubTask("Задача 5 ","Пам5 Пам5", TaskStatus.IN_PROGRESS);
        SubTask tusk4 = new SubTask("Задача 7 ","Пам7 Пам7", TaskStatus.DONE);

        manager.createTusk(tusk);
        manager.createEpicTusk(tusk1);
        manager.createSubTusk(tusk2);
        manager.createEpicTusk(tusk3);
        manager.createSubTusk(subtask);
        manager.createSubTusk(tusk4);
        manager.removeSubTuskById(5);
        System.out.println(manager.getAllListEpicTuck());
        System.out.println(manager.getSubTuskEpic(tusk1));
        System.out.println(manager.getSubTuskEpic(tusk3));
        System.out.println(manager.getAllListEpicTuck());
    }
}
