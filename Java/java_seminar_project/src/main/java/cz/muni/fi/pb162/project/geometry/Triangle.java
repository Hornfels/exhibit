package cz.muni.fi.pb162.project.geometry;
import cz.muni.fi.pb162.project.utils.SimpleMath;

/**
 * Class representing a Triangle in a 2D plane.
 *
 * @author Michal Krejcir
 */
public class Triangle extends ArrayPolygon implements Measurable {

    private final Triangle[] subTriangles = new Triangle[3];

    private static final double MAX_DEVIATION = 0.001;

    /**
     * Constructs a new Triangle based on the three given Vertices.
     * @param vert1 Vertex of the new Triangle.
     * @param vert2 Vertex of the new Triangle.
     * @param vert3 Vertex of the new Triangle.
     */
    public Triangle(Vertex2D vert1, Vertex2D vert2, Vertex2D vert3) {

        super(new Vertex2D[]{vert1, vert2, vert3});
    }

    /**
     * Constructs a new Triangle based on the three given Vertices and divides it right away.
     * @param vert1 Vertex of the new Triangle.
     * @param vert2 Vertex of the new Triangle.
     * @param vert3 Vertex of the new Triangle.
     * @param depth Depth to which the new Triangle will be divided.
     */
    public Triangle(Vertex2D vert1, Vertex2D vert2, Vertex2D vert3, int depth) {
        this(vert1, vert2, vert3);
        divide(depth);
    }

    @Override
    public double getWidth() {
        return SimpleMath.maxX(this) - SimpleMath.minX(this);
    }

    @Override
    public double getHeight() {
        return SimpleMath.maxY(this) - SimpleMath.minY(this);
    }


    @Override
    public String toString() {

        return String.format("Triangle: vertices=%s %s %s",
                getVertex(0).toString(),
                getVertex(1).toString(),
                getVertex(2).toString());
    }

    /**
     * Determines if the Triangle has already been divided into sub-triangles.
     * @return True if it's been divided, false otherwise.
     */
    public boolean isDivided() {
        return subTriangles[0] != null;
    }

    /**
     * Divides the Triangle into 3 corresponding sub-triangles.
     * @return True if division was successful, false if the Triangle was already divided.
     */
    public boolean divide() {

        if (isDivided()) {
            return false;
        }
        Vertex2D[] middleVertices = new Vertex2D[3];

        for(int i = 0; i <= 2; i++) {
            middleVertices[i] = getVertex(i).createMiddle(getVertex((i+1) % 3));
        }

        for(int i = 0; i <= 2; i++) {
            subTriangles[i] = new Triangle(getVertex(i), middleVertices[i], middleVertices[(i+2) % 3]);
        }
        return true;
    }

    /**
     * Recursively divides the Triangle into sub-Triangles depth times.
     * @param depth How many levels the Triangle should be divided into.
     */
    public void divide(int depth) {
        if (depth <= 0) {
            return;
        }
        divide();
        for (int i = 0; i <= 2; i++) {
            subTriangles[i].divide(depth - 1);
        }
        return;
    }

    /**
     * Returns a sub-triangle from the Triangle sub-triangles.
     * @param index Index of the sub-triangle. Can range from 0 to 2.
     * @return Corresponding sub-triangle, null if index out-of-range or Triangle's not divided yet.
     */
    public Triangle getSubTriangle(int index) {

        if (index > 2 || index < 0 || !isDivided()) {
            return null;
        }
        return subTriangles[index];
    }

    /**
     * Decides whether the Triangle is equilateral (all sides are the same length) or not.
     * @return True if the Triangle is equilateral, false otherwise.
     */
    public boolean isEquilateral() {
        double[] sideLengths = new double[3];

        for(int i = 0; i <= 2; i++) {
            sideLengths[i] = getVertex(i).distance(getVertex((i+1) % 3));
        }

        for(int i = 0; i <= 2; i++) {
            if (Math.abs(sideLengths[i] - sideLengths[(i+1) % 3]) > MAX_DEVIATION) {
                return false;
            }
        }
        return true;
    }
}
