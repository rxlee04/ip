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
        String userInput = scanner.nextLine();

        while (!userInput.equals("bye")) {
            System.out.println(divider);

            // "list"
            if (userInput.equals("list")) {
                System.out.println(" Here are the tasks in your list:");
                for (int i = 0; i < taskList.size(); i++) {
                    System.out.println((i + 1) + ". " + taskList.get(i).toString());
                }
            } else if (userInput.startsWith("mark")) { // "mark"
                String taskNoStr = userInput.substring(userInput.indexOf("mark") + 5);
                int taskNo = Integer.parseInt(taskNoStr);
                taskList.get(taskNo - 1).setDone(true);
                System.out.println("Nice! I've marked this task as done:");
                System.out.println(taskList.get(taskNo - 1).toString());
            } else if (userInput.startsWith("unmark")) { // "unmark"
                String taskNoStr = userInput.substring(userInput.indexOf("unmark") + 7);
                int taskNo = Integer.parseInt(taskNoStr);
                taskList.get(taskNo - 1).setDone(false);
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println(taskList.get(taskNo - 1).toString());
            } else if (userInput.startsWith("todo")) { // to do
                String taskStr = userInput.substring(userInput.indexOf("todo") + 5);
                ToDo task = new ToDo(taskStr);
                taskList.add(task);
                System.out.println(addTaskMsg);
                System.out.println(task.toString());
                System.out.println("Now you have " + taskList.size() + " tasks in the list.");
            } else if (userInput.startsWith("deadline")) { // deadline
                String taskStr = userInput.substring(userInput.indexOf("deadline") + 9, userInput.indexOf("/by"));
                String dl = userInput.substring(userInput.indexOf("/by") + 4);
                Deadline task = new Deadline(taskStr, dl);
                taskList.add(task);
                System.out.println(addTaskMsg);
                System.out.println(task.toString());
                System.out.println("Now you have " + taskList.size() + " tasks in the list.");

            } else if (userInput.startsWith("event")) { // event
                String taskStr = userInput.substring(userInput.indexOf("event") + 6, userInput.indexOf("/from"));
                String sdl = userInput.substring(userInput.indexOf("/from") + 6, userInput.indexOf("/to"));
                String edl = userInput.substring(userInput.indexOf("/to") + 4);
                Event task = new Event(taskStr, sdl, edl);
                taskList.add(task);
                System.out.println(addTaskMsg);
                System.out.println(task.toString());
                System.out.println("Now you have " + taskList.size() + " tasks in the list.");
            }
            System.out.println(divider);
            userInput = scanner.nextLine();
        }

        // print exit msg
        System.out.println(exitMsg);
    }
}
