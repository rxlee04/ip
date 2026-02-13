package wooper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.ArrayList;

import wooper.exception.WooperException;
import wooper.task.Deadline;
import wooper.task.Event;
import wooper.task.Task;
import wooper.task.ToDo;

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
        if (taskNo < 0 || taskNo >= taskList.size()) {
            throw new WooperException("Choose a number from the task list!");
        }
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
        if (taskNo < 0 || taskNo >= taskList.size()) {
            throw new WooperException("Choose a number from the task list!");
        }
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

        Event tEvent;
        assert sdl != null && edl != null : "Event dates should not be null";
        assert sdl.getClass() == edl.getClass() : "Start and end dates should be same type";

        if (sdl instanceof LocalDateTime && edl instanceof LocalDateTime) {
            tEvent = new Event(taskDesc, (LocalDateTime) sdl, (LocalDateTime) edl);
        } else if (sdl instanceof LocalDate && edl instanceof LocalDate) {
            tEvent = new Event(taskDesc, (LocalDate) sdl, (LocalDate) edl);
        } else {
            throw new WooperException("Please give same event date format"
                    + " (DD/MM/YYYY or DD/MM/YYYY HH:mm) for BOTH");
        }

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
        if (taskNo < 0 || taskNo >= taskList.size()) {
            throw new WooperException("Choose a number from the task list!");
        }
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
        ArrayList<Task> matchedTL = new ArrayList<>();
        for (int i = 0; i < taskList.size(); i++) {
            if (taskList.get(i).getTaskName().toLowerCase().contains(taskStr.toLowerCase())) {
                matchedTL.add(taskList.get(i));
            }
        }
        return matchedTL;
    }
}
