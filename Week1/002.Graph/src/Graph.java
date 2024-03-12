import java.awt.*;
import java.awt.geom.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

//duurt lang om te laden, met een klein beetje geduld doet ie het prima!

public class Graph extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Canvas canvas = new Canvas(1920, 1080);
        canvas.getGraphicsContext2D().translate(canvas.getWidth()/2, canvas.getHeight()/2);
        canvas.getGraphicsContext2D().scale(1, -1);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        primaryStage.setScene(new Scene(new Group(canvas)));
        primaryStage.setTitle("Graph");
        primaryStage.show();
    }
    
    
    public void draw(FXGraphics2D graphics) {
        graphics.setColor(Color.red);
        graphics.drawLine(0,0,0,1000);
        graphics.setColor(Color.green);
        graphics.drawLine(0,0,1000,0);
        graphics.setColor(Color.black);
        double lastY = (int) Math.pow(0, 3);
        float resolution = 0.0001f;
        double scale = 50;
        for (float i = -1080; i < 1080; i+= resolution) {
            double y = Math.pow(i, 3);
            graphics.draw(new Line2D.Double((i * scale), (lastY * scale), (i * scale), (y * scale)));
            lastY = y;
        }
    }
    
    
    
    public static void main(String[] args) {
        launch(Graph.class);
    }

}
