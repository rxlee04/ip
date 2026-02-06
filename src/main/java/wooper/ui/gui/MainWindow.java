package wooper.ui.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import wooper.Wooper;
import wooper.ui.gui.components.UserDialogBox;
import wooper.ui.gui.components.WooperDialogBox;

/**
 * Controls the main window of the Wooper graphical user interface.
 * Handles user interactions, displays dialog messages, and communicates
 * with the {@link Wooper} application logic.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Wooper wooper;

    /**
     * Initialises the main window after its FXML elements have been loaded.
     * Binds the scroll pane to automatically scroll as new dialog boxes are added.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the Wooper application instance into the main window.
     * Displays the greeting message returned by the application upon initialisation.
     *
     * @param w Wooper application logic instance.
     */
    public void setWooper(Wooper w) {
        wooper = w;
        String greeting = wooper.initWooper();
        dialogContainer.getChildren().add(
                WooperDialogBox.getWooperDialog(greeting)
        );
    }

    /**
     * Handles user input submitted through the text field.
     * Displays the user's input and the corresponding response from the Wooper
     * application. Exits the application if the user enters the {@code bye} command.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = wooper.getResponse(input);
        dialogContainer.getChildren().addAll(
                UserDialogBox.getUserDialog(input),
                WooperDialogBox.getWooperDialog(response)
        );
        userInput.clear();

        if (input.trim().equalsIgnoreCase("bye")) {
            Platform.exit();
        }
    }
}
