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

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.UnaryOperator;

public class HighScorePopUp {
    private Popup popup;
    private Pinball pinball;
    private int score = 0;

    public HighScorePopUp(Pinball pinball) {
        this.pinball = pinball;
        this.popup = new Popup();

        BorderPane borderPane = new BorderPane();

        Label labelHS = new Label("New Highscore!");
        Label label = new Label("Enter your name:");

        labelHS.setFont(getFontFX(50));

        Font font = getFontFX(15);
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
                pinball.resetLivesAndScore();
            }
        });

        textField.setOnAction(event -> {
            if (!textField.getText().isEmpty()) {
                HighScoreWriter writer = pinball.getHighScoreWriter();
                writer.addHighScore(textField.getText(), score);
                popup.hide();
                pinball.getBall().setMassType(MassType.NORMAL);
                pinball.resetLivesAndScore();
            }
        });
    }

    public javafx.scene.text.Font getFontFX(double size) {
        try {
            InputStream input = new BufferedInputStream(new FileInputStream("Eindopdracht/src/Frame/MiscFiles/unispace bd.ttf"));
            javafx.scene.text.Font font = javafx.scene.text.Font.loadFont(input, size);
            return font;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public Popup getPopup(int score) {
        this.score = score;
        return popup;
    }
}
