package cz.muni.fi.pb162.hw01.impl;

/**
 * CLass representing a Tile with it's value.
 * @author Michal Krejčíř
 */
public class Tile {

    private char value;
    public static final char EMPTY = ' ';

    /**
     * Creates an empty Tile.
     */
    public Tile() {
        value = EMPTY;
    }

    public char getValue() {
        return value;
    }

    public void setValue(char value) {
        this.value = value;
    }

    /**
     * Determines if the Tile is empty.
     * @return True if Empty, false otherwise.
     */
    public boolean isEmpty() {
        return value == EMPTY;
    }
}
