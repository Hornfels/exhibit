package cz.muni.fi.pb162.project.geometry;

import cz.muni.fi.pb162.project.utils.SimpleMath;
import cz.muni.fi.pb162.project.exception.MissingVerticesException;

/**
 * Class representing a Polygon without specifying exact Vertex storage method.
 * @author Michal Krejcir
 */
public abstract class SimplePolygon implements Polygon {

    protected SimplePolygon(Vertex2D[] vertices) throws IllegalArgumentException, MissingVerticesException {

        if (vertices == null) {
            throw new IllegalArgumentException("input array can't be null");
        }
        if (vertices.length < 3) {
            throw new MissingVerticesException("input array must contain at least 3 vertices");
        }
        for (int i = 0; i < vertices.length; i++) {
            if (vertices[i] == null) {
                throw new IllegalArgumentException("input array can't contain null");
            }
        }
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

        StringBuilder result = new StringBuilder("Polygon: vertices =");

        for (int i = 0; i < getNumVertices(); i++) {
            result.append(" " + getVertex(i).toString());
        }

        return result.toString();
    }

    /**
     * Gets an index-th Vertex of the SimplePolygon.
     * @param index Vertex index, can't be negative.
     * @return The index-th Vertex.
     */
    public abstract Vertex2D getVertex(int index);

    /**
     * Gets number of Vertices of the SimplePolygon.
     * @return Number of Vertices.
     */
    public abstract int getNumVertices();
}
