package cz.muni.fi.pb162.project.comparator;

import cz.muni.fi.pb162.project.geometry.Vertex2D;

import java.util.Comparator;

/**
 * Compares two vertices based on their X and Y coordinates in inverse order.
 * That is, Vert1 > Vert2, if Vert1 has smaller coordinates.
 * @author Michal Krejcir
 */
public class VertexInverseComparator implements Comparator<Vertex2D> {

    @Override
    public int compare(Vertex2D o1, Vertex2D o2) {

        return o1.compareTo(o2) * (-1);
    }
}
