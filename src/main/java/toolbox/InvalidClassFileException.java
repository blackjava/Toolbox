package toolbox;

import java.net.URL;

/**
 * InvalidClassFileException occurs when the application tries to reference a 
 * file as a class, and that is file is not a valid java class.
 */
public class InvalidClassFileException extends Exception {
    private final URL fileName;
    
    public InvalidClassFileException(URL fileName) {
        super();
        this.fileName = fileName;
    }

    public InvalidClassFileException(URL fileName, Throwable cause) {
        super(cause);
        this.fileName = fileName;
    }
}
