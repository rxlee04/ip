package wooper.task;

import java.time.LocalDate;
import java.time.LocalDateTime;

import wooper.util.DateTimeUtil;


/**
 * Represents a task that must be completed by a specific date or date-time.
 */
public class Deadline extends Task {
    private LocalDate date;
    private LocalDateTime datetime;

    /**
     * Creates a deadline task with a specific date and time.
     *
     * @param inTaskName Description of the deadline task.
     * @param inDatetime Date and time by which the task must be completed.
     */
    public Deadline(String inTaskName, LocalDateTime inDatetime) {
        super(inTaskName);
        datetime = inDatetime;
        date = null;
    }

    /**
     * Creates a deadline task with a specific date.
     *
     * @param inTaskName Description of the deadline task.
     * @param inDate Date by which the task must be completed.
     */
    public Deadline(String inTaskName, LocalDate inDate) {
        super(inTaskName);
        date = inDate;
        datetime = null;
    }

    public void setDeadline(LocalDateTime inDatetime) {
        datetime = inDatetime;
        date = null;
    }

    /**
     * Creates a deadline task with a specific date.
     *
     * @param inDate Date by which the task must be completed.
     */
    public void setDeadline(LocalDate inDate) {
        date = inDate;
        datetime = null;
    }

    /**
     * Returns the deadline of the task as a string.
     *
     * @return The deadline date or date-time in string form.
     */
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
