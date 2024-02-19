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

public class MovingCharacter extends Application {
    private ResizableCanvas canvas;
    private int i = 0;
    private BufferedImage[] tiles;
    private BufferedImage[] running;
    private BufferedImage[] jump;
    private BufferedImage image;
    private double x = 600;
    private boolean turned = true;

    @Override
    public void start(Stage stage) throws Exception {

        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        try {
            image = ImageIO.read((new File("Week3/002.MovingCharacter/resources/images/sprite.png")));
            tiles = new BufferedImage[57];
            running = new BufferedImage[32];
            jump = new BufferedImage[48];

            for (int i = 0; i < 57; i++) {
                tiles[i] = image.getSubimage(64 * (i % 8), 64 * (i % 9), 64, 64);
            }
            for (int j = 0; j < 16; j++) {
                running[j] = tiles[j];
            }
            for (int j = 0; j < 16; j++) {
                running[16 + j] = tiles[16 - j];
            }
//            for (int j = 0; j < ; j++) {
//
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }

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
        stage.setTitle("Moving Character");
        stage.show();
        draw(g2d);
    }


    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        AffineTransform tx = new AffineTransform();
        tx.translate(x, 250);
        if (turned){
            tx.scale(-1,1);
        }
        graphics.drawImage(running[i], tx, null);
    }


    public void update(double deltaTime) throws InterruptedException {
        Thread.sleep(250);
        i++;
        if (i > 31) {
            i = 0;
        }
        if (x >= 600) {
            turned = true;
        } else if (x <= 30) {
            turned = false;
        }
        if (turned){
            x -= 10;
        } else {
            x += 10;
        }
        System.out.println(x);
    }

    public static void main(String[] args) {
        launch(MovingCharacter.class);
    }

}
