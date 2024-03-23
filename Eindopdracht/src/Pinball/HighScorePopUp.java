package Pinball;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class HighScorePopUp {
    private Popup popup;
    private Pinball pinball;

    public HighScorePopUp(Pinball pinball) {
        this.pinball = pinball;
        this.popup = new Popup();

        BorderPane borderPane = new BorderPane();

        Label label = new Label("Enter your name:");

        TextField textField = new TextField();

        Button okButton = new Button("OK");

        borderPane.setTop(label);
        borderPane.setCenter(textField);
        borderPane.setBottom(okButton);

        popup.getContent().add(borderPane);


        okButton.setOnAction(e -> {
            if (!textField.getText().isEmpty()) {

            }
        });
    }


    public Popup getPopup() {
        return popup;
    }
}
