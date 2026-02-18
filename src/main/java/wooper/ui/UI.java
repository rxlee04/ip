package wooper.ui;

import java.util.ArrayList;

import wooper.task.Task;

/**
 * Handles user interaction for the Wooper application.
 * Reads commands from standard input and displays messages, task lists,
 * and feedback to the user via standard output.
 */
public class UI {
    /**
     * Prints the greeting message shown when the application starts.
     */
    public String printGreetingMessage() {
        String greetMsg = "Woo-pah! I'm Wooper\nWhat can I do for you?" + "\n\n"
                + "Tell me your tasks — I can help you organise and track them!";

        return greetMsg;
    }

    /**
     * Prints the farewell message shown when the application exits.
     */
    public String printExitMessage() {
        String exitMsg = "Woop Woop! Hope to see you again soon!";
        return exitMsg;
    }

    /**
     * Prints the list of tasks currently stored in the application.
     *
     * @param taskList List of tasks to be displayed.
     */
    public String printTaskList(ArrayList<Task> taskList) {
        String str = "";
        if (taskList.isEmpty()) {
            str = "Woop! Your task list is empty!";
        } else {
            str = "Here are the tasks in your list:";
            for (int i = 0; i < taskList.size(); i++) {
                str += "\n " + (i + 1) + ". " + taskList.get(i).toString();
            }
        }
        return str;
    }

    /**
     * Prints a confirmation message indicating that a task has been marked as done.
     *
     * @param t The task that was marked as completed.
     */
    public String printMarkTaskDoneMessage(Task t) {
        String str = "Woo-pah! I've marked this task as done:" + "\n" + t.toString();
        return str;
    }

    /**
     * Prints a confirmation message indicating that a task has been marked as not done.
     *
     * @param t The task that was marked as not completed.
     */
    public String printUnmarkTaskDoneMessage(Task t) {
        String str = "OK, I've marked this task as not done yet:" + "\n" + t.toString();
        return str;
    }

    /**
     * Prints a confirmation message indicating that a task has been added.
     *
     * @param t            The task that was added.
     * @param taskListSize The total number of tasks after the addition.
     */
    public String printAddTaskMessage(Task t, int taskListSize) {
        String str = "Got it. I've added this task:" + "\n" + t.toString() + "\n"
                + "Now you have " + taskListSize + " tasks in the list.";
        return str;
    }

    /**
     * Prints a confirmation message indicating that a task has been deleted.
     *
     * @param t            The task that was removed.
     * @param taskListSize The total number of tasks after deletion.
     */
    public String printDeleteTaskMessage(Task t, int taskListSize) {
        String str = "Noted. I've removed this task:" + "\n" + t.toString() + "\n"
                + "Now you have " + taskListSize + " tasks in the list.";
        return str;
    }

    /**
     * Prints an error message to inform the user of an invalid action or command.
     *
     * @param err The error message to be displayed.
     */
    public String printErrorMessage(String err) {
        String str = "Woopsie! " + err;
        return str;
    }

    /**
     * Prints the list of tasks that match a search query.
     * Displays a message indicating no matches if the list is empty.
     *
     * @param taskList List of tasks that matched the search criteria.
     */
    public String printFindTasksMessage(ArrayList<Task> taskList) {
        String str = "";
        if (taskList.isEmpty()) {
            str = "Woop! No matching tasks found.";
        } else {
            for (int i = 0; i < taskList.size(); i++) {
                str += (i + 1) + ". " + taskList.get(i).toString() + "\n";
            }
        }
        return str;
    }

    /**
     * Prints a confirmation message indicating that a task has been updated.
     *
     * @param t The task that was updated.
     */
    public String printUpdateTaskMessage(Task t) {
        String str = "Woo-pah! I've updated this task:" + "\n" + t.toString();
        return str;
    }

    /**
     * Prints a message indicating that the command entered is not recognised.
     */
    public String printUnknownCommandMessage() {
        return "Wooooo-pah? I don't understand this command.";
    }
}
