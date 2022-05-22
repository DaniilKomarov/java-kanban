package ru.yandex.taskTracker;

import java.util.ArrayList;
import java.util.HashMap;
import ru.yandex.taskTracker.model.*;
public class ManagerTusk {
    private HashMap<Integer, Task> taskMap = new HashMap<>();
    private HashMap<Integer, SubTask> subTuskMap = new HashMap<>();
    private HashMap<Integer, Epic> epicMap = new HashMap<>();
    private int id;
    private int epicId; // можно ее оставить? также получается вроде удобнее или это получается дублирование ?
    //не получится отсылаться к прошлому айди т.к. может быть подряд несколько саб тасков
    //  если вызывать при созаднии саб таска Epic.getId тогда айди должно быть статично
    // или при создании эпика сетер , то тоже статической
    public int generateId(){
        id++;
        return id;
    }


    public void createTask(Task task){
        id = generateId();
        task.setId(id);
        taskMap.put(id, task);

    }

    public void createSubTask(SubTask subtask){
        id = generateId();
        subtask.setId(id);
        subTuskMap.put(id,subtask);
        subtask.setEpicId(epicId);
        addSubTaskId(epicId,id);
        createStatusEpic(epicId);
    }

    public void createEpic(Epic epic){
        id = generateId();
        epic.setId(id);
        epicMap.put(id, epic);
        epicId = epic.getId();
    }

    public ArrayList<Task> getAllListTask() {

        return new ArrayList<>(taskMap.values());
    }

    public ArrayList<SubTask> getAllListSubTask() {

        return new ArrayList<>(subTuskMap.values());
    }

    public ArrayList<Epic> getAllListEpic() {

        return new ArrayList<>(epicMap.values());
    }


    public Task getTaskById(int id) {

        return taskMap.get(id);
    }

    public SubTask getSubTaskById(int id) {

        return subTuskMap.get(id);
    }

    public Epic getEpicById(int id) {

        return epicMap.get(id);
    }

    public void clearAllTask(){

        taskMap.clear();
    }
    public void clearAllSubTask(){

        subTuskMap.clear();
    }
    public void clearAllEpic(){

        epicMap.clear();
        subTuskMap.clear();
    }

    public void updateTask(Task task){

        taskMap.put(task.getId(), task);
    }

    public void updateSubTask(SubTask subTusk){

        taskMap.put(subTusk.getId(),subTusk);
    }

    public void updateEpic(Epic epic){

        epicMap.put(epic.getId(), epic);
    }
    public void removeTaskById(int id){

        taskMap.remove(id);
    }
    public void removeSubTaskById(int id){
        epicId=subTuskMap.get(id).getEpicId();
        Epic epic = epicMap.get(epicId);
        Integer id1 = id;
        epic.subTasksIds.remove(id1);
        subTuskMap.remove(id);
        createStatusEpic(epicId);
    }
    public void removeEpicById(int id){
        Epic epic = epicMap.get(id);
        ArrayList<Integer> epicSubRemove = new ArrayList<>();
        epicSubRemove=epic.getSubTasksIds();
        for(Integer idSub:epicSubRemove){
            subTuskMap.remove(idSub);
        }
        epicMap.remove(id);
    }


    private void addSubTaskId(int epicId,int id){
        (epicMap.get(epicId)).setSubTasksIds(id);

    }
    public ArrayList<SubTask> getSubTaskEpic(Epic epic){
        ArrayList<SubTask> subTasks = new ArrayList<>();
        for (Integer subId:epic.getSubTasksIds()){
            subTasks.add(subTuskMap.get(subId));
        }
        return subTasks;
    }
    private void createStatusEpic(int epicId){
        Epic epic = epicMap.get(epicId);
        ArrayList<Integer> epicSub = new ArrayList<>();
        epicSub=epic.getSubTasksIds();
        ArrayList<TaskStatus> subTasks = new ArrayList<>();
        for(Integer idSub:epicSub){
            TaskStatus status =  subTuskMap.get(idSub).getStatus();
            subTasks.add(status);
        }

        if ((subTasks.contains(TaskStatus.NEW)) & (!subTasks.contains(TaskStatus.DONE)) & (!subTasks.contains(TaskStatus.IN_PROGRESS)) ) {
            epic.setStatus(TaskStatus.NEW);
        } else if ((!subTasks.contains(TaskStatus.NEW)) & (subTasks.contains(TaskStatus.DONE)) & (!subTasks.contains(TaskStatus.IN_PROGRESS))){
            epic.setStatus(TaskStatus.DONE);
        } else{
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }

    }

}
