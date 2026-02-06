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


    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the Wooper instance
     */
    public void setWooper(Wooper w) {
        wooper = w;
        String greeting = wooper.initWooper();
        dialogContainer.getChildren().add(
                WooperDialogBox.getWooperDialog(greeting)
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
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
