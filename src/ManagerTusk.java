import java.util.ArrayList;
import java.util.HashMap;

public class ManagerTusk {
    private HashMap<Integer,Tusk> tuskMap = new HashMap<>();
    private HashMap<Integer, SubTask> subTuskMap = new HashMap<>();
    private HashMap<Integer,EpicTusk> epicTuskMap = new HashMap<>();
    private int id;
    private int epicId;
    public int createId(){
        id++;
        return id;
    }


    public void createTusk(Tusk tusk){

        tuskMap.put(createId(), tusk);
        tusk.setId(id);
    }

    public void createSubTusk(SubTask subtask){

        subTuskMap.put(createId(),subtask);
        subtask.setId(id);
        subtask.setEpicId(epicId);
        addSubTuskId(epicId,id);
        createStatusEpic(epicId);
    }

    public void createEpicTusk(EpicTusk epicTusk){

        epicTuskMap.put(createId(),epicTusk);
        epicTusk.setId(id);
        epicId = epicTusk.getId();
    }

    public ArrayList<Tusk> getAllListTusk() {

        return new ArrayList<>(tuskMap.values());
    }

    public ArrayList<SubTask> getAllListSubTusk() {

        return new ArrayList<>(subTuskMap.values());
    }

    public ArrayList<EpicTusk> getAllListEpicTuck() {

        return new ArrayList<>(epicTuskMap.values());
    }


    public Tusk getListTuskById(int id) {

        return tuskMap.get(id);
    }

    public SubTask getListSubTuskById(int id) {

        return subTuskMap.get(id);
    }

    public EpicTusk getListEpicTuckById(int id) {

        return epicTuskMap.get(id);
    }

    public void clearAllTusk(){

        tuskMap.clear();
    }
    public void clearAllSubTusk(){

        subTuskMap.clear();
    }
    public void clearAllEpicTusk(){

        epicTuskMap.clear();
        subTuskMap.clear();
    }

    public void updateTusk(Tusk tusk){

        tuskMap.put(tusk.getId(),tusk);
    }

    public void updateTusk(SubTask subTusk){

        tuskMap.put(subTusk.getId(),subTusk);
    }

    public void updateEpicTusk(EpicTusk epicTusk){

        epicTuskMap.put(epicTusk.getId(),epicTusk);
    }
    public void removeTuskById(int id){

        tuskMap.remove(id);
    }
    public void removeSubTuskById(int id){
        epicId=subTuskMap.get(id).getEpicId();
        EpicTusk epic = epicTuskMap.get(epicId);
        Integer id1 = id;
        Object idObject = id1;
        epic.subTasksId.remove(idObject);
        subTuskMap.remove(id);
        createStatusEpic(epicId);
    }
    public void removeEpicTuskById(int id){
        EpicTusk epic = epicTuskMap.get(id);
        ArrayList<Integer> epicSubRemove = new ArrayList<>();
        epicSubRemove=epic.getSubTasksId();
        for(Integer idSub:epicSubRemove){
            subTuskMap.remove(idSub);
        }
        epicTuskMap.remove(id);
    }


    private void addSubTuskId(int epicId,int id){
        (epicTuskMap.get(epicId)).setSubTasksId(id);

    }
    public ArrayList<SubTask> getSubTuskEpic(EpicTusk epic){
        ArrayList<SubTask> subTasks = new ArrayList<>();
        for (Integer subId:epic.getSubTasksId()){
            subTasks.add(subTuskMap.get(subId));
        }
        return subTasks;
    }
    private void createStatusEpic(int epicId){
        EpicTusk epic = epicTuskMap.get(epicId);
        ArrayList<Integer> epicSub = new ArrayList<>();
        epicSub=epic.getSubTasksId();
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
