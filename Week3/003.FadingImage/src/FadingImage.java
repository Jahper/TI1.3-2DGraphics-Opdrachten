import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javafx.animation.AnimationTimer;
import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class FadingImage extends Application {
    private ResizableCanvas canvas;
    private float fade = 0.05f;
    private BufferedImage image;
    private BufferedImage image1;
    private BufferedImage image2;
    private boolean waitTime = false;


    @Override
    public void start(Stage stage) throws Exception {
        image = ImageIO.read(new File("Week3/003.FadingImage/rec/20230731135539_1.jpg"));
        image1 = ImageIO.read(new File("Week3/003.FadingImage/rec/20230204220134_1.jpg"));
        image2 = ImageIO.read(new File("Week3/003.FadingImage/rec/theSnitch.png"));

        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now) {
                if (last == -1)
                    last = now;
                try {
                    update((now - last) / 1000000000.0);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                last = now;
                draw(g2d);
            }
        }.start();

        stage.setScene(new Scene(mainPane));
        stage.setTitle("Fading image");
        stage.show();
        draw(g2d);
    }


    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        AffineTransform tx = new AffineTransform();
        tx.translate(0, 0);
        if (waitTime) {
            graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, fade));
            graphics.drawImage(image, tx, null);
            graphics.drawImage(image1, tx, null);
        } else {
            graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, fade));
            graphics.drawImage(image1, tx, null);
            graphics.drawImage(image, tx, null);
        }
    }


    public void update(double deltaTime) throws InterruptedException {
        fade += 0.001f;
        System.out.println(fade);
        if (fade >= 0.95f){
            fade = 0.1f;
            waitTime = !waitTime;
        }
    }

    public static void main(String[] args) {
        launch(FadingImage.class);
    }

}
