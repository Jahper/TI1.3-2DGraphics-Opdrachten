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

        float[] fractions = {0.0f, 0.1f, 0.2f, 0.3f, 0.4f, 0.5f, 0.6f, 0.7f, 0.8f, 0.9f, 1.0f};
        Color[] colors = new Color[fractions.length];
        for (int i = 0; i < colors.length; i++)
            colors[i] = Color.getHSBColor(fractions[i], 1.0f, 1.0f);
        graphics.setPaint(new RadialGradientPaint(position, Math.min(1920, 1080), fractions, colors));
        graphics.fill(new Rectangle2D.Double(0,0,1920, 1080));

    }
    public static void main(String[] args)
    {
        launch(GradientPaintExercise.class);
    }

}
