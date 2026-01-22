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

        String exitMsg = divider + "\n Woop Woop! Hope to see you again soon!\n" + divider;

        // print greeting msg
        System.out.println(greetMsg);

        // get user input
        String userInput = scanner.nextLine();

        while (!userInput.equals("bye")) {
            System.out.println(divider);
            if (userInput.equals("list")) {
                for (int i = 0; i < taskList.size(); i++) {
                    System.out.println((i + 1) + ". " + taskList.get(i).getTaskName());
                }
            } else if (!userInput.equals("bye")) {
                taskList.add(new Task(userInput));
                System.out.println("added: " + userInput);
            }
            System.out.println(divider);
            userInput = scanner.nextLine();
        }

        // print exit msg
        System.out.println(exitMsg);
    }
}
