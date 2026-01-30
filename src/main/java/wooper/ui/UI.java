package wooper.ui;

import wooper.task.Task;

import java.util.ArrayList;
import java.util.Scanner;

public class UI {

    private final Scanner scanner = new Scanner(System.in);
    private final String divider = "____________________________________________________________";

    public String getUserCommand() {
        return scanner.nextLine();
    }

    public void printGreetingMessage() {
        String greetMsg = "Woo-pah! I'm wooper.Wooper\n What can I do for you?";
        System.out.println(divider);
        System.out.println(greetMsg);
        System.out.println(divider);
    }

    public void printExitMessage() {
        String exitMsg = "Woop Woop! Hope to see you again soon!";
        System.out.println(divider);
        System.out.println(exitMsg);
        System.out.println(divider);
    }

    public void printTaskList(ArrayList<Task> taskList) {
        System.out.println(divider);
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < taskList.size(); i++) {
            System.out.println((i + 1) + ". " + taskList.get(i).toString());
        }
        System.out.println(divider);
    }

    public void printMarkTaskDoneMessage(Task t) {
        System.out.println(divider);
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(t.toString());
        System.out.println(divider);
    }

    public void printUnmarkTaskDoneMessage(Task t) {
        System.out.println(divider);
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(t.toString());
        System.out.println(divider);
    }

    public void printAddTaskMessage(Task t, int taskListSize) {
        String addTaskMsg = "Got it. I've added this task:";
        System.out.println(divider);
        System.out.println(addTaskMsg);
        System.out.println(t.toString());
        System.out.println("Now you have " + taskListSize + " tasks in the list.");
        System.out.println(divider);
    }

    public void printDeleteTaskMessage(Task t, int taskListSize) {
        System.out.println(divider);
        System.out.println("Noted. I've removed this task:");
        System.out.println(t.toString());
        System.out.println("Now you have " + taskListSize + " tasks in the list.");
        System.out.println(divider);
    }

    public void printErrorMessage(String err) {
        System.out.println(divider);
        System.out.println(err);
        System.out.println(divider);
    }
}
