import java.awt.geom.Point2D;
import java.util.Random;

public class MovingPoint {
    private Point2D point;
    private Point2D direction;

    public MovingPoint() {
        Random random = new Random();
        this.point = new Point2D.Double(random.nextInt(1000), random.nextInt(1000));
        this.direction = new Point2D.Double(random.nextDouble() * 15, random.nextDouble() * 15);
    }

    public Point2D getPoint() {
        return point;
    }

    public Point2D getDirection() {
        return direction;
    }

    public void setPoint(Point2D point) {
        this.point = point;
    }

    public void setDirection(Point2D direction) {
        this.direction = direction;
    }

}
