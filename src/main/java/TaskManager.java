import java.util.ArrayList;

public class TaskManager {
    // Task List
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
        if (taskName.isEmpty()) {
            throw new WooperException("Woop! Please give the todo a description!");
        }
        ToDo td = new ToDo(taskName);
        taskList.add(td);
        return td;
    }

    public Deadline addDeadlineTask(String taskDesc, String dl) throws WooperException {
        if (taskDesc.isEmpty()) {
            throw new WooperException("Woop! Please give the deadline a description!");
        }
        if (dl.isEmpty()) {
            throw new WooperException("Woop! Please give a deadline!");
        }

        Deadline td = new Deadline(taskDesc, dl);
        taskList.add(td);
        return td;
    }

    public Event addEventTask(String taskDesc, String sdl, String edl) throws WooperException {
        if (taskDesc.isEmpty()) {
            throw new WooperException("Woop! Please give the event a description!");
        }
        if (sdl.isEmpty() || edl.isEmpty()) {
            throw new WooperException("Woop! Please give event's start and/or end date!");
        }

        Event td = new Event(taskDesc, sdl, edl);
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

}
