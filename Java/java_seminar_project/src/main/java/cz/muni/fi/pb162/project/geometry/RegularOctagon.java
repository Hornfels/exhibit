package cz.muni.fi.pb162.project.geometry;

/**
 * Class representing a Regular octagon in 2D plane.
 * @author Michal Krejcir
 */
public class RegularOctagon extends GeneralRegularPolygon {

    /**
     * Creates a new RegularOctagon with the given attributes.
     * @param center Center of the new RegularOctagon.
     * @param radius Radius of the new RegularOctagon.
     */
    public RegularOctagon(Vertex2D center, double radius) {
        super(center, 8, radius);
    }
}
