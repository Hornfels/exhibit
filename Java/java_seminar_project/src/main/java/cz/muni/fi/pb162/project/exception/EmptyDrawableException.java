package cz.muni.fi.pb162.project.exception;

/**
 * Exception thrown when there is nothing painted on the drawing object.
 *
 * @author Michal Krejcir
 */
public class EmptyDrawableException extends Exception {

    /**
     * Creates an EmptyDrawableException with the given error message.
     * @param errorMsg Error message.
     */
    public EmptyDrawableException(String errorMsg) {
        super(errorMsg);
    }

    /**
     * Creates an EmptyDrawableException with the given error message.
     * @param errorMsg Error message.
     * @param cause Cause of the exception.
     */
    public EmptyDrawableException(String errorMsg, Throwable cause) {
        super(errorMsg, cause);
    }
}
