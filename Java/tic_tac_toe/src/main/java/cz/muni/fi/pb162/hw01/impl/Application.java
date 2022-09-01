package cz.muni.fi.pb162.hw01.impl;

import com.beust.jcommander.Parameter;
import cz.muni.fi.pb162.hw01.Utils;
import cz.muni.fi.pb162.hw01.cmd.CommandLine;
import cz.muni.fi.pb162.hw01.cmd.Messages;

/**
 * Application class represents the command line interface of this application.
 *
 * @author jcechace
 */
public class Application {
    @Parameter(names = { "--size", "-s" })
    private int size = 3;

    // Implement additional command line flags

    @Parameter(names = {"--win", "-w"})
    private int win = 3;

    @Parameter(names = {"--history", "-h"})
    private int history = 1;

    @Parameter(names = {"--players", "-p"})
    // jde to takhle inicializovat?
    private String players = "xo";

    @Parameter(names = "--help", help = true)
    private boolean showUsage = false;

    /**
     * Application entry point
     *
     * @param args command line arguments of the application
     */
    public static void main(String[] args) {
        Application app = new Application();

        CommandLine cli = new CommandLine(app);
        cli.parseArguments(args);

        if (app.showUsage) {
            cli.showUsage();
        } else {
            app.run();
        }
    }

    private void validateArguments() {
        if (size < 3) {
            System.err.println("Error: size must be at least 3.");
            System.exit(2);
        }
        if (win < 3 || win > size) {
            System.err.println("Error: win must be at least 3 and not bigger or equal to size.");
            System.exit(2);
        }
        if (history < 1 || history >= size*size) {
            System.err.println("Error: history must be at least 1 and not bigger than size^2.");
            System.exit(2);
        }
        if (players.length() <= 1) {
            System.err.println("Error: at least one player required.");
            System.exit(2);
        }
    }

    /**
     * Application runtime logic
     */
    private void run() {

        validateArguments();
        Game game = new Game(size, history, players, win);

        String isOver = null;
        String playerInput;

        while (isOver == null) {

            game.printTurn();
            game.printBoard();
            game.printPrompt();

            playerInput = Utils.readLineFromStdIn();

            Command playerAction = new Command(playerInput);

            try {
                switch (playerAction.getType()) {
                    case PLAY:
                        game.resolvePlayCommand(playerAction);
                        break;
                    case REWIND:
                        game.resolveRewindCommand(playerAction);
                        break;
                    case QUIT:
                        game.resolveQuitCommand();
                        break;
                    default:
                        System.out.println(Messages.ERROR_INVALID_COMMAND);
                        break;
                }

                isOver = game.isOver();

            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
        game.printOver();
        game.printBoard();

        if(isOver.length() == 1) {
            System.out.printf(Messages.GAME_WINNER, isOver);
        }
        return;
    }
}
