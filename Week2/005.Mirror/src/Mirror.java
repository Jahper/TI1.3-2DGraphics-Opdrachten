import java.awt.*;
import java.awt.geom.*;

import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class Mirror extends Application {
    ResizableCanvas canvas;

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("Mirror");
        primaryStage.show();
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }


    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        graphics.translate(canvas.getWidth() / 2, canvas.getHeight() / 2);
        graphics.scale(1, -1);


        //assenstelsel
        GeneralPath path = new GeneralPath();
        graphics.setColor(Color.BLACK);
        path.moveTo(0, 0);
        path.lineTo(0, 500);
        path.lineTo(0, -500);
        path.closePath();
        path.lineTo(500, 0);
        path.lineTo(-500, 0);

        graphics.draw(path);

//        graphics.drawRect(-50,100,100,100);
        Rectangle2D rectangle2D = new Rectangle(-50, 100, 100, 100);
        Point2D position = new Point2D.Double(0, -150);


        double formula1 = (2 / ((1 + Math.pow(2.5, 2)))) - 1;
        double formula2 = ((2 * 2.5) / (1 + Math.pow(2.5, 2)));
        double formula3 = ((2 * Math.pow(2.5, 2)) / (1 + Math.pow(2.5, 2)));
        AffineTransform tx = new AffineTransform(formula1, formula2, formula2, formula3,0,0);
        tx.translate(position.getX(), position.getY());
        tx.rotate(50);

        graphics.draw(tx.createTransformedShape(rectangle2D));
        graphics.draw(rectangle2D);


    }


    public static void main(String[] args) {
        launch(Mirror.class);
    }

}
