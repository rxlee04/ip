package wooper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.stream.Collectors;

import wooper.enums.CommandType;
import wooper.exception.WooperException;
import wooper.task.Deadline;
import wooper.task.Event;
import wooper.task.Task;
import wooper.task.ToDo;
import wooper.util.DateTimeUtil;

/**
 * Manages the list of tasks in the Wooper application.
 * Provides operations to add, remove, mark, unmark, and retrieve tasks.
 * This class has all logic related to task manipulation and
 * ensures task-related constraints are enforced.
 */
public class TaskManager {
    // wooper.task.Task List
    private final ArrayList<Task> taskList = new ArrayList<>();

    /**
     * Returns the list of all tasks currently managed by the task manager.
     *
     * @return The list of tasks.
     */
    public ArrayList<Task> getAllTasks() {
        return taskList;
    }

    /**
     * Returns the number of tasks in the task list.
     *
     * @return The total number of tasks.
     */
    public int getTaskListSize() {
        return taskList.size();
    }

    /**
     * Marks the specified task as completed.
     *
     * @param taskNo Index of the task to mark as done.
     * @return The task that was marked as completed.
     * @throws WooperException If the task index is out of bounds.
     */
    public Task markTaskDone(int taskNo) throws WooperException {
        validateTaskIndex(taskNo);
        Task t = taskList.get(taskNo);
        assert t != null : "Retrieved task should not be null";

        t.setDone(true);
        return t;
    }

    /**
     * Marks the specified task as not completed.
     *
     * @param taskNo Index of the task to unmark.
     * @return The task that was marked as not completed.
     * @throws WooperException If the task index is out of bounds.
     */
    public Task unmarkTaskDone(int taskNo) throws WooperException {
        validateTaskIndex(taskNo);
        Task t = taskList.get(taskNo);
        assert t != null : "Retrieved task should not be null";

        t.setDone(false);
        return t;
    }

    /**
     * Adds a new todo task to the task list.
     *
     * @param taskName Description of the todo task.
     * @return The newly created todo task.
     * @throws WooperException If the task description is empty or blank.
     */
    public ToDo addToDoTask(String taskName) throws WooperException {
        if (taskName.isEmpty() || taskName.isBlank()) {
            throw new WooperException("Please give the todo a description!");
        }

        ToDo tDo = new ToDo(taskName);
        taskList.add(tDo);
        assert taskList.get(taskList.size() - 1) == tDo : "Newly added ToDo should be at end of task list";

        return tDo;
    }

    /**
     * Adds a new deadline task to the task list.
     *
     * @param taskDesc Description of the deadline task.
     * @param dl       Deadline date or date-time of the task.
     * @return The newly created deadline task.
     * @throws WooperException If the task description is empty or the deadline format is invalid.
     */
    public Deadline addDeadlineTask(String taskDesc, Temporal dl) throws WooperException {
        if (taskDesc.isEmpty() || taskDesc.isBlank()) {
            throw new WooperException("Please give the deadline a description!");
        }

        Deadline tDeadline;
        if (dl instanceof LocalDateTime) {
            tDeadline = new Deadline(taskDesc, (LocalDateTime) dl);
        } else if (dl instanceof LocalDate) {
            tDeadline = new Deadline(taskDesc, (LocalDate) dl);
        } else {
            throw new WooperException("Invalid deadline date format.");
        }

        taskList.add(tDeadline);
        assert taskList.get(taskList.size() - 1) == tDeadline : "Newly added Deadline task should be in task list";

        return tDeadline;
    }

