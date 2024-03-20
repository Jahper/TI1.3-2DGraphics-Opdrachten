import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.World;
import org.dyn4j.dynamics.joint.PrismaticJoint;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;

public class Launcher extends FramePart {
    private PrismaticJoint joint;

    public Launcher(World world) {
        super(world);
        createLauncher();
    }

    private void createLauncher() {
        //bottom part
        Body bottom = createBodyAndGameObject(4, 4, new Vector2(45, 49), "FrameImages/bottomBrick.png", new Vector2(30, -20), 0.01145);
        Body launchPad = new Body();
        BodyFixture launchPadFix = new BodyFixture(Geometry.createRectangle(4, 4));
        launchPadFix.setDensity(1000);
        launchPadFix.setFriction(10000);
        launchPad.addFixture(launchPadFix);
        launchPad.setMass(MassType.NORMAL);
        launchPad.setGravityScale(0);
        launchPad.translate(new Vector2(44.5, 20));
        world.addBody(launchPad);
        objects.add(new GameObject("FrameImages/spring.png", launchPad, new Vector2(), 0.02));

        joint = new PrismaticJoint(launchPad, bottom, new Vector2(45, 10), new Vector2(0, 45));
        joint.setLimitEnabled(true);
        joint.setMotorEnabled(false);
        joint.setLimits(0, 20);
        joint.setMaximumMotorForce(10000000);
        world.addJoint(joint);
    }

    public void update(double deltaTime) {
        if (joint.getJointTranslation() > -0.1) {
            joint.setMotorEnabled(false);
            joint.setMotorSpeed(0);
        } else {
            joint.setMotorEnabled(true);
            joint.setMotorSpeed(-100000);
        }
    }
}
