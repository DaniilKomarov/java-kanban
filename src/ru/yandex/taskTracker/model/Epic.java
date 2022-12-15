package ru.yandex.taskTracker.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Epic extends Task {
    private LocalDateTime endTime;

    protected ArrayList<Integer> subTasksIds = new ArrayList<>();
     private TaskType type = TaskType.EPIC;

    @Override
    public TaskType getType() {
        return type;
    }

    public  void setSubTasksIds(int id) {
        subTasksIds.add(id);
    }

    public Epic(String name, String description,Integer duration,LocalDateTime startTime) {
        super(name, description, TaskStatus.NEW,duration,startTime);


    }


    public ArrayList<Integer> getSubTasksIds() {
        return subTasksIds;
    }

    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}




