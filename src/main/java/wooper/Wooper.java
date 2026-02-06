package wooper;

/**
 * Launches the Wooper task management application.
 */
public class Wooper {
    public static void main(String[] args) {
        new WooperController().run();
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        return "Duke heard: " + input;
    }
}
