package ru.yandex.taskTracker;

import ru.yandex.taskTracker.history.HistoryManager;
import ru.yandex.taskTracker.history.InMemoryHistoryManager;


import java.io.File;

class Managers {
    public static HistoryManager getDefaultHistory(){
        return new InMemoryHistoryManager();
    }
    public static TaskManager getDefault(){
        return new FileBackedTasksManager(new File("resources/pam"));
    }


}
