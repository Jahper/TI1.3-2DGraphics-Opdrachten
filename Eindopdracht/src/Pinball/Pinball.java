package Pinball;

import Util.*;
import Frame.*;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.dyn4j.geometry.MassType;
import org.jfree.fx.ResizableCanvas;

import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Vector2;
import org.jfree.fx.FXGraphics2D;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
    private GameOverPopUp gameOverPopUp;
    private HighScorePopUp highScorePopUp;
    private Font font;
    private boolean gameOver = false;
    private BufferedImage background;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primarysStage = primaryStage;

        BorderPane mainPane = new BorderPane();

        world.setGravity(new Vector2(0, 9.8));

        canvas = new ResizableCanvas(g -> draw(g), mainPane);

        mainPane.setCenter(canvas);

        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());

        camera = new Camera(canvas, g -> draw(g), g2d);

        camera.setZoom(10);

        mousePicker = new MousePicker(canvas);

        highScoreWriter = new HighScoreWriter();

        gameOverPopUp = new GameOverPopUp();

        highScorePopUp = new HighScorePopUp(this);

        canvas.setOnMousePressed(event -> {
            if (!gameOver) {
                if (event.isShiftDown() && event.getButton() == MouseButton.MIDDLE) {
                    debugOn = !debugOn;
                } else if (event.getButton() == MouseButton.PRIMARY) {
                    pinballFrame.flipLeft();
                } else if (event.getButton() == MouseButton.SECONDARY) {
                    pinballFrame.flipRight();
                }
            } else {
                if (!highScorePopUp.getPopup(score).isShowing()) {
                    gameOverPopUp.getPopup().hide();
                    resetLivesAndScore();
                }
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
        if (gameOver) {
            return;
        }
        mousePicker.update(world, camera.getTransform((int) canvas.getWidth(), (int) canvas.getHeight()), 1, this.launcher);
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

        AffineTransform tx = new AffineTransform();
        tx.translate(-canvas.getWidth() / 17, -canvas.getHeight() / 13);
        tx.scale(0.21, 0.21);

        g.drawImage(background, tx, null);

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

        g.setFont(font.deriveFont(10f));
        g.drawString("Score:", 53, -33);
        g.drawString(score + "", 53, -20);
    }

    private void drawLives(FXGraphics2D g) {
        g.drawString("Lives:", -90, -33);
        g.drawString(lives + "", -90, -20);
    }

    private void drawHighScores(FXGraphics2D g) {
        g.setFont(font.deriveFont(7f));
        g.drawString("Top Scores", 53, -7);

        g.setFont(font.deriveFont(5f));
        List<HighScore> highScoreList = highScoreWriter.getHighScores();
        for (int i = 0; i < highScoreList.size(); i++) {
            g.drawString(highScoreList.get(i).toString(), 53, i * 6);
        }
    }

    private void checkBall() {
        if (ball.getBall().getTransform().getTranslationY() > 60) {
            lives--;
            ball.resetBall();
        }
        if (lives < 1) {
            gameOver();
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
        //font
        this.font = getFont(10);
        //background
        try {
            background = ImageIO.read(getClass().getResource("MarioBG2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void gameOver() {
        ball.setMassType(MassType.INFINITE);
        gameOver = true;

        if (!highScoreWriter.checkForNewHighScore(score)) {
            gameOverPopUp.getPopup().show(primarysStage);
            return;
        }

        Popup popup = highScorePopUp.getPopup(score);
        popup.show(primarysStage);
    }

    public Font getFont(float size) {
        try {
            InputStream input = new BufferedInputStream(new FileInputStream("Eindopdracht/src/Frame/MiscFiles/unispace bd.ttf"));
            Font font = Font.createFont(Font.TRUETYPE_FONT, input).deriveFont(size);
            return font;
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void resetLivesAndScore() {
        this.lives = 3;
        this.score = 0;
        this.gameOver = false;
        this.ball.setMassType(MassType.NORMAL);
    }

    public HighScoreWriter getHighScoreWriter() {
        return highScoreWriter;
    }

    public Ball getBall() {
        return ball;
    }
}
