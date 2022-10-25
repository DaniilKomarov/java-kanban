package ru.yandex.taskTracker;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ru.yandex.taskTracker.model.*;
public class InMemoryTaskManager implements TaskManager {
    protected HashMap<Integer, Task> taskMap = new HashMap<>();
    protected HashMap<Integer, SubTask> subTuskMap = new HashMap<>();
    protected HashMap<Integer, Epic> epicMap = new HashMap<>();
    protected HistoryManager historyManager = Managers.getDefaultHistory();

    private int id;

    @Override
    public int generateId(){
        id++;
        return id;
    }

    @Override
    public void createTask(Task task){
        id = generateId();
        task.setId(id);
        taskMap.put(id, task);

    }
    @Override
    public void createSubTask(SubTask subtask){
        id = generateId();
        subtask.setId(id);
        subTuskMap.put(id,subtask);
        addSubTaskId(subtask.getEpicId(),id);
        createStatusEpic(subtask.getEpicId());
    }
    @Override
    public void createEpic(Epic epic){
        id = generateId();
        epic.setId(id);
        epicMap.put(id, epic);
    }
    @Override
    public ArrayList<Task> getAllListTask() {

        return new ArrayList<>(taskMap.values());
    }
    @Override
    public ArrayList<SubTask> getAllListSubTask() {

        return new ArrayList<>(subTuskMap.values());
    }
    @Override
    public ArrayList<Epic> getAllListEpic() {

        return new ArrayList<>(epicMap.values());
    }

    @Override
    public Task getTaskById(int id) {
        historyManager.add(taskMap.get(id));
        return taskMap.get(id);
    }
    @Override
    public SubTask getSubTaskById(int id) {
        historyManager.add(subTuskMap.get(id));
        return subTuskMap.get(id);
    }
    @Override
    public Epic getEpicById(int id) {
        historyManager.add(epicMap.get(id));
        return epicMap.get(id);
    }
    @Override
    public void clearAllTask(){

        taskMap.clear();
    }
    @Override
    public void clearAllSubTask(){

        subTuskMap.clear();
    }
    @Override
    public void clearAllEpic(){

        epicMap.clear();
        subTuskMap.clear();
    }
    @Override
    public void updateTask(Task task){

        taskMap.put(task.getId(), task);
    }
    @Override
    public void updateSubTask(SubTask subTusk){

        taskMap.put(subTusk.getId(),subTusk);
    }
    @Override
    public void updateEpic(Epic epic){

        epicMap.put(epic.getId(), epic);
    }
    @Override
    public void removeTaskById(int id){
        historyManager.remove(id);
        taskMap.remove(id);
    }
    @Override
    public void removeSubTaskById(int id){
        historyManager.remove(id);
        int epicId=subTuskMap.get(id).getEpicId();
        Epic epic = epicMap.get(epicId);
        Integer id1 = id;
        epic.getSubTasksIds().remove(id1);
        subTuskMap.remove(id);
        createStatusEpic(epicId);
    }
    @Override
    public void removeEpicById(int id){

        Epic epic = epicMap.get(id);
        ArrayList<Integer> epicSubRemove = new ArrayList<>();
        epicSubRemove=epic.getSubTasksIds();
        for(Integer idSub:epicSubRemove){
            subTuskMap.remove(idSub);
            historyManager.remove(idSub);
        }
        historyManager.remove(id);
        epicMap.remove(id);
    }


    private void addSubTaskId(int epicId,int id){
        (epicMap.get(epicId)).setSubTasksIds(id);

    }
    @Override
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
    @Override
    public List<Task> getHistory(){
        return historyManager.getHistory();
    }

}
