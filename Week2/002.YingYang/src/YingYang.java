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

public class YingYang extends Application {
    private ResizableCanvas canvas;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("Ying Yang");
        primaryStage.show();
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }


    public void draw(FXGraphics2D graphics)
    {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        Area mainCircle = new Area(new Ellipse2D.Double(200,150,200,200));
        graphics.fill(mainCircle);
        graphics.setColor(Color.WHITE);
        Area halfCircle = new Area(new Arc2D.Double(200,150,200,200,90,180,Arc2D.PIE));
        graphics.fill(halfCircle);
        Area mainArea = new Area(mainCircle);
        mainArea.add(halfCircle);

        graphics.setColor(Color.WHITE);

        Area jing = new Area(new Ellipse2D.Double(250,150,100,100));
        graphics.fill(jing);
        Area a1 = new Area(mainArea);
        graphics.setColor(Color.BLACK);
        Area jang = new Area(new Ellipse2D.Double(250,250,100,100));
        graphics.fill(jang);
        Area a2 = new Area(a1);
        a2.add(jang);
        graphics.setColor(Color.WHITE);
        Area whiteBall = new Area(new Ellipse2D.Double(300,280,25,25));
        graphics.fill(whiteBall);
        a2.add(whiteBall);
        graphics.setColor(Color.BLACK);
        Area blackBall = new Area(new Ellipse2D.Double(300,180,25,25));
        graphics.fill(blackBall);
        a2.add(blackBall);
        graphics.draw(a2);
    }


    public static void main(String[] args)
    {
        launch(YingYang.class);
    }

}
