package ru.yandex.taskTracker;

import java.io.File;

class Managers {
    public static HistoryManager getDefaultHistory(){
        return new InMemoryHistoryManager();
    }
    public static TaskManager getDefault(){
        return new FileBackedTasksManager(new File("resources/pam"));
    }


}
