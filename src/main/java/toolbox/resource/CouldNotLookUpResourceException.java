package toolbox.resource;

import java.io.IOException;

/**
 * CouldNotLookUpResourceException indicates that an error occured while trying
 * to resolve a resource against the system. This error normally occurs if 
 * accessing the system failed, for instance due to privilege restrictions or
 * memory issues.
 */
public class CouldNotLookUpResourceException extends ResourceException {

    public CouldNotLookUpResourceException(IOException ioe, String resourcePath) {
        super(ioe, resourcePath);
    }
    
}
