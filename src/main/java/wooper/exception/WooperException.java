package wooper.exception;

/**
 * Represent an exception specific to the Wooper application.
 * Thrown when an invalid command, input, or operation is encountered.
 */
public class WooperException extends Exception {

    /**
     * Creates a WooperException with the specified error message.
     *
     * @param message Description of the error.
     */
    public WooperException(String message) {
        super(message);
    }
}
