package cz.muni.fi.pb162.hw01.impl;

import cz.muni.fi.pb162.hw01.Utils;
import cz.muni.fi.pb162.hw01.cmd.Messages;

import java.util.LinkedList;
import java.util.List;

/**
 * Class representing a tic-tac-toe game instance.
 * @author Michal Krejčíř
 */
public class Game implements IResolveCommand {

    private Board curBoard;
    private List<Board> history;
    private int historySize;
    private char[] players;
    private int winSize;
    private int turn = 1;
    private boolean quitRequested = false;

    /**
     * Creates a new Game based on the given arguments.
     * @param size Size of the Game Board.
     * @param historySize How many turns can be rewinded.
     * @param playerSymbols Symbols used by players to mark their tokens on the Board.
     * @param winSize How many symbols in a line is a player required to have to win.
     */
    public Game(int size, int historySize, String playerSymbols, int winSize) {

        curBoard = new Board(size);
        history = new LinkedList<Board>();
        this.historySize = historySize;
        this.winSize = winSize;
        players = new char[playerSymbols.length()];

        for (int i = 0; i < playerSymbols.length(); i++) {
            players[i] = playerSymbols.charAt(i);
        }
    }

    /**
     * Decides if the game is over.
     * @return Null for not over, player symbol in String if a given player has won,
     * String "Over" for game over without a winner.
     */
    public String isOver() {

        char winner = isWinner();

        if (winner != Tile.EMPTY) {
            return String.valueOf(winner);
        }

        if (curBoard.isFull() || quitRequested) {
            return new String("Over");
        }

        return null;
    }

    /**
     * Checks if any player has won.
     * @return Winning player's symbol, Tile.EMPTY if no one has won yet.
     */
    private char isWinner() {

        char winner;

        // check rows, cols and half of diagonals
        for (int i = 0; i < curBoard.getSize(); i++) {
            if ((winner = checkLine(curBoard.getRow(i))) != Tile.EMPTY) {
                return  winner;
            }
            if ((winner = checkLine(curBoard.getCol(i))) != Tile.EMPTY) {
                return  winner;
            }
            if ((winner = checkLine(curBoard.getDiag(i, 0, true))) != Tile.EMPTY) {
                return  winner;
            }
            if ((winner = checkLine(curBoard.getDiag(i, 0, false))) != Tile.EMPTY) {
                return  winner;
            }
        }
        // checks rest of diagonals
        for (int i = 1; i < curBoard.getSize(); i++) {
            if ((winner = checkLine(curBoard.getDiag(0, i, true))) != Tile.EMPTY) {
                return  winner;
            }
            if ((winner = checkLine(curBoard.getDiag(0, i, false))) != Tile.EMPTY) {
                return  winner;
            }
        }
        return Tile.EMPTY;
    }

    /**
     * Checks if the given line satisfies the winning condition.
     * @param line Line to check.
     * @return Symbol that satisfies the winning condition, EMPTY if the condition has not been met.
     */
    private char checkLine(Tile[] line) {

        int consecutive = 0;
        char previous = Tile.EMPTY;

        for (int i = 0; i < line.length; i++) {
            char value = line[i].getValue();

            if (value != previous) {
                consecutive = 1;
                previous = value;
            } else {
                consecutive++;
            }
            if (consecutive == winSize) {
                return value;
            }
        }
        return Tile.EMPTY;
    }

    /**
     * Prints the Game Board to stdout.
     */
    public void printBoard() {

        System.out.print(curBoard);
    }

    /**
     * Prints the current turn to stdout.
     */
    public void printTurn() {
        System.out.printf(Messages.TURN_COUNTER, turn);
    }

    /**
     * Prints prompt for player on turn to stdout.
     */
    public void printPrompt() {
        System.out.printf(Messages.TURN_PROMPT, players[(turn - 1) % players.length]);
    }

    /**
     * Prints game over message to stdout.
     */
    public void printOver() {
        System.out.printf(Messages.GAME_OVER, turn - 1);
    }

    /**
     * Adds curBoard to history and trims the history if longer than required.
     */
    private void historyAdd() {

        history.add(0, curBoard);

        if (history.size() == historySize) {
            history.remove(historySize - 1);
        }
    }

    @Override
    public void resolvePlayCommand(Command command) {

        int x = command.getValues()[0];
        int y = command.getValues()[1];
        Tile tile = curBoard.getTile(x, y);

        if (tile == null || !tile.isEmpty()) {

            Utils.error(Messages.ERROR_ILLEGAL_PLAY);
        }

        Board newBoard = curBoard.copy();
        historyAdd();

        curBoard = newBoard;
        tile = curBoard.getTile(x, y);
        tile.setValue(players[(turn - 1) % players.length]);
        turn++;
        return;
    }

    @Override
    public void resolveRewindCommand(Command command) {

        int rewindAmount = command.getValues()[0];

        if(rewindAmount >= historySize || rewindAmount < 0) {
            Utils.error(Messages.ERROR_REWIND);
        }

        while (rewindAmount > 1) {
            history.remove(0);
            rewindAmount--;
        }
        curBoard = history.remove(0);
        turn++;
        return;
    }

    @Override
    public void resolveQuitCommand() {
        quitRequested = true;
        return;
    }
}
