package toolbox;

import java.nio.file.Path;

/**
 * SystemUtilities provides functionality for accessing system properties.
 */
public interface SystemUtilities {
    
    /**
     * Retrieve the applications temporary directory.
     * @return a Path reference to the temporary directory
     */
    Path getTemporaryDirectory();
    
}
