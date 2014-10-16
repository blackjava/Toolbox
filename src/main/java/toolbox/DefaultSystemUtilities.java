package toolbox;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Default implementation of SystemUtilities.
 */
public class DefaultSystemUtilities implements SystemUtilities {

    @Override
    public Path getTemporaryDirectory() {
        String tmpdir = System.getProperty("java.io.tmpdir");
        return Paths.get(tmpdir);
    }
    
}
