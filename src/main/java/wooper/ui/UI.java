package wooper.ui;

import java.util.ArrayList;
import java.util.Scanner;

import wooper.task.Task;

/**
 * Handles user interaction for the Wooper application.
 * Reads commands from standard input and displays messages, task lists,
 * and feedback to the user via standard output.
 */
public class UI {
    private final Scanner scanner = new Scanner(System.in);
    private final String divider = "____________________________________________________________";

    /**
     * Returns the next command entered by the user.
     *
     * @return The user input command as a string.
     */
    public String getUserCommand() {
        return scanner.nextLine();
    }

    /**
     * Prints the greeting message shown when the application starts.
     */
    public void printGreetingMessage() {
        String greetMsg = "Woo-pah! I'm Wooper\n What can I do for you?";
        System.out.println(divider);
        System.out.println(greetMsg);
        System.out.println(divider);
    }

    /**
     * Prints the farewell message shown when the application exits.
     */
    public void printExitMessage() {
        String exitMsg = "Woop Woop! Hope to see you again soon!";
        System.out.println(divider);
        System.out.println(exitMsg);
        System.out.println(divider);
    }

    /**
     * Prints the list of tasks currently stored in the application.
     *
     * @param taskList List of tasks to be displayed.
     */
    public void printTaskList(ArrayList<Task> taskList) {
        System.out.println(divider);
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < taskList.size(); i++) {
            System.out.println((i + 1) + ". " + taskList.get(i).toString());
        }
        System.out.println(divider);
    }

    /**
     * Prints a confirmation message indicating that a task has been marked as done.
     *
     * @param t The task that was marked as completed.
     */
    public void printMarkTaskDoneMessage(Task t) {
        System.out.println(divider);
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(t.toString());
        System.out.println(divider);
    }

    /**
     * Prints a confirmation message indicating that a task has been marked as not done.
     *
     * @param t The task that was marked as not completed.
     */
    public void printUnmarkTaskDoneMessage(Task t) {
        System.out.println(divider);
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(t.toString());
        System.out.println(divider);
    }

    /**
     * Prints a confirmation message indicating that a task has been added.
     *
     * @param t The task that was added.
     * @param taskListSize The total number of tasks after the addition.
     */
    public void printAddTaskMessage(Task t, int taskListSize) {
        String addTaskMsg = "Got it. I've added this task:";
        System.out.println(divider);
        System.out.println(addTaskMsg);
        System.out.println(t.toString());
        System.out.println("Now you have " + taskListSize + " tasks in the list.");
        System.out.println(divider);
    }

    /**
     * Prints a confirmation message indicating that a task has been deleted.
     *
     * @param t The task that was removed.
     * @param taskListSize The total number of tasks after deletion.
     */
    public void printDeleteTaskMessage(Task t, int taskListSize) {
        System.out.println(divider);
        System.out.println("Noted. I've removed this task:");
        System.out.println(t.toString());
        System.out.println("Now you have " + taskListSize + " tasks in the list.");
        System.out.println(divider);
    }

    /**
     * Prints an error message to inform the user of an invalid action or command.
     *
     * @param err The error message to be displayed.
     */
    public void printErrorMessage(String err) {
        System.out.println(divider);
        System.out.println(err);
        System.out.println(divider);
    }

    /**
     * Prints the list of tasks that match a search query.
     * Displays a message indicating no matches if the list is empty.
     *
     * @param taskList List of tasks that matched the search criteria.
     */
    public void printFindTasksMessage(ArrayList<Task> taskList) {
        System.out.println(divider);
        if (taskList.isEmpty()) {
            System.out.println("Woop! No matching tasks found.");
        } else {
            for (int i = 0; i < taskList.size(); i++) {
                System.out.println((i + 1) + ". " + taskList.get(i).toString());
            }
        }
        System.out.println(divider);
    }
}
