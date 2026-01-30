import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Scanner;

public class WooperController {

    private final Scanner scanner = new Scanner(System.in);
    private final TaskManager taskManager = new TaskManager();
    private final TextView view = new TextView();
    private final Storage storage = new Storage();


    public void run() {
        // print greeting msg
        view.printGreetingMessage();

        // load data from storage
        try {
            ArrayList<Task> storageTasks = storage.load();
            taskManager.loadTaskList(storageTasks);
        } catch (WooperException e) {
            view.printErrorMessage(e.getMessage());
        }

        // user input for task management
        String userInput = "";

        // formatted user input
        ArrayList<String> actionAndArgs;
        CommandType action = null;

        while (action != CommandType.BYE) {
            userInput = scanner.nextLine();
            actionAndArgs = getActionAndArguements(userInput);
            action = getCommandType(actionAndArgs.get(0));

            try {
                if (action == CommandType.LIST) {
                    view.printTaskList(taskManager.getAllTasks());
                } else if (action == CommandType.MARK) {
                    int taskNo = Integer.parseInt(actionAndArgs.get(1)) - 1; // parse task no string -> int
                    Task t = taskManager.markTaskDone(taskNo);

                    // print and save
                    view.printMarkTaskDoneMessage(t);
                    storage.save(taskManager.getAllTasks());
                } else if (action == CommandType.UNMARK) {
                    int taskNo = Integer.parseInt(actionAndArgs.get(1)) - 1; // parse task no string -> int
                    Task t = taskManager.unmarkTaskDone(taskNo);

                    // print and save
                    view.printUnmarkTaskDoneMessage(t);
                    storage.save(taskManager.getAllTasks());
                } else if (action == CommandType.TODO) {
                    String taskName = actionAndArgs.get(1);
                    Task t = taskManager.addToDoTask(taskName);

                    // print and save
                    view.printAddTaskMessage(t, taskManager.getTaskListSize());
                    storage.save(taskManager.getAllTasks());
                } else if (action == CommandType.DEADLINE) {
                    String taskDesc = actionAndArgs.get(1);
                    Temporal dl = DateTimeUtil.parseDateOrDateTime(actionAndArgs.get(2), CommandType.DEADLINE);
                    Task t = taskManager.addDeadlineTask(taskDesc, dl);

                    // print and save
                    view.printAddTaskMessage(t, taskManager.getTaskListSize());
                    storage.save(taskManager.getAllTasks());
                } else if (action == CommandType.EVENT) {
                    String taskDesc = actionAndArgs.get(1);
                    Temporal sdl = DateTimeUtil.parseDateOrDateTime(actionAndArgs.get(2),CommandType.EVENT);
                    Temporal edl = DateTimeUtil.parseDateOrDateTime(actionAndArgs.get(3),CommandType.EVENT);
                    Task t = taskManager.addEventTask(taskDesc, sdl, edl);

                    // print and save
                    view.printAddTaskMessage(t, taskManager.getTaskListSize());
                    storage.save(taskManager.getAllTasks());
                } else if (action == CommandType.DELETE) {
                    int taskNo = Integer.parseInt(actionAndArgs.get(1)) - 1;
                    Task t = taskManager.deleteTask(taskNo);

                    // print and save
                    view.printDeleteTaskMessage(t, taskManager.getTaskListSize());
                    storage.save(taskManager.getAllTasks());
                } else if (action == CommandType.BYE) {
                    view.printExitMessage();
                    break;
                } else {
                    throw new WooperException("Wooooo-pah? I don't understand this command.");
                }
            } catch (WooperException e) {
                view.printErrorMessage(e.getMessage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private ArrayList<String> getActionAndArguements(String str) {
        str = str.trim();
        ArrayList<String> actionAndArgs = new ArrayList<>();
        if (str.equalsIgnoreCase("list")) {
            actionAndArgs.add("list");
        } else if (str.toLowerCase().startsWith("mark")) {
            String taskNoStr = str.substring(str.indexOf("mark") + 5).trim();
            actionAndArgs.add("mark");
            actionAndArgs.add(taskNoStr);
        } else if (str.toLowerCase().startsWith("unmark")) {
            String taskNoStr = str.substring(str.indexOf("unmark") + 7).trim();
            actionAndArgs.add("unmark");
            actionAndArgs.add(taskNoStr);
        } else if (str.toLowerCase().startsWith("todo")) {
            String taskStr = str.substring(str.indexOf("todo") + 5).trim();
            actionAndArgs.add("todo");
            actionAndArgs.add(taskStr);
        } else if (str.toLowerCase().startsWith("deadline")) {
            String taskStr = str.substring(str.indexOf("deadline") + 9, str.indexOf("/by")).trim();
            String dl = str.substring(str.indexOf("/by") + 4).trim();
            actionAndArgs.add("deadline");
            actionAndArgs.add(taskStr);
            actionAndArgs.add(dl);
        } else if (str.toLowerCase().startsWith("event")) {
            String taskStr = str.substring(str.indexOf("event") + 6, str.indexOf("/from")).trim();
            String sdl = str.substring(str.indexOf("/from") + 6, str.indexOf("/to")).trim();
            String edl = str.substring(str.indexOf("/to") + 4).trim();

            actionAndArgs.add("event");
            actionAndArgs.add(taskStr);
            actionAndArgs.add(sdl);
            actionAndArgs.add(edl);
        } else if (str.toLowerCase().startsWith("delete")) {
            String taskNoStr = str.substring(str.indexOf("delete") + 7).trim();
            actionAndArgs.add("delete");
            actionAndArgs.add(taskNoStr);
        } else if (str.equalsIgnoreCase("bye")) {
            actionAndArgs.add("bye");
        } else {
            actionAndArgs.add("unknown");
        }

        return actionAndArgs;

    }

    private CommandType getCommandType(String action) {
        switch (action) {
            case "list":
                return CommandType.LIST;
            case "mark":
                return CommandType.MARK;
            case "unmark":
                return CommandType.UNMARK;
            case "todo":
                return CommandType.TODO;
            case "deadline":
                return CommandType.DEADLINE;
            case "event":
                return CommandType.EVENT;
            case "delete":
                return CommandType.DELETE;
            case "bye":
                return CommandType.BYE;
            default:
                return CommandType.UNKNOWN;
        }
    }
}
