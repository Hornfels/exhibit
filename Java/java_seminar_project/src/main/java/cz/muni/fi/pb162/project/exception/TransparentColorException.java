package cz.muni.fi.pb162.project.exception;

/**
 * Exception thrown when attempted to draw with the same color as the backround.
 *
 * @author Michal Krejcir
 */
public class TransparentColorException extends Exception {

    /**
     * Creates an TransparentColorException with the given error message.
     * @param errorMsg Error message.
     */
    public TransparentColorException(String errorMsg) {
        super(errorMsg);
    }

    /**
     * Creates an TransparentColorException with the given error message.
     * @param errorMsg Error message.
     * @param cause Cause of the exception.
     */
    public TransparentColorException(String errorMsg, Throwable cause) {
        super(errorMsg, cause);
    }
}
