public class Deadline extends Task {
    String datetime = null;

    public Deadline(String inTaskName, String inDatetime) {
        super(inTaskName);
        datetime = inDatetime;
    }

    public String getDatetime() {
        return datetime;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + datetime + ")";
    }
}