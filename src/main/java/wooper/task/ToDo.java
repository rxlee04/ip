package wooper.task;

/**
 * Represents a todo task without any date or time constraints.
 */
public class ToDo extends Task {

    /**
     * Creates a todo task with the specified description.
     *
     * @param inTaskName Description of the todo task.
     */
    public ToDo(String inTaskName) {
        super(inTaskName);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
