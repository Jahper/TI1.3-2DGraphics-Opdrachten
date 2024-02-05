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

public class Colors extends Application {
    private ResizableCanvas canvas;

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("Colors");
        primaryStage.show();
    }


    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        Color[] colors = {Color.BLACK, Color.BLUE, Color.CYAN, Color.darkGray, Color.gray, Color.green, Color.lightGray, Color.magenta,
                Color.orange, Color.pink, Color.red, Color.white, Color.yellow};

        int baseX = 50;
        int baseY = 50;


        for (int i = 0; i < 13; i++) {
            graphics.setColor(colors[i]);

            Shape s = new Rectangle2D.Double(baseX, baseY, 20,20);
            graphics.fill(s);
            baseX += 20;
        }
    }


    public static void main(String[] args) {
        launch(Colors.class);
    }

}
