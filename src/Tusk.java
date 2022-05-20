import java.util.Objects;

public class Tusk {
    private String name;
    private String description;
    protected  int id;
    private TaskStatus status;

    public Tusk(String name, String description, TaskStatus status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }


    public  int getId(){
        return id;
    }


    public void setId(int id) {

        this.id = id;
    }


    @Override
    public String toString() {
        return "Tusk{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tusk tusk = (Tusk) o;
        return Objects.equals(name, tusk.name)
                && Objects.equals(description, tusk.description)
                && status == tusk.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, status);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}
