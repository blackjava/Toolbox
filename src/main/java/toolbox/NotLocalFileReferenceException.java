package toolbox;

import java.net.URL;

/**
 * NotLocalFileReferenceException occurs when the application expected a 
 * reference to the local file system, but the reference points to an external
 * location.
 */
public class NotLocalFileReferenceException extends Exception {
    private final Object reference;
    
    public NotLocalFileReferenceException(URL url) {
        this.reference = url.toString();
    }
    
    public String getReference() {
        return reference.toString();
    }
}
