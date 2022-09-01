package cz.muni.fi.pb162.project.geometry;

import java.util.Arrays;

/**
 * Class representing a Polygon with Vertices stored in an Array.
 *
 * @author Michal Krejcir
 */
public class ArrayPolygon extends SimplePolygon {

    private final Vertex2D[] vertices;


    /**
     * Creates a new ArrayPolygon.
     * @param vertices Array of vertices of the new polygon.
     */
    public ArrayPolygon(Vertex2D[] vertices) throws IllegalArgumentException {

        super(vertices);
        this.vertices = Arrays.copyOf(vertices, vertices.length);
    }

    @Override
    public Vertex2D getVertex(int i) throws IllegalArgumentException {

        if (i < 0) {
            throw new IllegalArgumentException("index smaller than 0");
        }
        return vertices[i % vertices.length];
    }

    @Override
    public int getNumVertices() {
        return vertices.length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ArrayPolygon that = (ArrayPolygon) o;
        return Arrays.equals(vertices, that.vertices);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(vertices);
    }
}
