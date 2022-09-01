package cz.muni.fi.pb162.project.geometry;

import java.util.Objects;

/**
 * Class representing a Polygon with a Color.
 *
 * @author Michal Krejcir
 */
public class ColoredPolygon {

    private final Polygon polygon;
    private final Color color;

    /**
     * Creates a new ColoredPolygon.
     * @param polygon Polygon of the new ColoredPolygon.
     * @param color Color of the new ColoredPolygon.
     */
    public ColoredPolygon(Polygon polygon, Color color) {

        this.polygon = polygon;
        this.color = color;
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ColoredPolygon that = (ColoredPolygon) o;
        // TODO
        // figure out why this comparison sometimes doesn't work.
        // return that.polygon == this.polygon && that.color == this.color;
        return that.polygon.equals(this.polygon) && that.color == this.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(polygon, color);
    }
}

