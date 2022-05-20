public class SubTask extends Tusk{
     int EpicId;

    public SubTask(String name, String description, TaskStatus status) {
        super(name, description, status);
    }

    public void setEpicId(int epicId) {
        EpicId = epicId;
    }

    public int getEpicId() {
        return EpicId;
    }
}
