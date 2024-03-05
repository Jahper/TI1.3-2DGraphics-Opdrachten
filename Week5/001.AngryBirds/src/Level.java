import org.dyn4j.dynamics.Body;

import java.util.ArrayList;

public interface Level {
    ArrayList<GameObject> getGameObjects();
    Body getRed();

}

