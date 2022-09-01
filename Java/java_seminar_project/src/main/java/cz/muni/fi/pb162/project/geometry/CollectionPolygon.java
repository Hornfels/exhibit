package cz.muni.fi.pb162.project.geometry;

import cz.muni.fi.pb162.project.exception.MissingVerticesException;
import cz.muni.fi.pb162.project.utils.SimpleMath;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Class representing a Polygon with Vertices stored in a List.
 *
 * @author Michal Krejcir
 */
public class CollectionPolygon extends SimplePolygon {

    private final List<Vertex2D> vertices;


    /**
     * Creates a new ArrayPolygon.
     * @param vertices Array of vertices of the new polygon.
     */
    public CollectionPolygon(Vertex2D[] vertices) throws IllegalArgumentException, MissingVerticesException {

        super(vertices);
        this.vertices = new LinkedList<>(Arrays.asList(Arrays.copyOf(vertices, vertices.length)));
    }

    /**
     * Creates a new ArrayPolygon.
     * @param vertices List of vertices of the new polygon.
     */
    public CollectionPolygon(List<Vertex2D> vertices) throws IllegalArgumentException, MissingVerticesException {

        super(vertices.toArray(new Vertex2D[0]));
        this.vertices = new LinkedList(vertices);
    }

    @Override
    public Vertex2D getVertex(int i) throws IllegalArgumentException {

        if (i < 0) {
            throw new IllegalArgumentException("index smaller than 0");
        }
        return vertices.get(i % vertices.size());
    }

    @Override
    public int getNumVertices() {
        return vertices.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CollectionPolygon that = (CollectionPolygon) o;
        return vertices.equals(that.vertices);
    }

    @Override
    public int hashCode() {
        return vertices.hashCode();
    }

    /**
     * Creates a new CollectionPolygon based on the current one, but
     * with it's leftmost Vertices removed (those with the lowest X coordinate).
     * @return New CollectionPolygon, null if no Vertex remains after the removal.
     */
    public CollectionPolygon withoutLeftmostVertices() {

        double minX = SimpleMath.minX(this);
        List<Vertex2D> newVerts = new LinkedList<Vertex2D>();

        for (int i = 0; i < getNumVertices(); i++) {

            Vertex2D currentVert = getVertex(i);

            if (currentVert.getX() > minX) {
                newVerts.add(currentVert);
            }
        }
        if (newVerts.isEmpty()) {
            return null;
        }
        return new CollectionPolygon(newVerts.toArray(new Vertex2D[0]));
    }
}
