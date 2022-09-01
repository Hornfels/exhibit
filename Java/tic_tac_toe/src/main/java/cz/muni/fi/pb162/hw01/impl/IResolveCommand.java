package cz.muni.fi.pb162.hw01.impl;

/**
 * Interface for resolving Commands from the user.
 * @author Michal Krejčíř
 */
public interface IResolveCommand {

    /**
     * Resolves the Play command from the user.
     * @param command Play command to resolve.
     */
    void resolvePlayCommand(Command command);

    /**
     * Resolves the Rewind command from the user.
     * @param command Rewind command to resolve.
     */
    void resolveRewindCommand(Command command);

    /**
     * Resolves the Quit command from the user.
     */
    void resolveQuitCommand();
}
