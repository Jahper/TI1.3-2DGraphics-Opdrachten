package Pinball;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Popup;

public class GameOverPopUp {
    private Popup popup;

    public GameOverPopUp() {
        this.popup = new Popup();
        setPopupContent();
    }

    private void setPopupContent() {
        popup.getContent().add(new ImageView(new Image("Frame/MiscFiles/gameOver.png")));
    }

    public Popup getPopup() {
        return popup;
    }
}
