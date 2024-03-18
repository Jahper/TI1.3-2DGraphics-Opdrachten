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

    private Body createBall() {
        this.ball = new Body();
        BodyFixture ballFixture = new BodyFixture(Geometry.createCircle(0.5));
//        ballFixture.setFriction(0.3);
        ballFixture.setRestitution(0.2);
        ballFixture.setDensity(0.001);
        ball.addFixture(ballFixture);
        ball.setMass(MassType.NORMAL);
        ball.setGravityScale(10);
        startPos = new Vector2(44.5, 18);
//        ball.translate(startPos);
        resetBall();
        resetBall();
        world.addBody(ball);
        objects.add(new GameObject("angry-bird-red-image-angry-birds-transparent-png-1637889.png", ball, new Vector2(0, 0), 0.01));
        return ball;
    }
    //todo ball reset niet goed
    public void resetBall() {
        ball.translate(new Vector2());
        ball.translate(startPos);
    }

    public void update(double deltaTime) {
        System.out.println(ball.getTransform().getTranslationY());

        if (ball.getTransform().getTranslationY() > 60) {
            System.out.println("dddddddd");
//            resetBall();
        }
    }
}
