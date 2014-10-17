package toolbox.resource;

/**
 *
 */
public class CouldNotOpenResourceException extends ResourceException {

    public CouldNotOpenResourceException(Throwable cause, String resourcePath) {
        super(cause, resourcePath);
    }
    
}
