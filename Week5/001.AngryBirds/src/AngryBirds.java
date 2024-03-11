import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import javax.imageio.ImageIO;

public class AngryBirds extends Application {

    private ResizableCanvas canvas;
    private World world;
    private MousePicker mousePicker;
    private Camera camera;
    private boolean debugSelected = false;
    private ArrayList<GameObject> gameObjects = new ArrayList<>();
    private Body red;
    private Point2D mousePos = null;
    private Vector2 force = new Vector2();
    private boolean launched = false;
    private BufferedImage backgroundImage;
    private BufferedImage slingshotImage;
    private ArrayList<Level> levels = new ArrayList<>();

    @Override
    public void start(Stage stage) throws Exception {

        BorderPane mainPane = new BorderPane();

        // Add debug button
        javafx.scene.control.CheckBox showDebug = new CheckBox("Show debug");
        showDebug.setOnAction(e -> {
            debugSelected = showDebug.isSelected();
        });
        //restart button
        Button restartButton = new javafx.scene.control.Button("Restart");
        restartButton.setOnAction(e -> addAllLevels());

        mainPane.setTop(new HBox(showDebug, restartButton));

        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());

        camera = new Camera(canvas, g -> draw(g), g2d);
        camera.setZoom(0.19769155847910316);
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

        canvas.setOnMousePressed(e -> {
            mousePos = new Point2D.Double(e.getX(), e.getY());
        });

        canvas.setOnMouseDragged(e -> {
            if (checkIfOnSlingshot()) {
                double speed = 1000;
                double diffX = (mousePos.getX() - e.getX()) * speed;
                double diffY = ((mousePos.getY() - e.getY()) * -1) * speed;

                force = new Vector2(diffX, diffY);
            }
        });

        canvas.setOnMouseReleased(e -> {
            if (!launched) {
//                red.setGravityScale(1);
//                red.setMass(MassType.NORMAL);
                red.applyForce(force);
                launched = true;
            }
        });

        stage.setScene(new Scene(mainPane, 1920, 1080));
        stage.setTitle("Angry Birds");
        stage.show();
        draw(g2d);
    }

    public void init() {
        world = new World();
        world.setGravity(new Vector2(0, -9.8));

        //background image
        try {
            backgroundImage = ImageIO.read(getClass().getResource("ABBackground.jpg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //slingshot image
        try {
            slingshotImage = ImageIO.read(getClass().getResource("pngegg.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //level init
        addAllLevels();
        Level level1 = levels.get(0);
        gameObjects = level1.getGameObjects();

        //birb
        getBirb();

        //borders
        drawBorders();
    }

    private void addAllLevels() {
        levels.clear();
        gameObjects.clear();
        world.removeAllBodies();
        drawBorders();
        launched = false;
        //todo meer levels toevoegen
        levels.add(new Level_1(this.world));

        gameObjects.addAll(levels.get(0).getGameObjects());
        getBirb();
    }

    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);

        //background image
        AffineTransform tx = new AffineTransform();
        tx.translate(-5000, 3000);
        tx.scale(4, -4);

        //slingshot
        AffineTransform txSling = new AffineTransform();
        txSling.translate(-3800, -950);
        txSling.scale(2, -2);


        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        AffineTransform originalTransform = graphics.getTransform();

        graphics.setTransform(camera.getTransform((int) canvas.getWidth(), (int) canvas.getHeight()));
        graphics.scale(1, -1);

        //misc images
        graphics.drawImage(backgroundImage, tx, null);
        graphics.drawImage(slingshotImage, txSling, null);

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

    private void drawBorders(){
        //borders
        //bottom platform

        Body platform = new Body();
        BodyFixture platformFixture = new BodyFixture(Geometry.createRectangle(100, 1));
        platform.addFixture(platformFixture);
        platform.setMass(MassType.INFINITE);
        platform.translate(new Vector2(0, -21));

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
    private void getBirb(){
        //birb
        Body red = new Body();
        BodyFixture fixture = new BodyFixture(Geometry.createCircle(1));
        fixture.setRestitution(0.2);
        fixture.setDensity(20);
        red.addFixture(fixture);
//        red.setMass(MassType.INFINITE);
        red.setMass(MassType.NORMAL);
        red.setGravityScale(0);
        red.translate(new Vector2(-36, -11.5));


        GameObject redObject = new GameObject("angry-bird-red-image-angry-birds-transparent-png-1637889.png", red, new Vector2(0, 0), 1);

        gameObjects.add(redObject);
        this.red = red;
        world.addBody(this.red);
    }

    public static void main(String[] args) {
        launch(AngryBirds.class);
    }

    private boolean checkIfOnSlingshot() {
        double scaleX = canvas.getWidth() / 1920;
        double scaleY = canvas.getHeight() / 1080;
        return mousePos.getX() >= 200 * scaleX && mousePos.getX() <= 275 * scaleX
                && mousePos.getY() >= 715 * scaleY && mousePos.getY() <= 790 * scaleY;
    }
}
