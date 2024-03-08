import java.awt.*;
import java.awt.geom.Point2D;
import java.nio.channels.Pipe;
import java.util.ArrayList;
import java.util.Random;

public class MovingPoint {
    private Point2D point;
    private Point2D direction;
    private ArrayList<Point2D> history = new ArrayList<>();

    public MovingPoint() {
        Random random = new Random();
        this.point = new Point2D.Double(random.nextInt(1000), random.nextInt(1000));
        this.direction = new Point2D.Double(random.nextDouble() * 2, random.nextDouble());
        history.add(this.point);
    }

    public Point2D getPoint() {
        return point;
    }

    public Point2D getDirection() {
        return direction;
    }

    public void setPoint(Point2D point) {
//        update(point);
        this.point = point;
    }

    public void setDirection(Point2D direction) {
        this.direction = direction;
    }

    public ArrayList<Point2D> getHistory() {
        return history;
    }

    private void update(Point2D p){
        history.add(p);
    }
}
