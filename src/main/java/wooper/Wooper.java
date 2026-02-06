package wooper;

/**
 * Launches the Wooper task management application.
 */
public class Wooper {
    private WooperController wooperController;

    public Wooper() {
        wooperController = new WooperController();
    }

    public String initWooper() {
        return wooperController.init();
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        String response = wooperController.handleUserInput(input);
        return response;
    }
}
