public class ToDo extends Task {

    public ToDo(String inTaskName) {
        super(inTaskName);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
