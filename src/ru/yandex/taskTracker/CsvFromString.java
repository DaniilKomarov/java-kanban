package ru.yandex.taskTracker;


import ru.yandex.taskTracker.history.InMemoryHistoryManager;
import ru.yandex.taskTracker.model.Epic;
import ru.yandex.taskTracker.model.SubTask;
import ru.yandex.taskTracker.model.Task;
import ru.yandex.taskTracker.model.TaskStatus;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CsvFromString extends InMemoryTaskManager {


    protected  Task taskFromString (String line){
        String[] parameters = line.split(",");
        Task task = new Task(parameters[2],parameters[4], TaskStatus.valueOf(parameters[3]),
                Integer.valueOf(parameters[5]),null);
        if(!(parameters[6].equals("null"))){
            task.setStartTime(LocalDateTime.parse(parameters[6]));
        }
        int id = Integer.valueOf(parameters[0]);
        taskMap.put(id,task);
        task.setId(id);
        return task;
    }
    public SubTask subTaskFromString(String line) {
        String[] parameters = line.split(",");
        SubTask task = new SubTask(parameters[2], parameters[4], TaskStatus.valueOf(parameters[3]),
                Integer.valueOf(parameters[8]),Integer.valueOf(parameters[5]), null);
        if(!(parameters[7].equals("null"))){
            task.setStartTime(LocalDateTime.parse(parameters[6]));
        }
        int id = Integer.valueOf(parameters[0]);
        subTaskMap.put(id,task);
        task.setId(id);
        return task;
    }
    public Epic epicFromString(String line){

        String[] parameters = line.split(",");
        Epic task = new Epic(parameters[2],parameters[4], Integer.valueOf(parameters[5]),null);
        if(!(parameters[6].equals("null"))){
            task.setStartTime(LocalDateTime.parse(parameters[6]));
            task.setEndTime(LocalDateTime.parse(parameters[7]));
        }
        task.setStatus(TaskStatus.valueOf(parameters[3]));

        int id = Integer.valueOf(parameters[0]);
        epicMap.put(id,task);
        task.setId(id);
        return task;
    }
    public InMemoryHistoryManager getHistoryFromString(String line){
        InMemoryHistoryManager manager = new InMemoryHistoryManager();
        List<Integer> id = new ArrayList<>(historyFromString(line));
        for(Integer value : id){
            if(taskMap.containsKey(value)){
                manager.add(taskMap.get(value));
                }else if(subTaskMap.containsKey(value)){
                    manager.add(subTaskMap.get(value));
                }else if(epicMap.containsKey(value)){
                    manager.add(epicMap.get(value));
            }
        }
        return manager;
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
