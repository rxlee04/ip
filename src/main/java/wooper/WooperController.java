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
        ParseResult pr;
        ArrayList<String> args = null;
        CommandType action = null;


        pr = parser.getActionAndArguments(userInput);
        action = pr.getCommandType();
        args = pr.getArgs();

        try {
            if (action == CommandType.LIST) {
                return ui.printTaskList(taskManager.getAllTasks());
            } else if (action == CommandType.MARK) {
                int taskNo = Integer.parseInt(args.get(0)) - 1; // parse task no string -> int
                Task t = taskManager.markTaskDone(taskNo);

                // print and save
                storage.save(taskManager.getAllTasks());
                return ui.printMarkTaskDoneMessage(t);
            } else if (action == CommandType.UNMARK) {
                int taskNo = Integer.parseInt(pr.getArgs().get(0)) - 1; // parse task no string -> int
                Task t = taskManager.unmarkTaskDone(taskNo);

                // print and save
                storage.save(taskManager.getAllTasks());
                return ui.printUnmarkTaskDoneMessage(t);
            } else if (action == CommandType.TODO) {
                String taskName = pr.getArgs().get(0);
                Task t = taskManager.addToDoTask(taskName);

                // print and save
                storage.save(taskManager.getAllTasks());
                return ui.printAddTaskMessage(t, taskManager.getTaskListSize());
            } else if (action == CommandType.DEADLINE) {
                String taskDesc = pr.getArgs().get(0);
                Temporal dl = DateTimeUtil.parseDateOrDateTime(pr.getArgs().get(1), CommandType.DEADLINE);
                Task t = taskManager.addDeadlineTask(taskDesc, dl);

                // print and save
                storage.save(taskManager.getAllTasks());
                return ui.printAddTaskMessage(t, taskManager.getTaskListSize());
            } else if (action == CommandType.EVENT) {
                String taskDesc = pr.getArgs().get(0);
                Temporal sdl = DateTimeUtil.parseDateOrDateTime(pr.getArgs().get(1), CommandType.EVENT);
                Temporal edl = DateTimeUtil.parseDateOrDateTime(pr.getArgs().get(2), CommandType.EVENT);
                Task t = taskManager.addEventTask(taskDesc, sdl, edl);

                // print and save
                storage.save(taskManager.getAllTasks());
                return ui.printAddTaskMessage(t, taskManager.getTaskListSize());
            } else if (action == CommandType.DELETE) {
                int taskNo = Integer.parseInt(pr.getArgs().get(0)) - 1;
                Task t = taskManager.deleteTask(taskNo);

                // print and save
                storage.save(taskManager.getAllTasks());
                return ui.printDeleteTaskMessage(t, taskManager.getTaskListSize());
            } else if (action == CommandType.FIND) {
                ArrayList<Task> matchedTL = taskManager.findTasks(pr.getArgs().get(0));
                return ui.printFindTasksMessage(matchedTL);
            } else if (action == CommandType.BYE) {
                return ui.printExitMessage();
            } else {
                return "Wooooo-pah? I don't understand this command.";
            }
        } catch (WooperException e) {
            return ui.printErrorMessage(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
