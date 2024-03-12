import java.awt.*;
import java.awt.geom.*;
import java.util.Random;

import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import org.jfree.fx.FXGraphics2D;

public class Spirograph extends Application {
    private TextField v1;
    private TextField v2;
    private TextField v3;
    private TextField v4;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Canvas canvas = new Canvas(1920, 1080);
        canvas.getGraphicsContext2D().translate(canvas.getWidth() / 2, canvas.getHeight() / 2);
        canvas.getGraphicsContext2D().scale(1, -1);
        canvas.getGraphicsContext2D().restore();
        VBox mainBox = new VBox();
        HBox topBar = new HBox();
        mainBox.getChildren().add(topBar);
        mainBox.getChildren().add(new Group(canvas));

        topBar.getChildren().add(v1 = new TextField("300"));
        topBar.getChildren().add(v2 = new TextField("1"));
        topBar.getChildren().add(v3 = new TextField("300"));
        topBar.getChildren().add(v4 = new TextField("10"));

        Button random = getButton();

        topBar.getChildren().add(random);

        v1.textProperty().addListener(e -> draw(new FXGraphics2D(canvas.getGraphicsContext2D()), canvas));
        v2.textProperty().addListener(e -> draw(new FXGraphics2D(canvas.getGraphicsContext2D()), canvas));
        v3.textProperty().addListener(e -> draw(new FXGraphics2D(canvas.getGraphicsContext2D()), canvas));
        v4.textProperty().addListener(e -> draw(new FXGraphics2D(canvas.getGraphicsContext2D()), canvas));

        draw(new FXGraphics2D(canvas.getGraphicsContext2D()), canvas);
        primaryStage.setScene(new Scene(mainBox));
        primaryStage.setTitle("Spirograph");
        primaryStage.show();
    }

    private Button getButton() {
        Button random = new Button("Random");
        Random randomGeneratorA = new Random();
        Random randomGeneratorB = new Random();
        Random randomGeneratorC = new Random();
        Random randomGeneratorD = new Random();

        random.setOnAction(event -> {
            v1.setText(String.valueOf(randomGeneratorA.nextInt(500)));
            v2.setText(String.valueOf(randomGeneratorB.nextInt(5)));
            v3.setText(String.valueOf(randomGeneratorC.nextInt(500)));
            v4.setText(String.valueOf(randomGeneratorD.nextInt(50)));
        });
        return random;
    }


    public void draw(FXGraphics2D graphics, Canvas canvas) {
        canvas.getGraphicsContext2D().clearRect(-960,-540,1920,1080);
        //you can use Double.parseDouble(v1.getText()) to get a double value from the first textfield
        //feel free to add more textfields or other controls if needed, but beware that swing components might clash in naming
        double a = Double.parseDouble(v1.getText());
        double b = Double.parseDouble(v2.getText());
        double c = Double.parseDouble(v3.getText());
        double d = Double.parseDouble(v4.getText());

        double lastX = a * Math.cos(b * 0) + c * Math.cos(d * 0);
        double lastY = a * Math.sin(b * 0) + c * Math.sin(d * 0);

        for (double i = 0; i < 1000; i+= 0.001) {
            double phi = i * 10;
            double x = a * Math.cos(b * phi) + c * Math.cos(d * phi);
            double y = a * Math.sin(b * phi) + c * Math.sin(d * phi);
            graphics.draw(new Line2D.Double(lastX, lastY, x, y));

            lastX = x;
            lastY = y;
        }
    }


    public static void main(String[] args) {
        launch(Spirograph.class);
    }

}
