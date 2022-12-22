package ru.yandex.taskTracker.file;

import ru.yandex.taskTracker.history.HistoryManager;
import ru.yandex.taskTracker.model.Epic;
import ru.yandex.taskTracker.model.SubTask;
import ru.yandex.taskTracker.model.Task;
import ru.yandex.taskTracker.InMemoryTaskManager;



import java.io.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager{
    private final File file;




    public FileBackedTasksManager(File file) {
        this.file=file;
    }

    @Override
    public   void createTask(Task task) {
        super.createTask(task);
        save();
    }

    @Override
    public void createSubTask(SubTask subtask) {
        super.createSubTask(subtask);
        save();
    }

    @Override
    public void createEpic(Epic epic) {
        super.createEpic(epic);
        save();
    }

    @Override
    public void clearAllTask() {
        super.clearAllTask();
        save();
    }

    @Override
    public void clearAllSubTask() {
        super.clearAllSubTask();
        save();
    }

    @Override
    public void clearAllEpic() {
        super.clearAllEpic();
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateSubTask(SubTask subTusk) {
        super.updateSubTask(subTusk);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void removeTaskById(int id) {
        super.removeTaskById(id);
        save();
    }

    @Override
    public void removeSubTaskById(int id) {
        super.removeSubTaskById(id);
        save();
    }

    @Override
    public void removeEpicById(int id) {
        super.removeEpicById(id);
        save();
    }

    @Override
    public Task getTaskById(int id) {
        save();
        return super.getTaskById(id);

    }

    @Override
    public SubTask getSubTaskById(int id) {
        save();
        return super.getSubTaskById(id);
    }

    @Override
    public Epic getEpicById(int id) {
        save();
        return super.getEpicById(id);
    }

    protected void save(){
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))){
            bufferedWriter.write("id,type,name,status,description,duration,startTime,endTime,epic");
            bufferedWriter.newLine();

            for(Task value : taskMap.values()){
                bufferedWriter.write(toStringTask(value));
                bufferedWriter.newLine();
            }
            for(Epic value : epicMap.values()){
                bufferedWriter.write(toStringEpic(value));
                bufferedWriter.newLine();
            }
            for(SubTask value : subTaskMap.values()){
                bufferedWriter.write(toStringSubTask(value));
                bufferedWriter.newLine();

            }


            bufferedWriter.newLine();
            bufferedWriter.newLine();
            bufferedWriter.write(historyToString(historyManager));

        }catch (IOException e){
             System.out.println(e);
        }catch (ManagerSaveException e){
            throw new ManagerSaveException("ManagerSaveException");
        }

    }
    public static FileBackedTasksManager loadFromFile(File file){
        FileBackedTasksManager taskManager = new FileBackedTasksManager(file);
        CsvFromString csvFromString = new CsvFromString();
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(file,StandardCharsets.UTF_8))){
            while(bufferedReader.ready()) {

                String line = bufferedReader.readLine();
                String[] parameters = line.split(",");
                if(line.isEmpty()){
                    continue;
                }else if (parameters[1].equals("TASK")) {
                   taskManager.addTask(csvFromString.taskFromString(line));

                } else if (parameters[1].equals("EPIC")) {
                    taskManager.addEpic(csvFromString.epicFromString(line));
                } else if (parameters[1].equals("SUBTASK")) {
                    taskManager.addSubTask(csvFromString.subTaskFromString(line));
                } else if (parameters[1].equals("type")) {
                    continue;
                }else {
                    taskManager.historyManager=csvFromString.getHistoryFromString(line);
                }

            }
        }catch (IOException e){
            System.out.println(e);
        }catch (ManagerSaveException ex){
            throw new ManagerSaveException("ManagerSaveException");
        }
        taskManager.setId(idOld);
        return taskManager;
    }
    private String toStringTask(Task task){
        String line = task.getId() + "," + task.getType() + "," + task.getName() + "," + task.getStatus() + ","
                + task.getDescription() + "," + task.getDuration() + "," + task.getStartTime() +"," + task.getEndTime();
        return line;
    }
    private String toStringSubTask(SubTask task){
        String line = task.getId() + "," + task.getType() + "," + task.getName() + "," + task.getStatus() + ","
                + task.getDescription() + "," + task.getDuration() + "," + task.getStartTime() + "," + task.getEndTime()
                + "," + task.getEpicId();
        return line;
    }
    private String toStringEpic(Epic task){
        String line = task.getId() + "," + task.getType() + "," + task.getName() + "," + task.getStatus() + ","
                + task.getDescription() + "," + task.getDuration() + "," + task.getStartTime() + "," +task.getEndTime();
        return line;
    }
    private static String historyToString(HistoryManager manager){
        List<Task> list = manager.getHistory();
        String prefix ="";
        StringBuilder br = new StringBuilder();
        for(Task task: list){
            br.append(prefix);
            prefix = ",";
            br.append(task.getId());
        }
        String historyString = br.toString();
        return historyString;
    }

    private static int idOld=0;
    private void addTask(Task task) {
        int id = task.getId();
        if(id>idOld){
            idOld=id;
        }
        taskMap.put(id,task);
    }


    private void addSubTask(SubTask subtask) {
        int id = subtask.getId();
        if(id>idOld){
            idOld=id;
        }
        subTaskMap.put(id,subtask);
    }


    private void addEpic(Epic epic) {
        int id =epic.getId();
        if(id>idOld){
            idOld=id;
        }
        epicMap.put(id,epic);
    }



}
