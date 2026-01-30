import java.time.LocalDate;
import java.time.LocalDateTime;

public class Event extends Task {
    private LocalDate startDate = null;
    private LocalDate endDate = null;
    private LocalDateTime startDateTime = null;
    private LocalDateTime endDateTime = null;

    public Event(String inTaskName, LocalDateTime inStartDT, LocalDateTime inEndDT) {
        super(inTaskName);
        startDateTime = inStartDT;
        endDateTime = inEndDT;
        startDate = null;
        endDate = null;
    }

    public Event(String inTaskName, LocalDate inStartDate, LocalDate inEndDate) {
        super(inTaskName);
        startDate = inStartDate;
        endDate = inEndDate;
        startDateTime = null;
        endDateTime = null;
    }

    public String getEventStart() {
        return hasTime()
                ? startDateTime.toString()
                : startDate.toString();
    }

    public String getEventEnd() {
        return hasTime()
                ? endDateTime.toString()
                : endDate.toString();
    }

    private boolean hasTime() {
        return startDateTime != null && endDateTime != null;
    }

    @Override
    public String toString() {
        if (hasTime()) {
            return "[E]" + super.toString() + " (from: "
                    + DateTimeUtil.parseDateTimeDisplay(startDateTime) + " to: "
                    + DateTimeUtil.parseDateTimeDisplay(endDateTime) + ")";
        } else {
            return "[E]" + super.toString() + " (from: "
                    + DateTimeUtil.parseDateDisplay(startDate) + " to: "
                    + DateTimeUtil.parseDateDisplay(endDate) + ")";
        }
    }
}
