package cz.muni.fi.pb162.project.geometry;

/**
 * @author Michal Krejcir
 */
public interface Colored {

    /**
     * Gets Color of the given object.
     * @return Color of the given object.
     */
    Color getColor();

    /**
     * Changes the Color of the given object.
     * @param color Color to which the object color should be changed.
     */
    void setColor(Color color);
}
