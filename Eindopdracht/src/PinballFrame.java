import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.World;
import org.dyn4j.dynamics.joint.RevoluteJoint;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;

import java.util.ArrayList;

public class PinballFrame {
    private World world;
    private ArrayList<GameObject> objects = new ArrayList<>();
    private ArrayList<Body> bodies = new ArrayList<>();
    private Body flipperLeft;
    private Body flipperRight;

    public PinballFrame(World world) {
        this.world = world;

        createFrame();
    }

    public void flipLeft() {
        flipperLeft.applyImpulse(new Vector2(0, -10000));
    }

    public void flipRight() {
        flipperRight.applyImpulse(new Vector2(0, -10000));
    }

    private void createFlippers(Body left, Body right) {
        //todo voor beide toevoegen aan gameobjects en textures maken
        //todo blokken toevoegen om rom te beperken van flipper

        Body flipperLeft = new Body();
        BodyFixture bodyFixtureLeft = new BodyFixture(Geometry.createRectangle(15, 2.8));
//        bodyFixture.setRestitution(10);
        flipperLeft.addFixture(bodyFixtureLeft);
        flipperLeft.setMass(MassType.NORMAL);//fixme
        flipperLeft.setGravityScale(1);
        flipperLeft.translate(new Vector2(-9, 34));
        bodies.add(flipperLeft);
        this.flipperLeft = flipperLeft;

        RevoluteJoint jointLeft = new RevoluteJoint(left, flipperLeft, new Vector2(-18, 34.5));
        world.addJoint(jointLeft);

//        Body test = new Body();
//        BodyFixture b = new BodyFixture(Geometry.createRectangle(1,1));
//        test.addFixture(b);
//        test.translate(new Vector2(-18, 34.5));
////        bodies.add(test);

        Body flipperRight = new Body();
        BodyFixture bodyFixtureRight = new BodyFixture(Geometry.createRectangle(15, 2.8));
        flipperRight.addFixture(bodyFixtureRight);
        flipperRight.setMass(MassType.NORMAL);//fixme
        flipperRight.setGravityScale(1);
        flipperRight.translate(new Vector2(9, 34));
        bodies.add(flipperRight);
        this.flipperRight = flipperRight;

        RevoluteJoint jointRight = new RevoluteJoint(right, flipperRight, new Vector2(18, 34.5));
        world.addJoint(jointRight);


    }

    private void createFrame() {
        //todo textures inkorten
        //side borders
        //left
        //bottom
        createBodyAndGameObject(4, 30, new Vector2(-37.5, 41), "FrameImages/brickSideMediumLeft.png", new Vector2(-210, 150), 0.012);

        //bottom borders
        //left part 1
        Body leftBottomBody = createBodyAndGameObject(25, 2.8, new Vector2(-28.5, 30), "FrameImages/sidePipePinballLeft.png", new Vector2(-290, 25), 0.012);
        leftBottomBody.rotate(0.4, new Vector2(-27.5, 30));
        //left part 2
        Body leftBottomBody2 = createBodyAndGameObject(10, 2.8, new Vector2(-45, 30), "FrameImages/pipeSectionPinball.png", new Vector2(900, 25), 0.012);
        leftBottomBody2.rotate(0.4, new Vector2(-27.5, 30));
        //right
        Body rightBottomBody = createBodyAndGameObject(25, 2.8, new Vector2(28.5, 30), "FrameImages/sidePipePinballRight.png", new Vector2(0, -25), 0.012);
        rightBottomBody.rotate(-0.4, new Vector2(27.5, 30));

        //side borders
        //left
        //middle
        createBodyAndGameObject(4, 40, new Vector2(-37.5, -10), "FrameImages/brickAndPipe.png", new Vector2(-30, 200), 0.012);
        //top
        createBodyAndGameObject(4, 10, new Vector2(-37.5, -45.5), "FrameImages/brickSideLeftSmall.png", new Vector2(-225, -50), 0.012);

        //right
        createBodyAndGameObject(4, 50, new Vector2(40, 30), "FrameImages/brickSidePinballRightMedium.png", new Vector2(-225, 600), 0.012);

        //side funnels
        //left
        createBodyAndGameObject(4, 100, new Vector2(-47.5, 2), "FrameImages/brickSidePinballLeft.png", new Vector2(-235, 520), 0.012);
        //right
        createBodyAndGameObject(4, 100, new Vector2(49.2, 2), "FrameImages/brickSidePinballRight.png", new Vector2(235, 400), 0.012);

        //upper border
        createBodyAndGameObject(100, 4, new Vector2(0.5, -50), "FrameImages/topBrickLayer.png", new Vector2(-131.5, -230), 0.012);
        //temporary bottom border for testing
        //fixme
        Body b = new Body();
        BodyFixture bf = new BodyFixture(Geometry.createRectangle(200, 1));
        bf.setFriction(0.7);
        b.addFixture(bf);
        b.setMass(MassType.INFINITE);
        b.translate(new Vector2(0, 48.5));
        bodies.add(b);

        createFlippers(leftBottomBody, rightBottomBody);
    }

    //method for returning a body for the borders of the pinball game
    private Body createBodyAndGameObject(double width, double height, Vector2 vector2, String imageFile, Vector2 offset, double scale) {
        Body body = new Body();
        BodyFixture bodyFixture = new BodyFixture(Geometry.createRectangle(width, height));
        bodyFixture.setFriction(0.7);
//        bodyFixture.setRestitution(10);
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
