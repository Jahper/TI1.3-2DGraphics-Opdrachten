import java.awt.*;
import java.awt.geom.*;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

public class Rainbow extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Canvas canvas = new Canvas(1920, 1080);
        canvas.getGraphicsContext2D().translate(canvas.getWidth()/2, 1000);
        canvas.getGraphicsContext2D().scale(1, -1);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        primaryStage.setScene(new Scene(new Group(canvas)));
        primaryStage.setTitle("Rainbow");
        primaryStage.show();
    }
    
    
    public void draw(FXGraphics2D graphics) {
        float radiusBinnen = 300;
        float radiusBuiten = 450;
        int steps = 1000;
        for (float i = 0; i < steps; i+= 0.1f) {
            graphics.setColor(Color.getHSBColor(i/steps, 1, 1));
            double cos = Math.cos(i / steps * Math.PI);
            double sin = Math.sin(i / steps * Math.PI);
            double x1 = radiusBinnen * cos;
            double y1 = radiusBinnen * sin;
            double x2 = radiusBuiten * cos;
            double y2 = radiusBuiten * sin;
            graphics.draw(new Line2D.Double(x1, y1, x2, y2));
        }


    }
    
    
    
    public static void main(String[] args) {
        launch(Rainbow.class);
    }

}
