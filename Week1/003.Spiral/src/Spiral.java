import java.awt.geom.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

public class Spiral extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Canvas canvas = new Canvas(1920, 1080);
        canvas.getGraphicsContext2D().translate(canvas.getWidth()/2, canvas.getHeight()/2);
        canvas.getGraphicsContext2D().scale(1, -1);
        canvas.getGraphicsContext2D().rotate(90);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        primaryStage.setScene(new Scene(new Group(canvas)));
        primaryStage.setTitle("Spiral");
        primaryStage.show();
    }
    
    
    public void draw(FXGraphics2D graphics) {
        double n = 2;
        double phi;
        double lastX = 0;
        double lastY = 0;
        for (double i = 0; i < 1000; i+= 0.01) {
            phi = n * i;
            double x = n * phi * Math.cos(phi);
            double y = n * phi * Math.sin(phi);
            graphics.draw(new Line2D.Double(lastX, lastY, x, y));
            lastX = x;
            lastY = y;


        }  
        
        
    }
    
    
    
    public static void main(String[] args) {
        launch(Spiral.class);
    }

}
