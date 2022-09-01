package cz.muni.fi.pb162.hw01.impl;

/**
 * Class representing a parsed command from the user of the tic-tac-toe app.
 * @author Michal Krejčíř
 */
public class Command {

    private final String rawCommand;
    private CommandTypes type;
    // basically a union
    private int[] values = new int[2];

    /**
     * Creates Command from raw command String.
     * @param rawCommand String containing the command.
     */
    public Command(String rawCommand) {

        this.rawCommand = rawCommand;
        parse();
    }

    /**
     * Parses the raw command String.
     */
    private void parse() {

        if (rawCommand.equals(":q")) {
            type = CommandTypes.QUIT;
            return;
        }
        if (rawCommand.matches("<<[1-9]+$")) {
            type = CommandTypes.REWIND;
            values[0] = Integer.parseInt(rawCommand.substring(2));
            return;
        }
        if(rawCommand.matches("^[0-9]+ [0-9]+$")) {
            String[] tokens = rawCommand.split(" ");
            type = CommandTypes.PLAY;
            values[0] = Integer.parseInt(tokens[0]);
            values[1] = Integer.parseInt(tokens[1]);
            return;
        }
        type = CommandTypes.INVALID;
        return;
    }

    public CommandTypes getType() {
        return type;
    }

    public int[] getValues() {
        return values;
    }
}
