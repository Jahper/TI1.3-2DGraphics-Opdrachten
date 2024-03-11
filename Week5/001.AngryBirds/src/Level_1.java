import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;

import java.util.ArrayList;

public class Level_1 implements Level {
    private ArrayList<GameObject> gameObjects = new ArrayList<>();
    private World world;

    public Level_1(World world) {
        this.world = world;

        //blocks
        createBlocks(5, 5, -18);
        createBlocks(6, 20, -18);

        //piggies
        createPiggy(1, 5, 0);
    }

    private void createPiggy(int amount, int x, int y) {
        Body piggy = new Body();
        BodyFixture pigFix = new BodyFixture(Geometry.createCircle(1.7));
        pigFix.setFriction(0.5);
        pigFix.setRestitution(0.2);
        piggy.addFixture(pigFix);
        piggy.setGravityScale(2);
        piggy.setMass(MassType.NORMAL);
        piggy.translate(new Vector2(x,y));

        GameObject o = new GameObject("badPiggy.png", piggy, new Vector2(0, 0), 0.45);

        gameObjects.add(o);

        world.addBody(piggy);
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
}
