import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Renderable{
    private Color color;
    private Point2D position;
    private Shape shape;

    public Renderable(Color color, Point2D position) {
        this.color = color;
        this.position = position;
        this.shape = new Rectangle2D.Double(this.position.getX(), this.position.getY(), 50, 50);
    }

    public Point2D getPosition() {
        return position;
    }

    public Shape getShape() {
        return shape;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }
}
