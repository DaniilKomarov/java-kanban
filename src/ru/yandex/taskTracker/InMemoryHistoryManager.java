package ru.yandex.taskTracker;

import ru.yandex.taskTracker.model.Task;

import java.util.ArrayList;
import java.util.List;

 class InMemoryHistoryManager implements HistoryManager{
    private List<Task> taskHistory= new ArrayList<>();
    @Override
    public void add(Task task) {
        if(taskHistory.size() == 10) {
            taskHistory.remove(0);
        }
        taskHistory.add(task);
    }

    @Override
    public List<Task> getHistory() {
        return taskHistory;
    }

}
