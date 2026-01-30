package wooper.util;

import wooper.enums.CommandType;
import wooper.exception.WooperException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.Temporal;

/**
 * Provides utility methods for parsing and formatting dates and date-times.
 */
public class DateTimeUtil {

    /** Formatter for parsing dates in the format DD/MM/YYYY. */
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /** Formatter for parsing date-times in the format DD/MM/YYYY HH:mm. */
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    /** Formatter for displaying dates in a user-friendly format. */
    public static final DateTimeFormatter DISPLAY_DATE_FORMATTER = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy");

    /** Formatter for displaying date-times in a user-friendly format. */
    public static final DateTimeFormatter DISPLAY_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm");

    /**
     * Returns whether the given string represents a date-time value.
     *
     * @param s Input string to be checked.
     * @return {@code true} if the string represents a date-time, {@code false} otherwise.
     */
    public static boolean isDateTime(String s) {
        // e.g. 2026-01-30T18:30 (contains 'T')
        return s != null && s.contains("T");
    }

    /**
     * Returns a formatted string representation of the specified date.
     *
     * @param d Date to be formatted.
     * @return A user-friendly string representation of the date.
     */
    public static String parseDateDisplay(LocalDate d) {
        return d.format(DISPLAY_DATE_FORMATTER);
    }

    /**
     * Returns a formatted string representation of the specified date-time.
     *
     * @param d Date-time to be formatted.
     * @return A user-friendly string representation of the date-time.
     */
    public static String parseDateTimeDisplay(LocalDateTime d) {
        return d.format(DISPLAY_DATE_TIME_FORMATTER);
    }

    /**
     * Returns a parsed date or date-time based on the given input string.
     * Parses the input as a date-time first, then as a date if date-time parsing fails.
     * Throws an exception if the input is empty or does not match the expected formats.
     *
     * @param dateStr Date or date-time string provided by the user.
     * @param type Command type that determines the error message context.
     * @return The parsed date or date-time as a {@link Temporal} object.
     * @throws WooperException If the input is empty or the format is invalid.
     */
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
