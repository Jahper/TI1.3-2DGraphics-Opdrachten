import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;

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

public class Screensaver extends Application {
    private ResizableCanvas canvas;
    private FXGraphics2D graphics;
    private ArrayList<MovingPoint> movingPoints = new ArrayList<>();
    private ArrayList<GeneralPath> paths = new ArrayList<>();


    @Override
    public void start(Stage stage) throws Exception {

        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        graphics = new FXGraphics2D(canvas.getGraphicsContext2D());
        new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now) {
                if (last == -1)
                    last = now;
                update((now - last) / 1000000000.0);
                last = now;
                draw(graphics);
            }
        }.start();

        canvas.setOnMouseClicked(e -> {
            movingPoints.clear();
            paths.clear();
            init();
        });

        stage.setScene(new Scene(mainPane));
        stage.setTitle("Screensaver");
        stage.setMaximized(true);
        stage.show();
        draw(graphics);
    }


    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.BLACK);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        graphics.setColor(Color.MAGENTA);

        for (GeneralPath path : paths) {
            graphics.draw(path);
        }
    }

    public void init() {
        for (int i = 0; i < 5; i++) {
            movingPoints.add(new MovingPoint());
        }
    }

    public void update(double deltaTime) {
        for (int i = 0; i < movingPoints.size(); i++) {
            for (MovingPoint movingPoint : movingPoints) {
                Point2D p = movingPoint.getPoint();
                Point2D pUpdate = movingPoint.getDirection();
                movingPoint.setPoint(new Point2D.Double(p.getX() + pUpdate.getX(), p.getY() + pUpdate.getY()));
                checkForEdge(movingPoint);
            }
            GeneralPath path = new GeneralPath();
            path.moveTo(movingPoints.get(0).getPoint().getX(), movingPoints.get(0).getPoint().getY());
            for (MovingPoint movingPoint : movingPoints) {
                Point2D p = movingPoint.getPoint();
                path.lineTo(p.getX(), p.getY());
            }
            path.closePath();
            paths.add(path);

            if (paths.size() > 30) {
                paths.remove(0);
            }
        }
        draw(graphics);
    }

    private void checkForEdge(MovingPoint m) {
        Point2D p = m.getPoint();
        Point2D pDirection = m.getDirection();
        if (p.getX() < 0) {
            m.setDirection(new Point2D.Double(-1 * pDirection.getX(), pDirection.getY()));
        } else if (p.getX() > canvas.getWidth()) {
            m.setDirection(new Point2D.Double(-1 * pDirection.getX(), pDirection.getY()));
        } else if (p.getY() < 0) {
            m.setDirection(new Point2D.Double(pDirection.getX(), -1 * pDirection.getY()));
        } else if (p.getY() > canvas.getHeight()) {
            m.setDirection(new Point2D.Double(pDirection.getX(), -1 * pDirection.getY()));
        }
    }

    public static void main(String[] args) {
        launch(Screensaver.class);
    }

}
