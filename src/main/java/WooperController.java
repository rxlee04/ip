import java.io.IOException;
import java.time.temporal.Temporal;
import java.util.ArrayList;

public class WooperController {

    private final Parser parser = new Parser();
    private final TaskManager taskManager = new TaskManager();
    private final UI ui = new UI();
    private final Storage storage = new Storage();


    public void run() {
        // print greeting msg
        ui.printGreetingMessage();

        // load data from storage
        try {
            ArrayList<Task> storageTasks = storage.load();
            taskManager.loadTaskList(storageTasks);
        } catch (WooperException e) {
            ui.printErrorMessage(e.getMessage());
        }

        // user input for task management
        String userInput = "";

        // formatted user input
        ParseResult pr;
        ArrayList<String> args = null;
        CommandType action = null;

        while (action != CommandType.BYE) {
            userInput = ui.getUserCommand();
            pr = parser.getActionAndArguments(userInput);
            action = pr.getCommandType();
            args = pr.getArgs();

            try {
                if (action == CommandType.LIST) {
                    ui.printTaskList(taskManager.getAllTasks());
                } else if (action == CommandType.MARK) {
                    int taskNo = Integer.parseInt(args.get(0)) - 1; // parse task no string -> int
                    Task t = taskManager.markTaskDone(taskNo);

                    // print and save
                    ui.printMarkTaskDoneMessage(t);
                    storage.save(taskManager.getAllTasks());
                } else if (action == CommandType.UNMARK) {
                    int taskNo = Integer.parseInt(pr.getArgs().get(0)) - 1; // parse task no string -> int
                    Task t = taskManager.unmarkTaskDone(taskNo);

                    // print and save
                    ui.printUnmarkTaskDoneMessage(t);
                    storage.save(taskManager.getAllTasks());
                } else if (action == CommandType.TODO) {
                    String taskName = pr.getArgs().get(0);
                    Task t = taskManager.addToDoTask(taskName);

                    // print and save
                    ui.printAddTaskMessage(t, taskManager.getTaskListSize());
                    storage.save(taskManager.getAllTasks());
                } else if (action == CommandType.DEADLINE) {
                    String taskDesc = pr.getArgs().get(0);
                    Temporal dl = DateTimeUtil.parseDateOrDateTime(pr.getArgs().get(1), CommandType.DEADLINE);
                    Task t = taskManager.addDeadlineTask(taskDesc, dl);

                    // print and save
                    ui.printAddTaskMessage(t, taskManager.getTaskListSize());
                    storage.save(taskManager.getAllTasks());
                } else if (action == CommandType.EVENT) {
                    String taskDesc = pr.getArgs().get(0);
                    Temporal sdl = DateTimeUtil.parseDateOrDateTime(pr.getArgs().get(1),CommandType.EVENT);
                    Temporal edl = DateTimeUtil.parseDateOrDateTime(pr.getArgs().get(2),CommandType.EVENT);
                    Task t = taskManager.addEventTask(taskDesc, sdl, edl);

                    // print and save
                    ui.printAddTaskMessage(t, taskManager.getTaskListSize());
                    storage.save(taskManager.getAllTasks());
                } else if (action == CommandType.DELETE) {
                    int taskNo = Integer.parseInt(pr.getArgs().get(0)) - 1;
                    Task t = taskManager.deleteTask(taskNo);

                    // print and save
                    ui.printDeleteTaskMessage(t, taskManager.getTaskListSize());
                    storage.save(taskManager.getAllTasks());
                } else if (action == CommandType.BYE) {
                    ui.printExitMessage();
                    break;
                } else {
                    throw new WooperException("Wooooo-pah? I don't understand this command.");
                }
            } catch (WooperException e) {
                ui.printErrorMessage(e.getMessage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }


}
