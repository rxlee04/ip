import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.Temporal;

public class DateTimeUtil {

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static final DateTimeFormatter DISPLAY_DATE_FORMATTER = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy");

    public static final DateTimeFormatter DISPLAY_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm");

    public static boolean isDateTime(String s) {
        // e.g. 2026-01-30T18:30 (contains 'T')
        return s != null && s.contains("T");
    }

    public static String parseDateDisplay(LocalDate d) {
        return d.format(DISPLAY_DATE_FORMATTER);
    }

    public static String parseDateTimeDisplay(LocalDateTime d) {
        return d.format(DISPLAY_DATE_TIME_FORMATTER);
    }

    public static Temporal parseDateOrDateTime(String dateStr, CommandType type) throws WooperException {
        // check if user gave date/datetime
        if (dateStr.isEmpty()) {
            if (type == CommandType.DEADLINE) {
                throw new WooperException("Woop! Please give a deadline!");
            } else if (type == CommandType.EVENT) {
                throw new WooperException("Woop! Please give event's start and/or end date!");
            }
        }

        // parse string to datetime
        try {
            return LocalDateTime.parse(dateStr, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            // if user give date only, parse string to date
            try {
                return LocalDate.parse(dateStr, DATE_FORMATTER);
            } catch (DateTimeParseException ex) {
                throw new WooperException(
                        "Woop! Date must be DD/MM/YYYY or DD/MM/YYYY HH:mm");
            }
        }
    }
}
