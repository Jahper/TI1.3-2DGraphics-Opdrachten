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
    }

    private void createFrame() {
        //upper border
        bodies.add(getBorderBody(900, 0.1, new Vector2(50, -500)));

        //side borders
        //left
        bodies.add(getBorderBody(0.1, 1600, new Vector2(-400, 300)));
//        bodies.add(getBorderBody(0.1, 450, new Vector2(-400, -275)));
        //right
        bodies.add(getBorderBody(0.1, 500, new Vector2(400, 300)));
//        bodies.add(getBorderBody(0.1, 450, new Vector2(400, -275)));

        //side funnels
        //right
        bodies.add(getBorderBody(0.1, 1600, new Vector2(500, 300)));
//        bodies.add(getBorderBody(0.1, 450, new Vector2(-400, -275)));
        //right


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
