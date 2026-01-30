import java.time.LocalDate;
import java.time.LocalDateTime;

public class Deadline extends Task {
    private LocalDate date;
    private LocalDateTime datetime;

    public Deadline(String inTaskName, LocalDateTime inDatetime) {
        super(inTaskName);
        datetime = inDatetime;
        date = null;
    }

    public Deadline(String inTaskName, LocalDate inDate) {
        super(inTaskName);
        date = inDate;
        datetime = null;
    }

    public String getDeadlineDueBy() {
        return hasTime()
                ? datetime.toString()
                : date.toString();
    }

    private boolean hasTime() {
        return datetime != null;
    }

    @Override
    public String toString() {
        if (hasTime()) {
            return "[D]" + super.toString() + " (by: " + DateTimeUtil.parseDateTimeDisplay(datetime) + ")";
        } else {
            return "[D]" + super.toString() + " (by: " + DateTimeUtil.parseDateDisplay(date) + ")";
        }
    }
}