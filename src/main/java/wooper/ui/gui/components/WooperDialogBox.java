package wooper.ui.gui.components;


import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import wooper.ui.gui.MainWindow;

/**
 * Represents a dialog box used to display Wooper's responses in the GUI.
 * Displays a message label alongside Wooper's image.
 */
public class WooperDialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private Circle displayPicture;

    private Image wooperImage = new Image(this.getClass().getResourceAsStream("/images/wooper_img.jpg"));

    private WooperDialogBox(String text) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    MainWindow.class.getResource("/view/components/WooperDialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        displayPicture.setFill(new ImagePattern(wooperImage));

        dialog.setText(text);
        if (isError(text)) {
            dialog.setStyle(
                    "-fx-background-color: #FDE2E4;"
                            + "-fx-background-radius: 12;"
                            + "-fx-padding: 10px;"
                            + "-fx-text-fill: #7A1E1E;"
            );
        }

    }

    public static WooperDialogBox getWooperDialog(String text) {
        var db = new WooperDialogBox(text);
        return db;
    }

    private boolean isError(String text) {
        return text.startsWith("Woopsie!");
    }
}
