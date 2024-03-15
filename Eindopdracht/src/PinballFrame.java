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
        //todo textures inkorten
        //side borders
        //left
        //bottom
        createBodyAndGameObject(4, 30, new Vector2(-40, 39.8), "FrameImages/brickSideMediumLeft.png", new Vector2(-21, 3), 0.012);

        //bottom borders
        //left part 1
        Body leftBottomBody = createBodyAndGameObject(25, 2.8, new Vector2(-28.5, 30), "FrameImages/sidePipePinballLeft.png", new Vector2(-100, 2.5), 0.012);
        leftBottomBody.rotate(0.4, new Vector2(-27.5, 30));
        //left part 2
        Body leftBottomBody2 = createBodyAndGameObject(10, 2.8, new Vector2(-45, 30), "FrameImages/pipeSectionPinball.png", new Vector2(900, 2.5), 0.012);
        leftBottomBody2.rotate(0.4, new Vector2(-27.5, 30));
        //right
        Body rightBottomBody = createBodyAndGameObject(25, 2.8, new Vector2(28.5, 30), "FrameImages/sidePipePinballRight.png", new Vector2(80, -2.5), 0.012);
        rightBottomBody.rotate(-0.4, new Vector2(27.5, 30));

        //side borders
        //left
        //middle
        createBodyAndGameObject(4, 40, new Vector2(-37.5, -10), "FrameImages/brickAndPipe.png", new Vector2(-4.2, 500), 0.012);
        //top
        createBodyAndGameObject(4, 10, new Vector2(-37.5, -45.5), "FrameImages/brickSideLeftSmall.png", new Vector2(-22.5, 8), 0.012);

        //right
        createBodyAndGameObject(4, 50, new Vector2(40, 30), "FrameImages/brickSidePinballRightMedium.png", new Vector2(-22.5, 18), 0.012);

        //side funnels
        //left
        createBodyAndGameObject(4, 100, new Vector2(-47.5, 2), "FrameImages/brickSidePinballLeft.png", new Vector2(-235, 6.2), 0.012);
        //right
        createBodyAndGameObject(4, 100, new Vector2(48.5, 2), "FrameImages/brickSidePinballRight.png", new Vector2(235, -6.2), 0.012);

        //upper border
        createBodyAndGameObject(100, 4, new Vector2(0.5, -50), "FrameImages/brickTopPinball.png", new Vector2(-7.5, -230), 0.012);
        //temporary bottom border for testing
        //fixme
        Body b = new Body();
        BodyFixture bf = new BodyFixture(Geometry.createRectangle(200, 1));
        b.addFixture(bf);
        b.setMass(MassType.INFINITE);
        b.translate(new Vector2(0, 48.5));
        bodies.add(b);
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
