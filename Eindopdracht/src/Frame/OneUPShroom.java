package Frame;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;

public class OneUPShroom extends FramePart {
    private Body shroom;
    public OneUPShroom(World world) {
        super(world);
        createShroom();
    }

    private void createShroom() {
        this.shroom = new Body();
        BodyFixture shroomFix = new BodyFixture(Geometry.createCircle(2.7));
        shroomFix.setRestitution(0);
        shroom.addFixture(shroomFix);
        shroom.setMass(MassType.INFINITE);
        shroom.translate(-45, -34);
        shroom.rotate(0.5 * Math.PI, new Vector2(-45, -34));
        world.addBody(shroom);
        objects.add(new GameObject("FrameImages/1UP.png", shroom, new Vector2(), 0.023));
    }

    public int checkOneUP(Ball ball) {
        if (ball.getBall().isInContact(shroom)) {
            return 500;
        }
        return 0;
    }
}
