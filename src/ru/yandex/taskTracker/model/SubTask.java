package ru.yandex.taskTracker.model;

import java.time.LocalDateTime;

public class SubTask extends Task {
     private int epicId;
    private TaskType type = TaskType.SUBTASK;

    public SubTask(String name, String description, TaskStatus status, int epicId,Integer duration,
                   LocalDateTime startTime) {

        super(name, description, status,duration,startTime);
        this.epicId =epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public TaskType getType() {
        return type;
    }
}
