package ru.yandex.taskTracker.model;

import ru.yandex.taskTracker.TaskStatus;

public class SubTask extends Task {
     int EpicId;

    public SubTask(String name, String description, TaskStatus status) {
        super(name, description, status);
    }

    public void setEpicId(int epicId) {
        EpicId = epicId;
    }

    public int getEpicId() {
        return EpicId;
    }
}
