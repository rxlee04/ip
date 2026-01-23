public class Event extends Task {
    String startDT = null;
    String endDT = null;

    public Event(String inTaskName, String inStartDT, String inEndDT) {
        super(inTaskName);
        startDT = inStartDT;
        endDT = inEndDT;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + startDT + " to: " + endDT + ")";
    }
}
