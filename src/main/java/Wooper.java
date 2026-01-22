import java.util.Scanner;

public class Wooper {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        String divider = "____________________________________________________________";
        String greetMsg = divider + "\n Woo-pah! I'm Wooper\n" +
                " What can I do for you? \n" + divider;

        String exitMsg = divider + "\n Woop Woop!. Hope to see you again soon!\n" + divider;
        System.out.println(greetMsg);

        String userInput = scanner.nextLine();

        while (!userInput.equals("bye")) {
            System.out.println(divider);
            System.out.println(userInput);
            System.out.println(divider);
            userInput = scanner.nextLine();
        }
        System.out.println(exitMsg);
    }
}
