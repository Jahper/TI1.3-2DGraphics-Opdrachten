import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;

import java.util.ArrayList;

public class Level_1 implements Level {
    private ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
    private World world;
    private Body red;

    public Level_1(World world) {
        this.world = world;

        //birb
        red = new Body();
        BodyFixture fixture = new BodyFixture(Geometry.createCircle(1));
        fixture.setRestitution(0.2);
        fixture.setDensity(20);
        red.addFixture(fixture);
        red.translate(new Vector2(-36, -11.5));


        GameObject redObject = new GameObject("angry-bird-red-image-angry-birds-transparent-png-1637889.png", red, new Vector2(0, 0), 1);

        gameObjects.add(redObject);
        world.addBody(red);

        //blocks
        createBlocks(5, 5, -18);
        createBlocks(6, 20, -18);
    }

    private void createBlocks(int amount, double x, double y) {
        for (int i = 0; i < amount; i++) {
            Body block = new Body();
            BodyFixture blockFix = new BodyFixture(Geometry.createRectangle(3.7, 3.7));
            blockFix.setFriction(0.5);
            blockFix.setRestitution(0.6);
            block.addFixture(blockFix);
            block.setGravityScale(2);
            block.setMass(MassType.NORMAL);
            block.translate(new Vector2(x, y + i * 3.5));

            GameObject o = new GameObject("321024-removebg-preview.png", block, new Vector2(0, 0), 0.8);

            gameObjects.add(o);

            world.addBody(block);
        }
    }

    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

    public Body getRed() {
        return red;
    }
}
