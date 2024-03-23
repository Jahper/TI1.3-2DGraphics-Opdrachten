package Pinball;

import Util.*;
import Frame.*;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.dyn4j.geometry.MassType;
import org.jfree.fx.ResizableCanvas;

import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Vector2;
import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.AffineTransform;

import java.util.ArrayList;

public class Pinball extends Application {
    private Stage primarysStage;
    private ResizableCanvas canvas;
    private Camera camera;
    private MousePicker mousePicker;
    private World world = new World();
    private ArrayList<GameObject> gameObjects = new ArrayList<>();
    private boolean debugOn = false;
    private PinballFrame pinballFrame;
    private Launcher launcher;
    private Ball ball;
    private BonusBlocks bonusBlocks;
    private OneUPShroom oneUPShroom;
    private int score = 0;
    private int lives = 3;
    private double oneUPTimer = 100;
    private HighScoreWriter highScoreWriter;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primarysStage = primaryStage;

        BorderPane mainPane = new BorderPane();

        world.setGravity(new Vector2(0, 9.8));

        canvas = new ResizableCanvas(g -> draw(g), mainPane);

        Button reset = new Button("reset");

        HBox topBar = new HBox(reset);

        mainPane.setCenter(canvas);
        mainPane.setTop(topBar);

        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());

        this.camera = new Camera(canvas, g -> draw(g), g2d);

        camera.setZoom(10);

        mousePicker = new MousePicker(canvas);

        this.highScoreWriter = new HighScoreWriter();

        reset.setOnAction(e -> {
//            ball.resetBall();fixme
//            score = 0;
//            lives = 3;
            new HighScorePopUp(this, 100).getPopup().show(primaryStage);

        });


        canvas.setOnMousePressed(event -> {
            if (event.isShiftDown() && event.getButton() == MouseButton.MIDDLE) {
                debugOn = !debugOn;
            } else if (event.getButton() == MouseButton.PRIMARY) {
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
        launcher.update(deltaTime);
        ball.update(deltaTime);
        checkBall();
        score += bonusBlocks.checkScore(this.ball);

        int oneUP = oneUPShroom.checkOneUP(this.ball);
        if (oneUP > 0 && oneUPTimer > 0.5) {
            score += oneUP;
            lives++;
            oneUPTimer = 0;
        }
        oneUPTimer += deltaTime;
        world.update(deltaTime);
    }

    private void draw(FXGraphics2D g) {
        g.setTransform(new AffineTransform());
        g.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        g.setTransform(camera.getTransform((int) canvas.getWidth(), (int) canvas.getHeight()));
        g.setBackground(Color.WHITE);
        g.setColor(Color.BLUE);

        for (GameObject gameObject : gameObjects) {
            gameObject.draw(g);
        }
        if (debugOn) {
            DebugDrawPinball.draw(g, world, 1);
        }

        drawScore(g);
        drawLives(g);
        drawHighScores(g);
    }

    private void drawScore(FXGraphics2D g) {
        g.setColor(Color.BLACK);
        g.drawString("Score:", 53, -33);
        String scoreString = score + "";

        if (scoreString.length() > 5) {
            scoreString = "Rlly?";
        }

        g.drawString(scoreString, 53, -20);
    }

    private void drawLives(FXGraphics2D g) {
        g.drawString("Lives:", -90, -33);
        g.drawString(lives + "", -90, -20);
    }

    private void drawHighScores(FXGraphics2D g) {
        g.drawString("Highscores:", 53, -7);
    }

    private void checkBall() {
        if (ball.getBall().getTransform().getTranslationY() > 60) {
            lives--;
            ball.resetBall();
        }
        if (lives < 1) {
            //todo game over
            gameOver();
            lives = 3;
            score = 0;
            highScoreWriter.printScores();
        }
    }

    public void init() {
        //frame
        this.pinballFrame = new PinballFrame(this.world);
        this.gameObjects.addAll(pinballFrame.getObjects());
        //launcher
        this.launcher = new Launcher(world);
        this.gameObjects.addAll(launcher.getObjects());
        //ball
        this.ball = new Ball(world);
        this.gameObjects.addAll(ball.getObjects());
        //bonusblocks
        this.bonusBlocks = new BonusBlocks(world);
        this.gameObjects.addAll(bonusBlocks.getObjects());
        //1-up shroom
        this.oneUPShroom = new OneUPShroom(world);
        this.gameObjects.addAll(oneUPShroom.getObjects());
    }

    private void gameOver() {
        if (!highScoreWriter.checkForNewHighScore(score)) {
            return;
        }

        ball.setMassType(MassType.INFINITE);
        HighScorePopUp highScorePopUp = new HighScorePopUp(this, score);
        Popup popup = highScorePopUp.getPopup();
        popup.show(primarysStage);
    }

    public HighScoreWriter getHighScoreWriter() {
        return highScoreWriter;
    }

    public Ball getBall() {
        return ball;
    }
}
