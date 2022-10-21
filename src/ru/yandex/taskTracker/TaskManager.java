package ru.yandex.taskTracker;

import java.util.ArrayList;
import java.util.List;

import ru.yandex.taskTracker.model.*;
public interface TaskManager {

     int generateId();

     void createTask(Task task);

     void createSubTask(SubTask subtask);

     void createEpic(Epic epic);

     ArrayList<Task> getAllListTask();

     ArrayList<SubTask> getAllListSubTask();

     ArrayList<Epic> getAllListEpic();

     Task getTaskById(int id);

     SubTask getSubTaskById(int id);

     Epic getEpicById(int id);

     void clearAllTask();

     void clearAllSubTask();

     void clearAllEpic();

     void updateTask(Task task);

     void updateSubTask(SubTask subTusk);

     void updateEpic(Epic epic);

     void removeTaskById(int id);

     void removeSubTaskById(int id);

     void removeEpicById(int id);

     ArrayList<SubTask> getSubTaskEpic(Epic epic);
     List<Task> getHistory();

}