    /**
     * Adds a new event task to the task list.
     *
     * @param taskDesc Description of the event task.
     * @param sdl      Start date or date-time of the event.
     * @param edl      End date or date-time of the event.
     * @return The newly created event task.
     * @throws WooperException If the task description is empty or the date formats do not match.
     */
    public Event addEventTask(String taskDesc, Temporal sdl, Temporal edl) throws WooperException {
        if (taskDesc.isEmpty() || taskDesc.isBlank()) {
            throw new WooperException("Please give the event a description!");
        }

        validateEventDateRange(sdl, edl);

        Event tEvent;
        assert sdl != null && edl != null : "Event dates should not be null";

        if (sdl instanceof LocalDateTime) {
            tEvent = new Event(taskDesc, (LocalDateTime) sdl, (LocalDateTime) edl);
        } else {
            // must be LocalDate here because validate already checked
            tEvent = new Event(taskDesc, (LocalDate) sdl, (LocalDate) edl);
        }

        assert sdl.getClass() == edl.getClass() : "Start and end dates should be same type";

        taskList.add(tEvent);
        assert taskList.get(taskList.size() - 1) == tEvent : "Newly added Event task should be in task list";

        return tEvent;
    }

    /**
     * Removes the specified task from the task list.
     *
     * @param taskNo Index of the task to delete.
     * @return The task that was removed.
     * @throws WooperException If the task index is out of bounds.
     */
    public Task deleteTask(int taskNo) throws WooperException {
        validateTaskIndex(taskNo);
        Task t = taskList.remove(taskNo);
        assert t != null : "Retrieved task should not be null";

        return t;
    }

    /**
     * Loads tasks from an external task list into the task manager.
     *
     * @param inTaskList List of tasks to be loaded.
     */
    public void loadTaskList(ArrayList<Task> inTaskList) {
        taskList.addAll(inTaskList);
    }

    /**
     * Returns a list of tasks whose descriptions contain the specified search string.
     *
     * @param taskStr Keyword used to match task descriptions.
     * @return A list of tasks whose names contain the specified string.
     * @throws WooperException If the search string is empty or blank.
     */
    public ArrayList<Task> findTasks(String taskStr) throws WooperException {
        assert !taskList.contains(null) : "Task list should not contain null tasks";
        if (taskStr.isEmpty() || taskStr.isBlank()) {
            throw new WooperException("Let me know which task you are trying to find :>");
        }
        String taskStrLower = taskStr.toLowerCase();
        ArrayList<Task> matchedTL = taskList.stream()
                .filter(t -> t.getTaskName().toLowerCase().contains(taskStrLower))
                .collect(Collectors.toCollection(ArrayList::new));
        return matchedTL;
    }

    /**
     * Updates fields of an existing task using the parsed update arguments.
     *
     * @param taskNo Index of the task to update.
     * @param args   Parsed arguments containing task type and updated fields.
     * @return The updated task.
     * @throws WooperException If the task index is invalid or the update arguments are invalid.
     */
    public Task updateTask(int taskNo, ArrayList<String> args) throws WooperException {
        validateTaskIndex(taskNo);
        validateUpdateArgs(args);

        Task task = taskList.get(taskNo);
        String inputType = args.get(1).trim().toLowerCase();

        //check tasktype exist
        if (inputType.isEmpty()) {
            throw new WooperException("Please state the task type.");
        }

        //check if tasktype match
        String actualType = getTaskTypeName(task);
        if (!actualType.equals(inputType)) {
            throw new WooperException("Please resend the update with the correct task type: " + actualType);
        }

        String taskName = args.get(2).trim();
        String by = args.get(3).trim();
        String from = args.get(4).trim();
        String to = args.get(5).trim();

        if (taskName.isEmpty() && by.isEmpty() && from.isEmpty() && to.isEmpty()) {
            throw new WooperException("Please specify at least one field to update.");
        }

        switch (actualType) {
        case "todo":
            updateTodo((ToDo) task, taskName, by, from, to);
            break;
        case "deadline":
            updateDeadline((Deadline) task, taskName, by, from, to);
            break;
        case "event":
            updateEvent((Event) task, taskName, by, from, to);
            break;
        default:
            throw new WooperException("Unsupported task type for update.");
        }
        return task;
    }

