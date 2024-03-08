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

        stage.setScene(new Scene(mainPane));
        stage.setTitle("Screensaver");
        stage.show();
        draw(graphics);
    }


    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        graphics.setColor(Color.BLACK);

//        MovingPoint movingPoint = points.get(0).get(0);


//        for (int i = 0; i <; i++) {
//
//        }
        GeneralPath path = new GeneralPath();
        path.moveTo(movingPoints.get(0).getPoint().getX(), movingPoints.get(0).getPoint().getY());
        for (int i = 0; i < movingPoints.size(); i++) {
            Point2D p = movingPoints.get(i).getPoint();
            path.lineTo(p.getX(), p.getY());
        }
        path.closePath();
        graphics.draw(path);
    }

    public void init() {
        ArrayList<MovingPoint> points1 = new ArrayList<>();
        ArrayList<MovingPoint> points2 = new ArrayList<>();
        ArrayList<MovingPoint> points3 = new ArrayList<>();
        ArrayList<MovingPoint> points4 = new ArrayList<>();
        ArrayList<MovingPoint> points5 = new ArrayList<>();
        ArrayList<MovingPoint> points6 = new ArrayList<>();
        ArrayList<MovingPoint> points7 = new ArrayList<>();
        ArrayList<MovingPoint> points8 = new ArrayList<>();
        ArrayList<MovingPoint> points9 = new ArrayList<>();
        ArrayList<MovingPoint> points10 = new ArrayList<>();


//        points.add(points1);
        for (int i = 0; i < 7; i++) {
            movingPoints.add(new MovingPoint());
        }

//        points.add(points2);
//        points.add(points3);
//        points.add(points4);
//        points.add(points5);
//        points.add(points6);
//        points.add(points7);
//        points.add(points8);
//        points.add(points9);
//        points.add(points10);
    }

    public void update(double deltaTime) {
        for (int i = 0; i < movingPoints.size(); i++) {
//            ArrayList<MovingPoint> point = points.get(0);

//            for (int j = 0; j < point.size() - 1; j++) {
//                ArrayList<MovingPoint> clone = new ArrayList<>(points.get(point.size() - j - 1));
//                points.set(point.size() - j, clone);
//            }

            for (MovingPoint movingPoint : movingPoints) {
                Point2D p = movingPoint.getPoint();
                Point2D pUpdate = movingPoint.getDirection();
                movingPoint.setPoint(new Point2D.Double(p.getX() + pUpdate.getX(), p.getY() + pUpdate.getY()));
                checkForEdge(movingPoint);
            }


//            if (i < point.size() - 1) {
//                if (!point.equals(points.get(i + 1))) {
//                    ArrayList<MovingPoint> clone = new ArrayList<>(point);
//                    if (i < points.size() - 1) {
//                        points.set(i + 1, clone);
//                    }
//
//                }
//            }
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
