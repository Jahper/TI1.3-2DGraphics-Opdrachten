import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

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

public class Spotlight extends Application {
    private ResizableCanvas canvas;
    private double x = 100;
    private double y = 100;
    private int spotlight = 0;
    private ArrayList<Shape> shapes = new ArrayList<>();
    private ArrayList<Integer> l1 = new ArrayList<>();
    private ArrayList<Integer> l2 = new ArrayList<>();
    private ArrayList<Integer> l3 = new ArrayList<>();
    private ArrayList<Integer> l4 = new ArrayList<>();
    private ArrayList<Color> colors = new ArrayList<>();

    @Override
    public void start(Stage stage) throws Exception {

        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        init(g2d);
        new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now) {
                if (last == -1)
                    last = now;
                update((now - last) / 1000000000.0);
                last = now;
                draw(g2d);
            }
        }.start();

        canvas.setOnMouseMoved(event -> {
            x = event.getX() -150;
            y = event.getY() -150;
        });
        canvas.setOnMouseClicked(event -> spotlight++);

        stage.setScene(new Scene(mainPane));
        stage.setMaximized(true);
        stage.setTitle("Spotlight");
        stage.show();
        draw(g2d);
    }


    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        AffineTransform tx = new AffineTransform();
        tx.translate(x,y);

        Shape temp = tx.createTransformedShape(shapes.get(spotlight));
        graphics.setClip(temp);

        for (int i = 0; i < 1000; i++) {
            graphics.setColor(colors.get(i));
            graphics.drawLine(l1.get(i), l2.get(i), l3.get(i), l4.get(i));
        }
        graphics.setClip(null);
    }

    public void init(FXGraphics2D g2d) {
        shapes.add(new Rectangle2D.Double(x,y,100,100));
        shapes.add(new Ellipse2D.Double(x,y,100, 100));

        Random r = new Random();
        for (int i = 0; i < 1000; i++) {
            l1.add(r.nextInt(2560));
            l2.add(r.nextInt(1440));
            l3.add(r.nextInt(2560));
            l4.add(r.nextInt(1440));
            colors.add(Color.getHSBColor(r.nextFloat(), 1, 1));
        }
    }

    public void update(double deltaTime) {
        if (spotlight >= 2){
            spotlight = 0;
        }
    }

    public static void main(String[] args) {
        launch(Spotlight.class);
    }

}
