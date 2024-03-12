import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;

import java.util.ArrayList;

public class PinballFrame {
    private ArrayList<GameObject> objects = new ArrayList<>();
    private ArrayList<Body> bodies = new ArrayList<>();

    public PinballFrame() {
        createFrame();

        //todo frame maken
    }

    private void createFrame() {
        //upper border
        bodies.add(getBorderBody(800, 0.1, new Vector2(0, -500)));

        //side borders
        //left
        bodies.add(getBorderBody(0.1, 1000, new Vector2(-400, 0)));
        //right
        bodies.add(getBorderBody(0.1, 1000, new Vector2(400, 0)));

        //bottom borders
        //left
        Body leftBottomBody = getBorderBody(250, 0.1, new Vector2(-285, 300));
        leftBottomBody.rotate(0.4, new Vector2(-275, 300));
        bodies.add(leftBottomBody);
        //right
        Body rightBottomBody = getBorderBody(250, 0.1, new Vector2(285, 300));
        rightBottomBody.rotate(-0.4, new Vector2(275, 300));
        bodies.add(rightBottomBody);

    }
    //method for returning a body for the borders of the pinball game
    private Body getBorderBody(double width, double height, Vector2 vector2) {
        Body body = new Body();
        BodyFixture bodyFixture = new BodyFixture(Geometry.createRectangle(width, height));
        body.addFixture(bodyFixture);
        body.setMass(MassType.INFINITE);
        body.translate(vector2);
        return body;
    }

    public ArrayList<GameObject> getObjects() {
        return objects;
    }

    public ArrayList<Body> getBodies() {
        return bodies;
    }
}
