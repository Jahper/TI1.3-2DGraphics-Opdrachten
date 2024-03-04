
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.dyn4j.collision.Fixture;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.Mass;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class AngryBirds extends Application {

    private ResizableCanvas canvas;
    private World world;
    private MousePicker mousePicker;
    private Camera camera;
    private boolean debugSelected = false;
    private ArrayList<GameObject> gameObjects = new ArrayList<>();

    @Override
    public void start(Stage stage) throws Exception {

        BorderPane mainPane = new BorderPane();

        // Add debug button
        javafx.scene.control.CheckBox showDebug = new CheckBox("Show debug");
        debugSelected = true;
        showDebug.setOnAction(e -> {
            debugSelected = showDebug.isSelected();
        });
        mainPane.setTop(showDebug);

        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());

        camera = new Camera(canvas, g -> draw(g), g2d);
        mousePicker = new MousePicker(canvas);

        new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now) {
                if (last == -1) {
                    last = now;
                }
                update((now - last) / 1000000000.0);
                last = now;
                draw(g2d);
            }
        }.start();

        stage.setScene(new Scene(mainPane, 1920, 1080));
        stage.setTitle("Angry Birds");
        stage.show();
        draw(g2d);
    }

    public void init() {
        world = new World();
        world.setGravity(new Vector2(0, -9.8));

        //birb
        Body red = new Body();
        BodyFixture fixture = new BodyFixture(Geometry.createCircle(1));
        fixture.setRestitution(0.3);
        red.addFixture(fixture);
        red.setGravityScale(1);
        red.setMass(MassType.NORMAL);

        GameObject redObject = new GameObject("angry-bird-red-image-angry-birds-transparent-png-1637889.png", red, new Vector2(0, 0), 1);

        gameObjects.add(redObject);

        world.addBody(red);

        //block

        Body block = new Body();
        BodyFixture blockFix = new BodyFixture(Geometry.createRectangle(3.5,3.5));
        blockFix.setFriction(0.8);
        block.addFixture(blockFix);
        block.setGravityScale(1);
        block.setMass(MassType.NORMAL);
        block.translate(new Vector2(5, 0));

        GameObject o = new GameObject("321024-removebg-preview.png", block, new Vector2(0, 0), 0.8);

        gameObjects.add(o);

        world.addBody(block);

        //test platform

        Body platform = new Body();
        BodyFixture platformFixture = new BodyFixture(Geometry.createRectangle(100, 1));
        platform.addFixture(platformFixture);
        platform.setMass(MassType.INFINITE);
        platform.translate(new Vector2(0, -24));

        world.addBody(platform);
        //borders
        Body border1 = new Body();
        BodyFixture bf1 = new BodyFixture(Geometry.createRectangle(1, 1000));
        border1.addFixture(bf1);
        border1.setMass(MassType.INFINITE);
        border1.translate(new Vector2(50, -24));

        world.addBody(border1);

        Body border2 = new Body();
        BodyFixture bf2 = new BodyFixture(Geometry.createRectangle(1, 1000));
        border2.addFixture(bf2);
        border2.setMass(MassType.INFINITE);
        border2.translate(new Vector2(-50, -24));

        world.addBody(border2);

    }

    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        AffineTransform originalTransform = graphics.getTransform();

        graphics.setTransform(camera.getTransform((int) canvas.getWidth(), (int) canvas.getHeight()));
        graphics.scale(1, -1);

        for (GameObject go : gameObjects) {
            go.draw(graphics);
        }

        if (debugSelected) {
            graphics.setColor(Color.blue);
            DebugDraw.draw(graphics, world, 100);
        }

        graphics.setTransform(originalTransform);
    }

    public void update(double deltaTime) {
        mousePicker.update(world, camera.getTransform((int) canvas.getWidth(), (int) canvas.getHeight()), 100);
        world.update(deltaTime);
    }

    public static void main(String[] args) {
        launch(AngryBirds.class);
    }

}
