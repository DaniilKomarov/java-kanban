package ru.yandex.taskTracker.model;

import java.util.ArrayList;

public class Epic extends Task {
     public ArrayList<Integer> subTasksIds = new ArrayList<>();

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




