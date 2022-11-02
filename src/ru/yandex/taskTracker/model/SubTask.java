package ru.yandex.taskTracker.model;

public class SubTask extends Task {
     private int epicId;
    private TaskType type = TaskType.SUBTASK;

    public SubTask(String name, String description, TaskStatus status, int epicId) {

        super(name, description, status);
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
