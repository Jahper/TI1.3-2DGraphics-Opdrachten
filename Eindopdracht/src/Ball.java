import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;

public class Ball extends FramePart {
    private Body ball;
    private Vector2 startPos;
    public Ball(World world) {
        super(world);
        createBall();
    }

    private void createBall() {
        this.ball = new Body();
        BodyFixture ballFixture = new BodyFixture(Geometry.createCircle(0.5));
//        ballFixture.setFriction(0.3);
        ballFixture.setRestitution(0.2);
        ballFixture.setDensity(0.001);
        ball.addFixture(ballFixture);
        ball.setMass(MassType.NORMAL);
        ball.setGravityScale(10);
        startPos = new Vector2(44.5, 18);
        ball.translate(startPos);
        world.addBody(ball);
        objects.add(new GameObject("angry-bird-red-image-angry-birds-transparent-png-1637889.png", ball, new Vector2(0, 0), 0.01));
    }
    public void resetBall() {
        ball.translateToOrigin();
        ball.translate(startPos);
        ball.setAsleep(true);
        ball.setAsleep(false);
    }

    public void update(double deltaTime) {
        if (ball.getTransform().getTranslationY() > 60) {
            resetBall();
        }
        //houdt ball in het midden
        if (ball.getTransform().getTranslationY() > 15 && ball.getTransform().getTranslationX() > 42.5) {
            double y = ball.getTransform().getTranslationY();
            ball.translateToOrigin();
            ball.translate(new Vector2(44.5, y));
        }
    }

    public Body getBall() {
        return ball;
    }
}
