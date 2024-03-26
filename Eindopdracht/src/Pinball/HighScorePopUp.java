package Pinball;

import Util.HighScoreWriter;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import org.dyn4j.geometry.MassType;

import java.util.function.UnaryOperator;

public class HighScorePopUp {
    private Popup popup;
    private Pinball pinball;

    public HighScorePopUp(Pinball pinball, int score) {
        //todo mooi in het midden zetten
        this.pinball = pinball;
        this.popup = new Popup();

        BorderPane borderPane = new BorderPane();

        Label labelHS = new Label("New Highscore!");
        Label label = new Label("Enter your name:");

        Font fontHS = new Font("Berlin Sans FB", 50);
        labelHS.setFont(fontHS);

        Font font = new Font("Berlin Sans FB", 15);
        label.setFont(font);

        VBox labelBox = new VBox(labelHS, label);

        TextField textField = new TextField();
        textField.setFont(font);
        textField.setMaxWidth(145);
        textField.setMinWidth(145);

        Button okButton = new Button("OK");
        okButton.setFont(font);

        borderPane.setTop(labelBox);
        borderPane.setLeft(textField);
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

        textField.setTextFormatter(new TextFormatter<>(rejectChange));

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
