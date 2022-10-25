package ru.yandex.taskTracker;

import ru.yandex.taskTracker.model.Epic;
import ru.yandex.taskTracker.model.SubTask;
import ru.yandex.taskTracker.model.Task;
import ru.yandex.taskTracker.model.TaskStatus;
import ru.yandex.taskTracker.InMemoryTaskManager;

import java.util.ArrayList;
import java.util.List;

public class CsvFromString extends InMemoryTaskManager {


    protected Task taskFromString (String line){
        String[] parameters = line.split(",");
        Task task = new Task(parameters[2],parameters[4], TaskStatus.valueOf(parameters[3]));
        int id = Integer.valueOf(parameters[0]);
        taskMap.put(id,task);
        task.setId(id);
        return task;
    }
    public SubTask subTaskFromString(String line) {
        String[] parameters = line.split(",");
        SubTask task = new SubTask(parameters[2], parameters[4], TaskStatus.valueOf(parameters[3]),
                Integer.valueOf(parameters[5]));
        int id = Integer.valueOf(parameters[0]);
        subTuskMap.put(id,task);
        task.setId(id);
        return task;
    }
    public Epic epicFromString(String line){
        String[] parameters = line.split(",");
        Epic task = new Epic(parameters[2],parameters[4]);
        int id = Integer.valueOf(parameters[0]);
        epicMap.put(id,task);
        task.setId(id);
        return task;
    }
    public void getHistoryFromString(String line){
        InMemoryHistoryManager manager = new InMemoryHistoryManager();
        List<Integer> id = new ArrayList<>(historyFromString(line));
        for(Integer value : id){
            if(taskMap.containsKey(value)){
                manager.add(taskMap.get(value));
                }else if(subTuskMap.containsKey(value)){
                    manager.add(subTuskMap.get(value));
                }else if(epicMap.containsKey(value)){
                    manager.add(epicMap.get(value));
            }
        }
    }
    private static List<Integer> historyFromString(String line){
        List<Integer> id = new ArrayList<>();
        String[] ids = line.split(",");
        for (int i=0;i< ids.length;i++){
            id.add(Integer.valueOf(ids[i]));
        }
        return id;
    }
}
