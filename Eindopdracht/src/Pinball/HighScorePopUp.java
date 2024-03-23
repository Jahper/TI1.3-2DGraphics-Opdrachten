package Pinball;

import Util.HighScoreWriter;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
import org.dyn4j.geometry.MassType;

import java.util.function.UnaryOperator;

public class HighScorePopUp {
    private Popup popup;
    private Pinball pinball;

    public HighScorePopUp(Pinball pinball, int score) {
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

        //maakt text in textfield maximaal x lang
        UnaryOperator<TextFormatter.Change> rejectChange = change -> {
            if (change.isContentChange()) {
                if (change.getControlNewText().length() > 4) {
                    return null;
                }
            }
            return change;
        };

        textField.setTextFormatter(new TextFormatter(rejectChange));

        okButton.setOnAction(e -> {
            if (!textField.getText().isEmpty()) {
                HighScoreWriter writer = pinball.getHighScoreWriter();
                writer.addHighScore(textField.getText(), score);
                popup.hide();
                pinball.getBall().setMassType(MassType.NORMAL);
                writer.printScores();
            }
        });
    }


    public Popup getPopup() {
        return popup;
    }
}
