import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;

import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class BlockDrag extends Application {
    ResizableCanvas canvas;
    ArrayList<Renderable> renderables = new ArrayList<>();
    FXGraphics2D graphics;
    boolean dragging = false;
    double xDiff;
    double yDiff;

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("Block Dragging");
        primaryStage.show();

        canvas.setOnMousePressed(e -> mousePressed(e));
        canvas.setOnMouseReleased(e -> mouseReleased(e));
        canvas.setOnMouseDragged(e -> mouseDragged(e));

        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }


    public void draw(FXGraphics2D graphics) {
        this.graphics = graphics;
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        graphics.clearRect(0, 0, 1920, 1080);

        for (Renderable renderable : renderables) {
            graphics.setColor(renderable.getColor());
            graphics.fill(renderable.getShape());
            graphics.draw(renderable.getShape());
        }
    }


    public static void main(String[] args) {
        launch(BlockDrag.class);
    }

    private void mousePressed(MouseEvent e) {

        for (int i = 0; i < renderables.size(); i++) {

            Renderable renderable = renderables.get(i);

            Color color = renderable.getColor();
            graphics.setColor(color);
            if (e.getY() >= renderable.getPosition().getY() && e.getY() <= renderable.getPosition().getY() + 50
                    && e.getX() >= renderable.getPosition().getX() && e.getX() <= renderable.getPosition().getX() + 50) {
                xDiff = e.getX() - renderable.getPosition().getX();
                yDiff = e.getY() - renderable.getPosition().getY();
                dragging = true;
            }
        }
    }

    private void mouseReleased(MouseEvent e) {
        dragging = false;
    }

    private void mouseDragged(MouseEvent e) {

        for (int i = 0; i < renderables.size(); i++) {
            Renderable renderable = renderables.get(i);
            Color color = renderable.getColor();
            graphics.setColor(color);
            if (e.getY() >= renderable.getPosition().getY() && e.getY() <= renderable.getPosition().getY() + 50
                    && e.getX() >= renderable.getPosition().getX() && e.getX() <= renderable.getPosition().getX() + 50 && !dragging) {
                dragging = true;
                System.out.println(e.getX() + " : " + xDiff + "    :    " + e.getY()+ " : " + yDiff);
                double x = e.getX() - xDiff;
                double y = e.getY() - yDiff;
                System.out.println(x + " : " + y);
                graphics.clearRect(0, 0, 1920, 1080);
                renderables.set(i, new Renderable(color, new Point2D.Double(x, y)));


            }
            for (Renderable renderable1 : renderables) {
                graphics.setColor(renderable1.getColor());
                graphics.fill(renderable1.getShape());
                graphics.draw(renderable1.getShape());
            }
        }
        dragging = false;
    }

    public void init() {
        Color[] colors = {Color.red, Color.black, Color.blue, Color.yellow, Color.green};
        for (int i = 0; i < 5; i++) {
            double x = i * 50;
            double y = i * 50;
            renderables.add(new Renderable(colors[i], new Point2D.Double(x, 50)));
        }
    }
}
