import java.util.ArrayList;

public class EpicTusk extends Tusk {
     protected ArrayList<Integer> subTasksId = new ArrayList<>();

    public  void setSubTasksId(int id) {
        subTasksId.add(id);
    }

    public EpicTusk(String name, String description) {
        super(name, description,TaskStatus.NEW);

    }


    public ArrayList<Integer> getSubTasksId() {
        return subTasksId;
    }
}




