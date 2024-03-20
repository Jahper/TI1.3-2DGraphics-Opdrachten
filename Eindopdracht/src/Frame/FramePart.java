package Frame;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;

import java.util.ArrayList;

public abstract class FramePart {
    public World world;

    public ArrayList<GameObject> objects = new ArrayList<>();
    public ArrayList<Body> scoreBodies = new ArrayList<>();

    public FramePart(World world) {
        this.world = world;
    }

    public Body createBodyAndGameObject(double width, double height, Vector2 vector2, String imageFile, Vector2 offset, double scale) {
        Body body = new Body();
        BodyFixture bodyFixture = new BodyFixture(Geometry.createRectangle(width, height));
        bodyFixture.setFriction(0.3);
        bodyFixture.setRestitution(0.6);
        body.addFixture(bodyFixture);
        body.setMass(MassType.INFINITE);
        body.translate(vector2);
        world.addBody(body);
        objects.add(new GameObject(imageFile, body, offset, scale));
        return body;
    }

    public Body createBodyAndGameObjectBouncy(double width, double height, Vector2 vector2, String imageFile, Vector2 offset, double scale) {
        Body body = new Body();
        BodyFixture bodyFixture = new BodyFixture(Geometry.createRectangle(width, height));
        bodyFixture.setFriction(0.3);
        bodyFixture.setRestitution(10);
        body.addFixture(bodyFixture);
        body.setMass(MassType.INFINITE);
        body.translate(vector2);
        world.addBody(body);
        objects.add(new GameObject(imageFile, body, offset, scale));
        scoreBodies.add(body);
        return body;
    }

    public ArrayList<GameObject> getObjects() {
        return objects;
    }
}
