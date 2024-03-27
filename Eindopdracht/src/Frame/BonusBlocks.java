package Frame;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Vector2;

public class BonusBlocks extends FramePart {
    public BonusBlocks(World world) {
        //todo blokken toevoegen om inf. bounce te voorkomen
        super(world);
        createBlocks();
    }

    private void createBlocks() {
        //middle
        createBodyAndGameObjectBouncy(5,5, new Vector2(-10, 0), "FrameImages/PinBallMysteryBox.png", new Vector2(), 0.01).rotate(0.25 * Math.PI, new Vector2(-10, 0));
        createBodyAndGameObjectBouncy(5,5, new Vector2(10, 0), "FrameImages/PinBallMysteryBox.png", new Vector2(), 0.01).rotate(-0.25 * Math.PI, new Vector2(10, 0));
        //left
        createBodyAndGameObjectBouncy(5,5, new Vector2(-20, -3), "FrameImages/PinBallMysteryBox.png", new Vector2(), 0.01);
        createBodyAndGameObjectBouncy(5,5, new Vector2(-20, -42), "FrameImages/PinBallMysteryBox.png", new Vector2(), 0.01).rotate(0.25 * Math.PI, new Vector2(-20, -42));
        //right
        createBodyAndGameObjectBouncy(5,5, new Vector2(20, -3), "FrameImages/PinBallMysteryBox.png", new Vector2(), 0.01);
        createBodyAndGameObjectBouncy(5,5, new Vector2(20, -42), "FrameImages/PinBallMysteryBox.png", new Vector2(), 0.01).rotate(-0.25 * Math.PI, new Vector2(20, -42));
        //todo side
    }

    public int checkScore(Ball ball) {
        for (Body scoreBody : scoreBodies) {
            if (ball.getBall().isInContact(scoreBody)) {
                return 10;
            }
        }
        return 0;
    }
}
