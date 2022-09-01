package cz.muni.fi.pb162.project.geometry;

/**
 * Class representing a Vertex in a 2D plane.
 *
 * @author Michal Krejcir
 */

public class Vertex2D implements Comparable<Vertex2D> {

    private final double x;
    private final double y;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    /**
     * Constructs a new Vertex based on the given x and y coordinates.
     * @param x X coordinate of the new Vertex.
     * @param y Y coordinate of the new Vertex.
     */
    public Vertex2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return String.format("[%s, %s]", x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        Vertex2D otherVertex = (Vertex2D) o;
        return Double.compare(otherVertex.getX(), getX()) == 0 && Double.compare(otherVertex.getY(), getY()) == 0;
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash += 31 * (int)Double.doubleToLongBits(getX());
        hash += 31 * 2 * ((int)Double.doubleToLongBits(getY()));
        return hash;
    }

    /**
     * Creates a new Vertex with coordinates computed as a middle points
     * between the two Vertices.
     * @param givenVertex Vertex whose coordinates are used for computing the new Vertex coordinates
     * @return New Vertex.
     */
    public Vertex2D createMiddle(Vertex2D givenVertex) {
        double middleX = (x + givenVertex.x) / 2;
        double middleY = (y + givenVertex.y) / 2;
        return new Vertex2D(middleX, middleY);
    }

    /**
     * Calculates distance between the two Vertices.
     * @param givenVertex Vertex whose distance from this Vertex should be calculated.
     * @return Distance between two Vertices, -1.0 if givenVertex is null.
     */
    public double distance(Vertex2D givenVertex) {
        if (givenVertex == null) {
            return -1.0;
        }
        return Math.sqrt(Math.pow(givenVertex.getX() - getX(), 2) + Math.pow(givenVertex.getY() - getY(), 2));
    }

    @Override
    public int compareTo(Vertex2D o) {

        int diffX = Double.compare(this.getX(), o.getX());

        if (diffX != 0) {
            return diffX;
        }
        return Double.compare(this.getY(), o.getY());
    }
}
