package wooper.task;

import wooper.util.DateTimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents a task that occurs over a specific time period.
 */
public class Event extends Task {
    private LocalDate startDate = null;
    private LocalDate endDate = null;
    private LocalDateTime startDateTime = null;
    private LocalDateTime endDateTime = null;

    /**
     * Creates an event task with a specific start and end date-time.
     *
     * @param inTaskName Description of the event task.
     * @param inStartDT Start date and time of the event.
     * @param inEndDT End date and time of the event.
     */
    public Event(String inTaskName, LocalDateTime inStartDT, LocalDateTime inEndDT) {
        super(inTaskName);
        startDateTime = inStartDT;
        endDateTime = inEndDT;
        startDate = null;
        endDate = null;
    }

    /**
     * Creates an event task with a specific start and end date.
     *
     * @param inTaskName Description of the event task.
     * @param inStartDate Start date of the event.
     * @param inEndDate End date of the event.
     */
    public Event(String inTaskName, LocalDate inStartDate, LocalDate inEndDate) {
        super(inTaskName);
        startDate = inStartDate;
        endDate = inEndDate;
        startDateTime = null;
        endDateTime = null;
    }

    /**
     * Returns the start time or date of the event.
     *
     * @return The start date or date-time of the event.
     */
    public String getEventStart() {
        return hasTime()
                ? startDateTime.toString()
                : startDate.toString();
    }

    /**
     * Returns the end time or date of the event.
     *
     * @return The end date or date-time of the event.
     */
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
