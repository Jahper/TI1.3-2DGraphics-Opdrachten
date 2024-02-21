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
    private BufferedImage[] running;
    private BufferedImage[] jump;
    private double x = 600;
    private double y = 250;
    private boolean turned = true;
    private boolean jumping = false;
    private BufferedImage[] animation = new BufferedImage[57];
    @Override
    public void start(Stage stage) throws Exception {

        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        createBufferedImages();

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

        canvas.setOnMousePressed(event -> {
            jumping = true;
            i = 0;
        });

        stage.setScene(new Scene(mainPane));
        stage.setTitle("Moving Character");
        stage.show();
        draw(g2d);
    }
    private void createBufferedImages() {
        try {
            BufferedImage image = ImageIO.read((new File("Week3/002.MovingCharacter/resources/images/sprite.png")));
            running = new BufferedImage[11];
            jump = new BufferedImage[25];
            //running animation
            for (int j = 0; j < 8; j++) {
                running[j] = image.getSubimage(64 * (j % 8), 0, 64, 64);
            }
            for (int j = 0; j < 4; j++) {
                running[j] = image.getSubimage(64 * (j % 8), 64, 64, 64);
            }
            //jumping animation
            for (int j = 0; j < 8; j++) {
                jump[j] = image.getSubimage(64 * (j % 8), 64, 64, 64);
            }
            for (int j = 0; j < 8; j++) {
                jump[8 + j] = image.getSubimage(64 * (j % 8), 320, 64, 64);
            }
            for (int j = 0; j < 3; j++) {
                jump[16 + j] = image.getSubimage(64 * (j % 8), 448, 64, 64);
            }
            for (int j = 0; j < 6; j++) {
                jump[19 + j] = image.getSubimage(64 * (j % 8), 64, 64, 64);
            }
            animation = running;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        AffineTransform tx = new AffineTransform();
        tx.translate(x, y);
        if (turned) {
            tx.scale(-1, 1);
        }
        graphics.drawImage(animation[i], tx, null);
    }
    public void update(double deltaTime) throws InterruptedException {
        Thread.sleep(25);
        i++;
        if (jumping){
            if (i > 24){
                i = 0;
                jumping = false;
            }
            if (i >= 13 && i <= 16){
                y -= 10;
            } else if (i >= 17 && i <= 20) {
                y += 10;
            } else {
                y = 250;
            }
            animation = jump;
        } else {
            animation = running;
            if (i > 7) {
                i = 0;
            }
        }

        if (x >= 600) {
            turned = true;
        } else if (x <= 30) {
            turned = false;
        }
        if (turned) {
            x -= 10;
        } else {
            x += 10;
        }
        System.out.println(i);
    }
    public static void main(String[] args) {
        launch(MovingCharacter.class);
    }
}
