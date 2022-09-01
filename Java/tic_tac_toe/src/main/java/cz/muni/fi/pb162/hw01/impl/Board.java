package cz.muni.fi.pb162.hw01.impl;

import java.util.LinkedList;

/**
 * Class representing a tic-tac-toe board made up of individual Tiles.
 * @author Michal Krejčíř
 */
public class Board implements IBoard {

    private final int size;
    private Tile[][] tiles;

    /**
     * Constructs new Board of the given size and with all Tiles initialized to EMPTY values.
     * @param size Number of Tiles in each row/column.
     */
    public Board(int size) {

        this.size = size;
        this.tiles = new Tile[size][size];

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                tiles[x][y] = new Tile();
            }
        }
    }

    public int getSize() {

        return size;
    }

    /**
     * Gets Tile at the given coordinates.
     * @param x Row of the wanted Tile.
     * @param y Column of the wanted Tile.
     * @return Tile, null for invalid coordinates.
     */
    public Tile getTile(int x, int y) {

        if (x >= size || y >= size) {
            return null;
        }
        return tiles[x][y];
    }

    @Override
    public boolean isFull() {

        for (int x = 0; x < size; x ++) {
            for (int y = 0; y < size; y ++) {
                if (getTile(x, y).isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean isTileEmpty(int x, int y) {

        if (x >= size || y >= size) {
            return false;
        }
        return getTile(x, y).isEmpty();
    }

    @Override
    public Board copy() {

        Board copy = new Board(getSize());

        for (int x = 0; x < size; x ++) {
            for (int y = 0; y < size; y++) {
                copy.setTileValue(x, y, getTile(x, y).getValue());
            }
        }
        return copy;

    }

    @Override
    public Tile[] getRow(int row) {

        if (row >= size) {
            return null;
        }
        Tile[] result = new Tile[size];

        for (int y = 0; y < size; y++) {
            result[y] = getTile(row, y);
        }
        return result;
    }

    @Override
    public Tile[] getCol(int col) {

        if (col >= size) {
            return null;
        }
        Tile[] result = new Tile[size];

        for (int x = 0; x < size; x++) {
            result[x] = getTile(x, col);
        }
        return result;
    }

    @Override
    public Tile[] getDiag(int row, int col, boolean leftToRight) {

        if (row >= size || col >= size) {
            return null;
        }
        LinkedList<Tile> result = new LinkedList<Tile>();
        int i = 0;

        while (row < size && col < size && col >= 0) {
            result.add(getTile(row, col));
            row++;
            i++;
            if (leftToRight) {
                col++;
            } else {
                col--;
            }
        }
        return result.toArray(new Tile[result.size()]);
    }


    @Override
    public void setTileValue(int x, int y, char value) {

        if (x >= size || y >= size) {
            return;
        }
        getTile(x, y).setValue(value);
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();
        int boardSize = getSize();
        String rowSeparator = buildRowSeparator(boardSize);

        builder.append(rowSeparator);
        for (int x = 0; x < boardSize; x++) {
            builder.append('|');

            for (int y = 0; y < boardSize; y++) {
                builder.append(getTile(x, y).getValue());
                builder.append('|');
            }
            builder.append('\n');
            builder.append(rowSeparator);
        }
        return builder.toString();
    }

    /**
     * Builds row separator with \n ending for printing out formatted board.
     * @param size Size of board.
     * @return Row separator.
     */
    private static String buildRowSeparator(int size) {

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 2 * size + 1; i++) {
            builder.append('-');
        }
        builder.append('\n');
        return builder.toString();
    }
}
