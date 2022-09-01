package cz.muni.fi.pb162.project.exception;

/**
 * Exception thrown when there are not enough vertices in the collection.
 *
 * @author Michal Krejcir
 */
public class MissingVerticesException extends RuntimeException {

    /**
     * Creates an MissingVerticesException with the given error message.
     * @param errorMsg Error message.
     */
    public MissingVerticesException(String errorMsg) {
        super(errorMsg);
    }

    /**
     * Creates an MissingVerticesException with the given error message.
     * @param errorMsg Error message.
     * @param cause Cause of the exception.
     */
    public MissingVerticesException(String errorMsg, Throwable cause) {
        super(errorMsg, cause);
    }
}
