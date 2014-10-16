package toolbox;

import java.io.File;
import java.nio.file.Path;
import org.junit.Test;
import static org.junit.Assert.*;

public class SystemUtilitiesTest {
    
    @Test
    public void retrieve_temporary_directory() {
        SystemUtilities systemUtilities = new DefaultSystemUtilities();
        
        Path temporaryDirectoryPath = systemUtilities.getTemporaryDirectory();
        assertNotNull(temporaryDirectoryPath);
        
        File temporaryDirectory = temporaryDirectoryPath.toFile();
        assertTrue(temporaryDirectory.exists());
        assertTrue(temporaryDirectory.isDirectory());
        assertTrue(temporaryDirectory.canRead());
        assertTrue(temporaryDirectory.canWrite());
        assertTrue(temporaryDirectory.canExecute());
    }

}
