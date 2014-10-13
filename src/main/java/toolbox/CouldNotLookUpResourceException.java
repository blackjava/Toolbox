package toolbox;

import java.io.IOException;

public class CouldNotLookUpResourceException extends ResourceException {

    public CouldNotLookUpResourceException(IOException ioe, String resourcePath) {
        super(ioe, resourcePath);
    }
    
}
