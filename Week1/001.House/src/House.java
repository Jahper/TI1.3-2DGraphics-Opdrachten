import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

public class House extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Canvas canvas = new Canvas(1024, 768);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()),512 ,50 ,112 , 300);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()),512 ,50 ,912 , 300);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()),112 ,300 ,112 , 750);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()),912 ,300 ,912 , 750);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()),112 ,750 ,912 , 750);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()),200 ,750 ,200 , 550);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()),300 ,750 ,300 , 550);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()),200 ,550 ,300 , 550);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()),700 ,650 ,400 , 650);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()),700 ,500 ,400 , 500);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()),700 ,500 ,700 , 650);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()),400 ,500 ,400 , 650);


        primaryStage.setScene(new Scene(new Group(canvas)));
        primaryStage.setTitle("House");
        primaryStage.show();
    }


    public void draw(FXGraphics2D graphics, int x1, int y1, int x2, int y2) {
        graphics.drawLine(x1, y1, x2, y2);
    }


    public static void main(String[] args) {
        launch(House.class);
    }

}
