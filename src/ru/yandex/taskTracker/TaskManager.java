package ru.yandex.taskTracker;

import java.util.ArrayList;
import java.util.List;

import ru.yandex.taskTracker.model.*;
public interface TaskManager {

    public int generateId();

    public void createTask(Task task);

    public void createSubTask(SubTask subtask);

    public void createEpic(Epic epic);

    public ArrayList<Task> getAllListTask();

    public ArrayList<SubTask> getAllListSubTask();

    public ArrayList<Epic> getAllListEpic();

    public Task getTaskById(int id);

    public SubTask getSubTaskById(int id);

    public Epic getEpicById(int id);

    public void clearAllTask();

    public void clearAllSubTask();

    public void clearAllEpic();

    public void updateTask(Task task);

    public void updateSubTask(SubTask subTusk);

    public void updateEpic(Epic epic);

    public void removeTaskById(int id);

    public void removeSubTaskById(int id);

    public void removeEpicById(int id);

    public ArrayList<SubTask> getSubTaskEpic(Epic epic);
    public List<Task> getHistory();

}
