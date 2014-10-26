package toolbox;

import java.nio.file.Path;

/**
 * InvalidClassFileException occurs when the application tries to reference a 
 * file as a class, and that is file is not a valid java class.
 */
public class InvalidClassFileException extends Exception {
    private final Path fileName;
    
    public InvalidClassFileException(Path fileName) {
        super();
        this.fileName = fileName;
    }

    public InvalidClassFileException(Path fileName, Throwable cause) {
        super(cause);
        this.fileName = fileName;
    }
}
