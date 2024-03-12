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
        createBodyAndGameObject(900, 10, new Vector2(50, -500), "pipeSectionPinball.png", new Vector2(0,0), 0.1);

        //side borders
        //left

        //bottom
        createBodyAndGameObject(10, 300, new Vector2(-400, 398), "pipeSectionPinball.png", new Vector2(0,0), 0.1);
        //middle
        createBodyAndGameObject(10, 400, new Vector2(-400, -100), "pipeSectionPinball.png", new Vector2(0,0), 0.1);
        //top
        createBodyAndGameObject(10, 100, new Vector2(-400, -455), "pipeSectionPinball.png", new Vector2(0,0), 0.1);

        //right
        createBodyAndGameObject(10, 500, new Vector2(400, 300), "pipeSectionPinball.png", new Vector2(0,0), 0.1);
//        bodies.add(getBorderBody(0.1, 450, new Vector2(400, -275)));

        //side funnels
        //left
        createBodyAndGameObject(10, 1600, new Vector2(-490, 295), "pipeSectionPinball.png", new Vector2(0,0), 0.1);
        createBodyAndGameObject(90, 10, new Vector2(-450, -500), "pipeSectionPinball.png", new Vector2(0,0), 0.1);
//
        //right
        createBodyAndGameObject(10, 1600, new Vector2(500, 295), "pipeSectionPinball.png", new Vector2(0,0), 0.1);
//        bodies.add(getBorderBody(0.1, 450, new Vector2(-400, -275)));


        //bottom borders
        //left part 1
        Body leftBottomBody = createBodyAndGameObject(250, 28, new Vector2(-285, 300), "sidePipePinballLeft.png", new Vector2(0,25), 0.092);
        leftBottomBody.rotate(0.4, new Vector2(-275, 300));
//        bodies.add(leftBottomBody);
        //left part 2
        Body leftBottomBody2 = createBodyAndGameObject(100, 28, new Vector2(-460, 300), "pipeSectionPinball.png", new Vector2(800,25), 0.092);
        leftBottomBody2.rotate(0.4, new Vector2(-275, 300));
//        bodies.add(leftBottomBody2);
        //right
        //todo hitbox aan einde buis toevoegen
        Body rightBottomBody = createBodyAndGameObject(250, 28, new Vector2(285, 300), "sidePipePinballRight.png", new Vector2(0,-25), 0.092);
        rightBottomBody.rotate(-0.4, new Vector2(275, 300));
//        bodies.add(rightBottomBody);

    }

    //method for returning a body for the borders of the pinball game
    //todo body en gameobject in klasse toevoegen
    private Body createBodyAndGameObject(double width, double height, Vector2 vector2, String imageFile, Vector2 offset, double scale) {
        Body body = new Body();
        BodyFixture bodyFixture = new BodyFixture(Geometry.createRectangle(width, height));
        body.addFixture(bodyFixture);
        body.setMass(MassType.INFINITE);
        body.translate(vector2);
        bodies.add(body);
        objects.add(new GameObject(imageFile, body, offset, scale));
        return body;
    }

    public ArrayList<GameObject> getObjects() {
        return objects;
    }

    public ArrayList<Body> getBodies() {
        return bodies;
    }
}
