package ru.yandex.taskTracker.http;
import ru.yandex.taskTracker.Managers;
import ru.yandex.taskTracker.file.FileBackedTasksManager;
import ru.yandex.taskTracker.adapter.*;
import ru.yandex.taskTracker.model.*;

import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HttpTaskManager extends FileBackedTasksManager {
    private final KVTaskClient client;
    private final Gson gson;
    public HttpTaskManager(int port) {
        super(null);
        client = new KVTaskClient(port);
        gson = Managers.getDefaultGson();

    }
    @Override
    protected void save(){
        String jsonTasks = gson.toJson(new ArrayList<>(taskMap.values()));
        client.put("tasks",jsonTasks);
        String jsonEpics = gson.toJson(new ArrayList<>(epicMap.values()));
        client.put("epics",jsonEpics);
        String jsonSubTasks = gson.toJson(new ArrayList<>(subTaskMap.values()));
        client.put("subtasks",jsonSubTasks);
        String jsonHistory = gson.toJson(new ArrayList<>(historyManager.getHistory()));
        client.put("history",jsonHistory);
    }
    public void load(){
        ArrayList<Task> tasks = gson.fromJson(client.load("tasks"),new TypeToken<ArrayList<Task>>() {}.getType());
        addTasks(tasks);
        ArrayList<Epic> epics = gson.fromJson(client.load("epics"),new TypeToken<ArrayList<Epic>>() {}.getType());
        addEpics(epics);
        ArrayList<SubTask> subTasks = gson.fromJson(client.load("subtasks"),
                new TypeToken<ArrayList<SubTask>>() {}.getType());
        addSubTasks(subTasks);
        ArrayList<Task> history = gson.fromJson(client.load("history"),new TypeToken<ArrayList<Task>>() {}.getType());
        for(Task task : history) {
            historyManager.add(task);
        }
        setId(id);

    }
    private int id = 0;
    private void addTasks(List<Task> tasks){
        for (Task task:tasks){
            taskMap.put(task.getId(),task);
            if(id<task.getId()){
                id=task.getId();
            }
        }
    }
    private void addSubTasks(List<SubTask> subTasks){
        for (SubTask subTask:subTasks){
            subTaskMap.put(subTask.getId(),subTask);
            if(id<subTask.getId()){
                id=subTask.getId();
            }
        }
    }
    private void addEpics(List<Epic> epics){
        for (Epic epic:epics){
            epicMap.put(epic.getId(),epic);
            if(id<epic.getId()){
                id=epic.getId();
            }
        }
    }

}
