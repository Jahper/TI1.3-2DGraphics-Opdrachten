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

public class Rainbow extends Application {
    private ResizableCanvas canvas;
    private double angle = Math.PI * 1.45;
    private  double letterAngle = 2.35;

    @Override
    public void start(Stage stage) throws Exception
    {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        stage.setScene(new Scene(mainPane));
        stage.setTitle("Rainbow");
        stage.show();
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }


    public void draw(FXGraphics2D graphics)
    {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        graphics.translate(canvas.getWidth()/2, canvas.getHeight()/2);

        String regenboog = "regenboog";
        Font font = new Font("Arial", Font.PLAIN, 60);

        Color[] color = {Color.red, Color.PINK, Color.orange, Color.yellow, Color.green, Color.magenta, Color.blue, Color.cyan, Color. RED};

        for (int i = 0; i < regenboog.length(); i++) {
            String letter = regenboog.substring(i,i + 1);
            Shape shape = font.createGlyphVector(graphics.getFontRenderContext(), letter).getOutline();
            angle += 0.4;
            AffineTransform tx = new AffineTransform();

            tx.rotate(angle);

            tx.translate(250,250);

            tx.rotate(letterAngle);
//            graphics.setColor(color[i]);
//            graphics.fill(shape);
            graphics.draw(tx.createTransformedShape(shape));
        }



//        Shape shape = font.createGlyphVector(graphics.getFontRenderContext(), "regenboog").getOutline();
//
//        graphics.draw(AffineTransform.getTranslateInstance(250,250).createTransformedShape(shape));
    }


    public static void main(String[] args)
    {
        launch(Rainbow.class);
    }

}
