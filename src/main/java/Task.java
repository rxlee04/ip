public class Task {
    private String taskName = null;
    private boolean isDone = false;

    public Task(String inTaskName) {
        taskName = inTaskName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    @Override
    public String toString() {
        String tString = isDone?"[X]":"[ ]";
        tString += " " + taskName;
        return tString;
    }
}
