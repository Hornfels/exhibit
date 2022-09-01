package cz.muni.fi.pb162.hw01.impl;

/**
 * Interface with functionality required for Board in tic-tac-toe.
 * @author Michal Krejčíř
 * Heavily inspired by https://gitlab.fi.muni.cz/-/snippets/68
 * written by jcechace.
 */
public interface IBoard {

        /**
         * Places a symbol on given coordinates
         * @param x row
         * @param y column
         * @param value symbol
         */
        void setTileValue(int x, int y, char value);

        /**
         * Returns board's cell on given coordinates
         * @param x row
         * @param y column
         * @return character in cell or null
         */
        Tile getTile(int x, int y);

        /**
         * @return size of the board
         */
        int getSize();

        /**
         * @return true if the board is completely filled by symbols
         */
        boolean isFull();

        /**
         * @param x row
         * @param y column
         * @return true if cell is empty
         */
        boolean isTileEmpty(int x, int y);

        /**
         * Creates a deep copy of this board
         * @return copy of this board
         */
        Board copy();

        /**
         * Returns xth row of Tiles from the Board.
         * @param row Index of the row in the Board.
         * @return Wanted row, null for invalid coordinate.
         */
        Tile[] getRow(int row);

        /**
         * Returns yth column of Tiles from the Board.
         * @param col Index of the column in the Board.
         * @return Wanted column, null for invalid coordinate.
         */
        Tile[] getCol(int col);

        /**
         * Returns a diagonal of Tiles from the Board. Diagonal starts at the edge of the Board.
         * @param row Row in which the diagonal starts.
         * @param col Column in which the diagonal starts.
         * @param leftToRight Direction of the diagonal. True for left to right, false for right to left.
         * @return Wanted diagonal, null for invalid coordinates.
         */
        Tile[] getDiag(int row, int col, boolean leftToRight);



}
