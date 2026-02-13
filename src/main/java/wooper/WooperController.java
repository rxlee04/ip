package wooper;

import java.io.IOException;
import java.time.temporal.Temporal;
import java.util.ArrayList;

import wooper.enums.CommandType;
import wooper.exception.WooperException;
import wooper.parser.ParseResult;
import wooper.parser.Parser;
import wooper.storage.Storage;
import wooper.task.Task;
import wooper.ui.UI;
import wooper.util.DateTimeUtil;

/**
 * Handles the main control flow of the Wooper application.
 * Coordinates interactions between the user interface, parser, task manager,
 * and storage components to process user commands and execute the corresponding actions.
 */
public class WooperController {

    private final Parser parser = new Parser();
    private final TaskManager taskManager = new TaskManager();
    private final UI ui = new UI();
    private final Storage storage = new Storage();

    public WooperController() {
    }

    /**
     * Returns the greeting message after loading saved tasks from storage.
     *
     * @return The greeting message, or an error message if loading fails.
     */
    public String init() {
        // load data from storage
        try {
            ArrayList<Task> storageTasks = storage.load();
            taskManager.loadTaskList(storageTasks);
        } catch (WooperException e) {
            return "Error loading tasks: " + e.getMessage();
        }
        return ui.printGreetingMessage();
    }

    /**
     * Starts the main loop of the Wooper.
     * Loads existing tasks from storage.
     * Displays the greeting message.
     * Repeatedly reads user input, parses commands, executes the requested actions, and saves task
     * updates when necessary. The loop terminates when the "bye" command is given.
     *
     */
    public String handleUserInput(String userInput) {
        // formatted user input
        ParseResult pr = parser.getActionAndArguments(userInput);
        CommandType action = pr.getCommandType();
        ArrayList<String> args = pr.getArgs();

        try {
            switch (action) {
            case LIST:
                return handleList();
            case MARK:
                return handleMark(args);
            case UNMARK:
                return handleUnmark(args);
            case TODO:
                return handleTodo(args);
            case DEADLINE:
                return handleDeadline(args);
            case EVENT:
                return handleEvent(args);
            case DELETE:
                return handleDelete(args);
            case FIND:
                return handleFind(args);
            case BYE:
                return handleBye();
            case UNKNOWN:
            default:
                return handleUnknown();
            }
        } catch (WooperException e) {
            return ui.printErrorMessage(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String handleList() {
        return ui.printTaskList(taskManager.getAllTasks());
    }

    private String handleMark(ArrayList<String> args) throws WooperException, IOException {
        int taskNo = parseTaskIndex(args);
        Task t = taskManager.markTaskDone(taskNo);
        saveAllTasks();
        return ui.printMarkTaskDoneMessage(t);
    }

    private String handleUnmark(ArrayList<String> args) throws WooperException, IOException {
        int taskNo = parseTaskIndex(args);
        Task t = taskManager.unmarkTaskDone(taskNo);
        saveAllTasks();
        return ui.printUnmarkTaskDoneMessage(t);
    }

    private String handleTodo(ArrayList<String> args) throws WooperException, IOException {
        Task t = taskManager.addToDoTask(args.get(0));
        saveAllTasks();
        return ui.printAddTaskMessage(t, taskManager.getTaskListSize());
    }

    private String handleDeadline(ArrayList<String> args) throws WooperException, IOException {
        String taskDesc = args.get(0);
        Temporal dl = DateTimeUtil.parseDateOrDateTime(args.get(1), CommandType.DEADLINE);
        Task t = taskManager.addDeadlineTask(taskDesc, dl);
        saveAllTasks();
        return ui.printAddTaskMessage(t, taskManager.getTaskListSize());
    }

    private String handleEvent(ArrayList<String> args) throws WooperException, IOException {
        String taskDesc = args.get(0);
        Temporal start = DateTimeUtil.parseDateOrDateTime(args.get(1), CommandType.EVENT);
        Temporal end = DateTimeUtil.parseDateOrDateTime(args.get(2), CommandType.EVENT);
        Task t = taskManager.addEventTask(taskDesc, start, end);
        saveAllTasks();
        return ui.printAddTaskMessage(t, taskManager.getTaskListSize());
    }

    private String handleDelete(ArrayList<String> args) throws WooperException, IOException {
        int taskNo = parseTaskIndex(args);
        Task t = taskManager.deleteTask(taskNo);
        saveAllTasks();
        return ui.printDeleteTaskMessage(t, taskManager.getTaskListSize());
    }

    private String handleFind(ArrayList<String> args) throws WooperException {
        ArrayList<Task> matchedTL = taskManager.findTasks(args.get(0));
        return ui.printFindTasksMessage(matchedTL);
    }

    private String handleBye() {
        return ui.printExitMessage();
    }

    private String handleUnknown() {
        return ui.printUnknownCommandMessage();
    }

    private void saveAllTasks() throws IOException {
        storage.save(taskManager.getAllTasks());
    }

    private int parseTaskIndex(ArrayList<String> args) throws WooperException {
        if (args == null || args.isEmpty() || args.get(0).isEmpty()) {
            throw new WooperException("Task number is missing.");
        }

        try {
            int oneBased = Integer.parseInt(args.get(0));
            return oneBased - 1;
        } catch (NumberFormatException e) {
            throw new WooperException("Task number must be a valid integer.");
        }
    }
}
