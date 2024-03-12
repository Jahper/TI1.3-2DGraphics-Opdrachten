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
        //side borders
        //left
        //bottom
        createBodyAndGameObject(40, 300, new Vector2(-400, 398), "FrameImages/brickSideMediumLeft.png", new Vector2(-210, 30), 0.1007);

        //bottom borders
        //left part 1
        Body leftBottomBody = createBodyAndGameObject(250, 28, new Vector2(-285, 300), "FrameImages/sidePipePinballLeft.png", new Vector2(0, 25), 0.092);
        leftBottomBody.rotate(0.4, new Vector2(-275, 300));
        //left part 2
        Body leftBottomBody2 = createBodyAndGameObject(100, 28, new Vector2(-450, 300), "FrameImages/pipeSectionPinball.png", new Vector2(800, 25), 0.092);
        leftBottomBody2.rotate(0.4, new Vector2(-275, 300));
        //right
        Body rightBottomBody = createBodyAndGameObject(250, 28, new Vector2(285, 300), "FrameImages/sidePipePinballRight.png", new Vector2(0, -25), 0.092);
        rightBottomBody.rotate(-0.4, new Vector2(275, 300));

        //side borders
        //left
        //middle
        createBodyAndGameObject(40, 400, new Vector2(-375, -100), "FrameImages/brickAndPipe.png", new Vector2(-42, 510), 0.1);
        //top
        createBodyAndGameObject(40, 100, new Vector2(-375, -455), "FrameImages/brickSideLeftSmall.png", new Vector2(-225, 80), 0.1007);

        //right
        createBodyAndGameObject(40, 500, new Vector2(400, 300), "FrameImages/brickSidePinballRightMedium.png", new Vector2(-225, 180), 0.1007);

        //side funnels
        //left
        createBodyAndGameObject(40, 1000, new Vector2(-475, 20), "FrameImages/brickSidePinballLeft.png", new Vector2(-235, 62), 0.1007);
        //right
        createBodyAndGameObject(40, 1000, new Vector2(485, 20), "FrameImages/brickSidePinballRight.png", new Vector2(235, -62), 0.1007);

        //upper border
        createBodyAndGameObject(1000, 40, new Vector2(5, -500), "FrameImages/brickTopPinball.png", new Vector2(-75, -230), 0.1007);
    }

    //method for returning a body for the borders of the pinball game
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
