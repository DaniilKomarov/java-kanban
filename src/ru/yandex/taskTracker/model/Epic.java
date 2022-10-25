package ru.yandex.taskTracker.model;

import ru.yandex.taskTracker.TaskType;

import java.util.ArrayList;

public class Epic extends Task {
     protected ArrayList<Integer> subTasksIds = new ArrayList<>();
     private TaskType type = TaskType.EPIC;

    @Override
    public TaskType getType() {
        return type;
    }

    public  void setSubTasksIds(int id) {
        subTasksIds.add(id);
    }

    public Epic(String name, String description) {
        super(name, description, TaskStatus.NEW);

    }


    public ArrayList<Integer> getSubTasksIds() {
        return subTasksIds;
    }
}




