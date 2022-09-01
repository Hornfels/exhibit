package cz.muni.fi.pb162.project.geometry;

/**
 * Class representing a Circle in a 2D plane.
 *
 * @author Michal Krejcir
 */
public class Circle extends GeneralRegularPolygon implements Circular, Measurable  {

    /**
     * Creates a new Circle with the given center and radius.
     * @param center Center of the new Circle.
     * @param radius Radius of the new Circle.
     */
    public Circle(Vertex2D center, double radius) {
        super(center, Integer.MAX_VALUE, radius);
        this.setColor(Color.RED);
    }

    /**
     * Creates a new Circle with center in [0, 0] and radius 1.
     */
    public Circle() {
        this(new Vertex2D(0, 0), 1);
    }

    @Override
    public String toString() {
        return String.format("Circle: center=%s, radius=%s", getCenter(), getRadius());
    }

    @Override
    public double getEdgeLength() {
        return 0.0;
    }

}
