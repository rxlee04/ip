package wooper.task;

/**
 * Represents a generic task with a description and completion status.
 */
public class Task {
    private String taskName = null;
    private boolean isDone = false;

    /**
     * Creates a task with the specified description.
     *
     * @param inTaskName Description of the task.
     */
    public Task(String inTaskName) {
        taskName = inTaskName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String inTaskName) {
        taskName = inTaskName;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    @Override
    public String toString() {
        String tString = isDone ? "[X]" : "[ ]";
        tString += " " + taskName;
        return tString;
    }
}
