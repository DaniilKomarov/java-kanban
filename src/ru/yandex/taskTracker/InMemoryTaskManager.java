package ru.yandex.taskTracker;
import java.util.*;
import java.util.TreeSet;
import java.util.Set;

import ru.yandex.taskTracker.history.HistoryManager;
import ru.yandex.taskTracker.model.*;
public class InMemoryTaskManager implements TaskManager {
    protected HashMap<Integer, Task> taskMap = new HashMap<>();
    protected HashMap<Integer, SubTask> subTaskMap = new HashMap<>();
    protected HashMap<Integer, Epic> epicMap = new HashMap<>();
    protected HistoryManager historyManager = Managers.getDefaultHistory();

    private int id;
    public void setId(int id) {
        this.id = id;
    }
    private Comparator<Task> comparator = new Comparator<Task>() {
        @Override
        public int compare(Task o1, Task o2) {
            if (o1.getId()==o2.getId()) {
                return 0;
            }
            if(o1.getStartTime() == null ){
                return 2;
            }
            else if (o2.getStartTime() == null){
                return -1;
            }
             else if (o1.getStartTime().isBefore(o2.getStartTime())) {
                return -1;
            }else{
                return 1;
            }
        }
        };
    protected Set<Task> prioritizedTasks = new TreeSet<>(comparator);

    @Override
    public int generateId(){
        id++;
        return id;
    }

    @Override
    public void createTask(Task task){
        int check = checkIntersection(task);
        if(check == 1){
            return;
        }
        id = generateId();
        task.setId(id);
        taskMap.put(id, task);
        prioritizedTasks.add(task);

    }
    @Override
    public void createSubTask(SubTask subtask){
        int check = checkIntersection(subtask);
        if(check == 1){
            return;
        }
        id = generateId();
        subtask.setId(id);
        subTaskMap.put(id,subtask);
        addSubTaskId(subtask.getEpicId(),id);
        createStatusEpic(subtask.getEpicId());
        createEpicTime(subtask.getEpicId());
        prioritizedTasks.add(subtask);

    }
    @Override
    public void createEpic(Epic epic){
        int check = checkIntersection(epic);
        if(check == 1){
            return;
        }
        id = generateId();
        epic.setId(id);
        epicMap.put(id, epic);
        prioritizedTasks.add(epic);
    }
    @Override
    public ArrayList<Task> getAllListTask() {

        return new ArrayList<>(taskMap.values());
    }
    @Override
    public ArrayList<SubTask> getAllListSubTask() {

        return new ArrayList<>(subTaskMap.values());
    }
    @Override
    public ArrayList<Epic> getAllListEpic() {

        return new ArrayList<>(epicMap.values());
    }