    private void validateTaskIndex(int taskNo) throws WooperException {
        if (taskNo < 0 || taskNo >= taskList.size()) {
            throw new WooperException("Choose a number from the task list!");
        }
    }

    private String getTaskTypeName(Task task) {
        if (task instanceof wooper.task.ToDo) {
            return "todo";
        }

        if (task instanceof wooper.task.Deadline) {
            return "deadline";
        }

        if (task instanceof wooper.task.Event) {
            return "event";
        }

        return "task";
    }

    private void validateUpdateArgs(ArrayList<String> args) throws WooperException {
        if (args == null || args.size() < 6) {
            throw new WooperException(
                    "Invalid update format.\n"
                            + "Examples:\n"
                            + "update 1 /taskType todo /taskName newName\n"
                            + "update 1 /taskType deadline /by 06/06/2026\n"
                            + "update 1 /taskType event /from 02/02/2026 14:00 /to 02/02/2026 16:00");
        }
    }

    private void updateTodo(ToDo task, String taskName, String by, String from, String to) throws WooperException {
        if (!by.isEmpty() || !from.isEmpty() || !to.isEmpty()) {
            throw new WooperException("Todo only supports /taskName update.");
        }
        if (!taskName.isEmpty()) {
            task.setTaskName(taskName);
        }
    }

    private void updateDeadline(Deadline task, String taskName, String by, String from, String to)
            throws WooperException {

        if (!from.isEmpty() || !to.isEmpty()) {
            throw new WooperException("Deadline does not support /from or /to.");
        }

        if (!taskName.isEmpty()) {
            task.setTaskName(taskName);
        }

        if (!by.isEmpty()) {
            Temporal dl = DateTimeUtil.parseDateOrDateTime(by, CommandType.DEADLINE);
            if (dl instanceof LocalDateTime) {
                task.setDeadline((LocalDateTime) dl);
            } else if (dl instanceof LocalDate) {
                task.setDeadline((LocalDate) dl);
            } else {
                throw new WooperException("Invalid deadline date format.");
            }
        }
    }

    private void updateEvent(Event task, String taskName, String by, String from, String to)
            throws WooperException {

        if (!by.isEmpty()) {
            throw new WooperException("Event does not support /by.");
        }

        if (!taskName.isEmpty()) {
            task.setTaskName(taskName);
        }

        boolean hasFrom = !from.isEmpty();
        boolean hasTo = !to.isEmpty();
        if (hasFrom != hasTo) {
            throw new WooperException("Event update requires BOTH /from and /to together.");
        }

        if (hasFrom) {
            Temporal start = DateTimeUtil.parseDateOrDateTime(from, CommandType.EVENT);
            Temporal end = DateTimeUtil.parseDateOrDateTime(to, CommandType.EVENT);

            validateEventDateRange(start, end);

            if (start instanceof LocalDateTime) {
                task.setEventStart((LocalDateTime) start);
                task.setEventEnd((LocalDateTime) end);
            } else {
                // must be LocalDate because validate already ensured valid types
                task.setEventStart((LocalDate) start);
                task.setEventEnd((LocalDate) end);
            }
        }
    }

    private void validateEventDateRange(Temporal start, Temporal end) throws WooperException {
        if (start == null || end == null) {
            throw new WooperException("Event dates cannot be null.");
        }

        // must be same type
        if (start.getClass() != end.getClass()) {
            throw new WooperException("Please give same event date format (DD/MM/YYYY or DD/MM/YYYY HH:mm) for BOTH");
        }

        // must be start < end
        if (start instanceof LocalDateTime) {
            if (!((LocalDateTime) start).isBefore((LocalDateTime) end)) {
                throw new WooperException("Event start date/time must be before end date/time.");
            }
            return;
        }

        if (start instanceof LocalDate) {
            if (!((LocalDate) start).isBefore((LocalDate) end)) {
                throw new WooperException("Event start date must be before end date.");
            }
            return;
        }

        throw new WooperException("Invalid event date format.");
    }
}
