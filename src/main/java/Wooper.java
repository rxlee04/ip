import java.util.Scanner;
import java.util.ArrayList;

public class Wooper {
    public static void main(String[] args) {

        // create scanner
        Scanner scanner = new Scanner(System.in);

        // Task List
        ArrayList<Task> taskList = new ArrayList<>();

        String divider = "____________________________________________________________";
        String greetMsg = divider + "\n Woo-pah! I'm Wooper\n" +
                " What can I do for you? \n" + divider;

        String addTaskMsg = "Got it. I've added this task:";

        String exitMsg = divider + "\n Woop Woop! Hope to see you again soon!\n" + divider;

        // print greeting msg
        System.out.println(greetMsg);

        // get user input
        String userInput = "";

        while (!userInput.equals("bye")) {
            try {
                userInput = scanner.nextLine();
                System.out.println(divider);

                // "list"
                if (userInput.equals("list")) {
                    System.out.println(" Here are the tasks in your list:");
                    for (int i = 0; i < taskList.size(); i++) {
                        System.out.println((i + 1) + ". " + taskList.get(i).toString());
                    }
                } else if (userInput.startsWith("mark")) { // "mark"
                    String taskNoStr = userInput.substring(userInput.indexOf("mark") + 5);
                    int taskNo = Integer.parseInt(taskNoStr) - 1;
                    if (taskNo < 0 || taskNo >= taskList.size()) {
                        throw new WooperException("Woop! Choose a number from the task list!");
                    }
                    taskList.get(taskNo).setDone(true);
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println(taskList.get(taskNo).toString());
                } else if (userInput.startsWith("unmark")) { // "unmark"
                    String taskNoStr = userInput.substring(userInput.indexOf("unmark") + 7);
                    int taskNo = Integer.parseInt(taskNoStr) - 1;
                    if (taskNo < 0 || taskNo >= taskList.size()) {
                        throw new WooperException("Woop! Choose a number from the task list!");
                    }
                    taskList.get(taskNo).setDone(false);
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println(taskList.get(taskNo).toString());
                } else if (userInput.startsWith("todo")) { // to do
                    String taskStr = userInput.substring(userInput.indexOf("todo") + 5).trim();
                    if (taskStr.isEmpty()) {
                        throw new WooperException("Woop! Please give the todo a description!");
                    }
                    ToDo task = new ToDo(taskStr);
                    taskList.add(task);
                    System.out.println(addTaskMsg);
                    System.out.println(task.toString());
                    System.out.println("Now you have " + taskList.size() + " tasks in the list.");
                } else if (userInput.startsWith("deadline")) { // deadline
                    String taskStr = userInput.substring(userInput.indexOf("deadline") + 9, userInput.indexOf("/by")).trim();
                    if (taskStr.isEmpty()) {
                        throw new WooperException("Woop! Please give the deadline a description!");
                    }
                    String dl = userInput.substring(userInput.indexOf("/by") + 4).trim();
                    if (dl.isEmpty()) {
                        throw new WooperException("Woop! Please give a deadline!");
                    }
                    Deadline task = new Deadline(taskStr, dl);
                    taskList.add(task);
                    System.out.println(addTaskMsg);
                    System.out.println(task.toString());
                    System.out.println("Now you have " + taskList.size() + " tasks in the list.");

                } else if (userInput.startsWith("event")) { // event
                    String taskStr = userInput.substring(userInput.indexOf("event") + 6, userInput.indexOf("/from")).trim();
                    if (taskStr.isEmpty()) {
                        throw new WooperException("Woop! Please give the event a description!");
                    }
                    String sdl = userInput.substring(userInput.indexOf("/from") + 6, userInput.indexOf("/to")).trim();
                    String edl = userInput.substring(userInput.indexOf("/to") + 4).trim();
                    if (sdl.isEmpty() || edl.isEmpty()) {
                        throw new WooperException("Woop! Please give event's start and/or end date!");
                    }
                    Event task = new Event(taskStr, sdl, edl);
                    taskList.add(task);
                    System.out.println(addTaskMsg);
                    System.out.println(task.toString());
                    System.out.println("Now you have " + taskList.size() + " tasks in the list.");
                } else if (userInput.startsWith("delete")) {
                    String taskNoStr = userInput.substring(userInput.indexOf("delete") + 7);
                    int taskNo = Integer.parseInt(taskNoStr) - 1;
                    if (taskNo < 0 || taskNo >= taskList.size()) {
                        throw new WooperException("Woop! Choose a number from the task list!");
                    }
                    Task temp = taskList.get(taskNo);
                    taskList.remove(taskNo);
                    System.out.println("Noted. I've removed this task:");
                    System.out.println(temp.toString());
                    System.out.println("Now you have " + taskList.size() + " tasks in the list.");
                } else if (!userInput.equals("bye")) {
                    throw new WooperException("Wooooo-pah? I don't understand this command.");
                }
            } catch (WooperException e) {
                System.out.println(divider);
                System.out.println(e.getMessage());
                System.out.println(divider);
            }
            System.out.println(divider);
        }

        // print exit msg
        System.out.println(exitMsg);

        // this is a test commit to check for sourcetree
    }
}