    @Override
    public Task getTaskById(int id) {
        if (taskMap.containsKey(id)) {
            historyManager.add(taskMap.get(id));
            return taskMap.get(id);
        }
        return null;
    }
    @Override
    public SubTask getSubTaskById(int id) {
        if(subTaskMap.containsKey(id)) {
            historyManager.add(subTaskMap.get(id));
            return subTaskMap.get(id);
        }
        return null;
    }
    @Override
    public Epic getEpicById(int id) {
        if(epicMap.containsKey(id)) {
            historyManager.add(epicMap.get(id));
            return epicMap.get(id);
        }
        return null;
    }
    @Override
    public void clearAllTask(){

        taskMap.clear();
    }
    @Override
    public void clearAllSubTask(){

        subTaskMap.clear();
        for(Epic epic:epicMap.values()){
            epic.getSubTasksIds().clear();
        }
    }
    @Override
    public void clearAllEpic(){

        epicMap.clear();
        subTaskMap.clear();
    }
    @Override
    public void updateTask(Task task){
        int id = task.getId();
        prioritizedTasks.remove(taskMap.get(id));
        int check = checkIntersection(task);
        if(check == 1){
            return;
        }
        taskMap.put(task.getId(), task);
        prioritizedTasks.add(task);
    }
    @Override
    public void updateSubTask(SubTask subTask){
        int id = subTask.getId();
        prioritizedTasks.remove(subTaskMap.get(id));
        int check = checkIntersection(subTask);
        if(check == 1){
            return;
        }
        subTaskMap.put(subTask.getId(),subTask);
        prioritizedTasks.add(subTask);
    }
    @Override
    public void updateEpic(Epic epic){
        int id = epic.getId();
        prioritizedTasks.remove(epicMap.get(id));
        int check = checkIntersection(epic);
        if(check == 1){
            return;
        }
        epicMap.put(epic.getId(), epic);
        prioritizedTasks.add(epicMap.get(id));
    }
    @Override
    public void removeTaskById(int id){
        historyManager.remove(id);
        prioritizedTasks.remove(taskMap.get(id));
        taskMap.remove(id);

    }
    @Override
    public void removeSubTaskById(int id){
        historyManager.remove(id);
        int epicId= subTaskMap.get(id).getEpicId();
        Epic epic = epicMap.get(epicId);
        Integer id1 = id;
        epic.getSubTasksIds().remove(id1);
        prioritizedTasks.remove(subTaskMap.get(id));
        subTaskMap.remove(id);
        createStatusEpic(epicId);
        createEpicTime(epicId);

    }
    @Override
    public void removeEpicById(int id){

        Epic epic = epicMap.get(id);
        ArrayList<Integer> epicSubRemove = new ArrayList<>();
        epicSubRemove=epic.getSubTasksIds();
        for(Integer idSub:epicSubRemove){
            prioritizedTasks.remove(getSubTaskById(idSub));
            subTaskMap.remove(idSub);
            historyManager.remove(idSub);
        }
        historyManager.remove(id);
        prioritizedTasks.remove(epic);
        epicMap.remove(id);
    }


    private void addSubTaskId(int epicId,int id){
        (epicMap.get(epicId)).setSubTasksIds(id);

    }
    @Override
    public ArrayList<SubTask> getSubTaskEpic(Epic epic){
        ArrayList<SubTask> subTasks = new ArrayList<>();
        for (Integer subId:epic.getSubTasksIds()){
            subTasks.add(subTaskMap.get(subId));
        }
        return subTasks;
    }
    private void createStatusEpic(int epicId){
        Epic epic = epicMap.get(epicId);
        ArrayList<Integer> epicSub = new ArrayList<>();
        epicSub=epic.getSubTasksIds();
        ArrayList<TaskStatus> subTasks = new ArrayList<>();
        for(Integer idSub:epicSub){
            TaskStatus status =  subTaskMap.get(idSub).getStatus();
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
    @Override
    public Set<Task> getPrioritizedTasks(){
        return prioritizedTasks;
    }
    private void createEpicTime(int epicId){
        Epic epic = epicMap.get(epicId);
        Integer duration = epic.getDuration();
        prioritizedTasks.remove(epic);
        epic.setEndTime(null);
        epic.setStartTime(null);
        for(Integer idSub:epic.getSubTasksIds()) {
            SubTask subTask = subTaskMap.get(idSub);
            duration += subTask.getDuration();
            if (epic.getStartTime() == null) {
                epic.setStartTime(subTask.getStartTime());
            } else if (subTask.getStartTime().isBefore(epic.getStartTime())) {
                epic.setStartTime(subTask.getStartTime());
            }
            if (epic.getEndTime() == null) {
                epic.setEndTime(subTask.getEndTime());
            } else if (epic.getEndTime().isBefore(subTask.getEndTime())) {
                epic.setEndTime(subTask.getEndTime());
            }
        }
        epic.setDuration(duration);
        prioritizedTasks.add(epic);
    }
    private int checkIntersection(Task task){
        for(Task taskTime:prioritizedTasks ){
            if (taskTime.getStartTime()==null || task.getStartTime()==null){
                break;
            }
            if (taskTime.getStartTime().isBefore(task.getStartTime())
                    &taskTime.getEndTime().isAfter(task.getEndTime())){
                return 1;
            } else if (taskTime.getStartTime().isAfter(task.getStartTime())
                    &taskTime.getEndTime().isBefore(task.getEndTime())) {
                return 1;
            }
        }
        return 0;
    }


}
