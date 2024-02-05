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

import javax.swing.text.Position;

public class GradientPaintExercise extends Application {
    private ResizableCanvas canvas;
    private Point2D position = new Point2D.Double(100,100);


    @Override
    public void start(Stage primaryStage) throws Exception
    {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);

        canvas.setOnMouseDragged(event ->
        {
            position = new Point2D.Double(event.getX(), event.getY());
            draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        });

        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("GradientPaint");
        primaryStage.show();
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }


    public void draw(FXGraphics2D graphics)
    {
        graphics.setBackground(Color.white);
        graphics.clearRect(0,0,1920,1080);

//        graphics.setTransform(new AffineTransform());
//        graphics.setBackground(Color.white);
//        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
//        Color[] colors = {Color.yellow, Color.blue, Color.red};
//        float[] fractions = { 0.0f, 0.1f, 0.2f};
        float[] fractions = {0.0f, 0.1f, 0.2f, 0.3f, 0.4f, 0.5f, 0.6f, 0.7f, 0.8f, 0.9f, 1.0f};
        Color[] colors = new Color[fractions.length];
        for (int i = 0; i < colors.length; i++)
            colors[i] = Color.getHSBColor(fractions[i], 1.0f, 1.0f);
//        position = new Point2D.Double(event.getX(), event.getY());
        graphics.setPaint(new RadialGradientPaint(position, Math.min(1920, 1080), fractions, colors));

//        canvas.setOnMouseDragged(event ->
//        {
//
//        });
//        Shape rectangle = new Rectangle2D.Double(0,0,1920,1080);
        graphics.fill(new Rectangle2D.Double(0,0,1920, 1080));

    }
//public void draw(FXGraphics2D g2d)
//{
//    g2d.setBackground(Color.white);
//    g2d.clearRect(0,0,1920,1080);
//
//    canvas.setOnMouseDragged(event -> position = new Point2D.Double(event.getX(), event.getY()));
//
//    float[] fractions = { 0.0f, 0.1f, 0.2f, 0.3f, 0.4f, 0.5f, 0.6f, 0.7f, 0.8f, 0.9f, 1.0f};
//    Color[] colors = new Color[fractions.length];
//    for(int i = 0; i < colors.length; i++)
//        colors[i] = Color.getHSBColor(fractions[i], 1.0f, 1.0f);
//
//    g2d.setPaint(new RadialGradientPaint(
//            1920/2, 1080/2,	//center
//            Math.min(1920, 1080), //radius
////            (float)position.getX(), (float)position.getY(), //focal point
//            fractions, colors, MultipleGradientPaint.CycleMethod.REPEAT));
//
//    g2d.fill(new Rectangle2D.Double(0,0,1920, 1080));
//
//}


    public static void main(String[] args)
    {
        launch(GradientPaintExercise.class);
    }

}
