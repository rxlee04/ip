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
    private final Parser parser = new Parser();
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
        ParseResult pr;
        ArrayList<String> args = null;
        CommandType action = null;

        while (action != CommandType.BYE) {
            userInput = scanner.nextLine();
            pr = parser.getActionAndArguments(userInput);
            action = pr.getCommandType();
            args = pr.getArgs();

            try {
                if (action == CommandType.LIST) {
                    view.printTaskList(taskManager.getAllTasks());
                } else if (action == CommandType.MARK) {
                    int taskNo = Integer.parseInt(args.get(0)) - 1; // parse task no string -> int
                    Task t = taskManager.markTaskDone(taskNo);

                    // print and save
                    view.printMarkTaskDoneMessage(t);
                    storage.save(taskManager.getAllTasks());
                } else if (action == CommandType.UNMARK) {
                    int taskNo = Integer.parseInt(pr.getArgs().get(0)) - 1; // parse task no string -> int
                    Task t = taskManager.unmarkTaskDone(taskNo);

                    // print and save
                    view.printUnmarkTaskDoneMessage(t);
                    storage.save(taskManager.getAllTasks());
                } else if (action == CommandType.TODO) {
                    String taskName = pr.getArgs().get(0);
                    Task t = taskManager.addToDoTask(taskName);

                    // print and save
                    view.printAddTaskMessage(t, taskManager.getTaskListSize());
                    storage.save(taskManager.getAllTasks());
                } else if (action == CommandType.DEADLINE) {
                    String taskDesc = pr.getArgs().get(0);
                    Temporal dl = DateTimeUtil.parseDateOrDateTime(pr.getArgs().get(1), CommandType.DEADLINE);
                    Task t = taskManager.addDeadlineTask(taskDesc, dl);

                    // print and save
                    view.printAddTaskMessage(t, taskManager.getTaskListSize());
                    storage.save(taskManager.getAllTasks());
                } else if (action == CommandType.EVENT) {
                    String taskDesc = pr.getArgs().get(0);
                    Temporal sdl = DateTimeUtil.parseDateOrDateTime(pr.getArgs().get(1),CommandType.EVENT);
                    Temporal edl = DateTimeUtil.parseDateOrDateTime(pr.getArgs().get(2),CommandType.EVENT);
                    Task t = taskManager.addEventTask(taskDesc, sdl, edl);

                    // print and save
                    view.printAddTaskMessage(t, taskManager.getTaskListSize());
                    storage.save(taskManager.getAllTasks());
                } else if (action == CommandType.DELETE) {
                    int taskNo = Integer.parseInt(pr.getArgs().get(0)) - 1;
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


}
