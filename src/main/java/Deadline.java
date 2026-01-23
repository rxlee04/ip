public class Deadline extends Task {
    String datetime = null;

    public Deadline(String inTaskName, String inDatetime) {
        super(inTaskName);
        datetime = inDatetime;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + datetime + ")";
    }


}
