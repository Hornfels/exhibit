package cz.muni.fi.pb162.project.geometry;

/**
 * Enum representing possible colors.
 * @author Michal Krejcir
 */
public enum Color {
    RED, GREEN, BLUE, YELLOW, ORANGE, BLACK, WHITE;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}

