import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.ResizableCanvas;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;
import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class Pinball extends Application {
    private ResizableCanvas canvas;
    private Camera camera;
    private MousePicker mousePicker;
    private Body ball;
    private World world = new World();
    private ArrayList<GameObject> gameObjects = new ArrayList<>();
    private boolean debugOn = true;
    private PinballFrame pinballFrame;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane mainPane = new BorderPane();

        world.setGravity(new Vector2(0, 9.8));

        canvas = new ResizableCanvas(g -> draw(g), mainPane);

        RadioButton radioButton = new RadioButton("debug");



        mainPane.setCenter(canvas);
        mainPane.setTop(radioButton);

        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());

        this.camera = new Camera(canvas, g -> draw(g), g2d);

        camera.setZoom(10);

        mousePicker = new MousePicker(canvas);

        ball = createBall();

        radioButton.setOnAction(e -> {
            if (radioButton.isSelected()) {
                debugOn = true;
            } else {
                debugOn = false;
            }
        });



        canvas.setOnMousePressed(event -> {
            System.out.println(event.getButton());
            if (event.getButton() == MouseButton.PRIMARY) {
                pinballFrame.flipLeft();
            } else if (event.getButton() == MouseButton.SECONDARY) {
                pinballFrame.flipRight();
            }
        });
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

        Scene scene = new Scene(mainPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pinball");
        primaryStage.setHeight(1080);
        primaryStage.setWidth(1920);
        primaryStage.show();

        draw(g2d);
    }

    private void update(double deltaTime) {
        mousePicker.update(world, camera.getTransform((int) canvas.getWidth(), (int) canvas.getHeight()), 1);
        world.update(deltaTime);
    }

    private void draw(FXGraphics2D g) {
        g.setTransform(new AffineTransform());
        g.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        g.setTransform(camera.getTransform((int) canvas.getWidth(), (int) canvas.getHeight()));
        g.setBackground(Color.WHITE);
        g.setColor(Color.BLUE);

//        DebugDraw.draw(g, world, 1);


        for (GameObject gameObject : gameObjects) {
            gameObject.draw(g);
        }
        if (debugOn) {
            DebugDrawPinball.draw(g, world, 1);
        }
    }

    public void init() {
        //frame
        this.pinballFrame = new PinballFrame(this.world);
        this.gameObjects.addAll(pinballFrame.getObjects());

        for (Body body : pinballFrame.getBodies()) {
            world.addBody(body);
        }
//        createBall();
    }

    private Body createBall() {
        Body ball = new Body();
        BodyFixture ballFixture = new BodyFixture(Geometry.createCircle(0.5));
        ballFixture.setFriction(0.5);
        ballFixture.setRestitution(0.7);
        ballFixture.setDensity(0.001);
        ball.addFixture(ballFixture);
        ball.setMass(MassType.NORMAL);
        ball.setGravityScale(10);
        ball.translate(new Vector2(0, -10));
        world.addBody(ball);
        gameObjects.add(new GameObject("angry-bird-red-image-angry-birds-transparent-png-1637889.png", ball, new Vector2(0,0), 0.01));
        return ball;
    }
}
