package wooper;

import wooper.exception.WooperException;
import wooper.task.Deadline;
import wooper.task.Event;
import wooper.task.Task;
import wooper.task.ToDo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.ArrayList;

public class TaskManager {
    // wooper.task.Task List
    private final ArrayList<Task> taskList = new ArrayList<>();

    public ArrayList<Task> getAllTasks() {
        return taskList;
    }

    public int getTaskListSize() {
        return taskList.size();
    }

    public Task markTaskDone(int taskNo) throws WooperException {
        if (taskNo < 0 || taskNo >= taskList.size()) {
            throw new WooperException("Woop! Choose a number from the task list!");
        }
        Task t = taskList.get(taskNo);
        t.setDone(true);
        return t;
    }

    public Task unmarkTaskDone(int taskNo) throws WooperException {
        if (taskNo < 0 || taskNo >= taskList.size()) {
            throw new WooperException("Woop! Choose a number from the task list!");
        }
        Task t = taskList.get(taskNo);
        t.setDone(false);
        return t;
    }

    public ToDo addToDoTask(String taskName) throws WooperException {
        if (taskName.isEmpty() || taskName.isBlank()) {
            throw new WooperException("Woop! Please give the todo a description!");
        }
        ToDo td = new ToDo(taskName);
        taskList.add(td);
        return td;
    }

    public Deadline addDeadlineTask(String taskDesc, Temporal dl) throws WooperException {
        if (taskDesc.isEmpty() || taskDesc.isBlank()) {
            throw new WooperException("Woop! Please give the deadline a description!");
        }

        Deadline td;
        if (dl instanceof LocalDateTime) {
            td = new Deadline(taskDesc, (LocalDateTime) dl);
        } else if (dl instanceof LocalDate) {
            td = new Deadline(taskDesc, (LocalDate) dl);
        } else {
            throw new WooperException("Woop! Invalid deadline date format.");
        }
        taskList.add(td);
        return td;
    }

    public Event addEventTask(String taskDesc, Temporal sdl, Temporal edl) throws WooperException {
        if (taskDesc.isEmpty() || taskDesc.isBlank()) {
            throw new WooperException("Woop! Please give the event a description!");
        }

        Event td;
        if (sdl instanceof LocalDateTime && edl instanceof LocalDateTime) {
            td = new Event(taskDesc, (LocalDateTime) sdl, (LocalDateTime) edl);
        } else if (sdl instanceof LocalDate && edl instanceof LocalDate) {
            td = new Event(taskDesc, (LocalDate) sdl, (LocalDate) edl);
        } else {
            throw new WooperException("Woop! Please give same event date format (DD/MM/YYYY or DD/MM/YYYY HH:mm) for BOTH");
        }

        taskList.add(td);
        return td;
    }

    public Task deleteTask(int taskNo) throws WooperException {
        if (taskNo < 0 || taskNo >= taskList.size()) {
            throw new WooperException("Woop! Choose a number from the task list!");
        }
        Task t = taskList.remove(taskNo);
        return t;
    }

    public void loadTaskList(ArrayList<Task> inTaskList) {
        for (int i = 0; i < inTaskList.size(); i++) {
            taskList.add(inTaskList.get(i));
        }
    }

    /**
     * Returns a list of tasks whose descriptions contain the specified search string.
     *
     * @param taskStr Keyword used to match task descriptions.
     * @return A list of tasks whose names contain the specified string.
     * @throws WooperException If the search string is empty or blank.
     */
    public ArrayList<Task> findTasks(String taskStr) throws WooperException {
        if (taskStr.isEmpty() || taskStr.isBlank()) {
            throw new WooperException("Woop! Let me know which task you are trying to find :>");
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
