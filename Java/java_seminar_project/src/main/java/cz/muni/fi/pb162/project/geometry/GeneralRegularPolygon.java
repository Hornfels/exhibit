package cz.muni.fi.pb162.project.geometry;

/**
 * Class representing a Regular polygon in 2D plane.
 * @author Michal Krejcir
 */
public class GeneralRegularPolygon implements RegularPolygon, Colored {

    private Vertex2D center;
    private int numEdges;
    private double radius;
    private Color color;

    /**
     * Creates a new GeneralRegularPolygon with the given attributes.
     * @param center Center of the new GeneralRegularPolygon.
     * @param numberOfEdges Number of edges of the new GeneralRegularPolygon.
     * @param radius Radius of the new GeneralRegularPolygon.
     */
    public GeneralRegularPolygon(Vertex2D center, int numberOfEdges, double radius) {
        this.center = center;
        this.numEdges = numberOfEdges;
        this.radius = radius;
        this.color = Color.BLACK;
    }

    @Override
    public Vertex2D getCenter() {
        return center;
    }

    public int getNumEdges() {
        return numEdges;
    }

    @Override
    public double getRadius() {
        return radius;
    }

    @Override
    public Color getColor() {
        return color;
    }

    public void setCenter(Vertex2D center) {
        this.center = center;
    }

    public void setNumEdges(int numberOfEdges) {
        this.numEdges = numberOfEdges;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public double getEdgeLength() {
        return 2 * getRadius() * Math.sin(Math.PI / getNumEdges());
    }

    @Override
    public double getHeight() {
        return 2 * getRadius();
    }

    @Override
    public double getWidth() {
        return getHeight();
    }

    @Override
    public Vertex2D getVertex(int index) {

        double x = getCenter().getX()
                - getRadius() * Math.cos(index * 2 * (Math.PI / getNumEdges()));
        double y = getCenter().getY()
                - getRadius() * Math.sin(index * 2 * (Math.PI / getNumEdges()));
        return new Vertex2D(x, y);
    }

    @Override
    public String toString() {
        return String.format("%s-gon: center=%s, radius=%s, color=%s",
                getNumEdges(), getCenter(), getRadius(), getColor());
    }
}
