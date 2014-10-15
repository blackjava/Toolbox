package toolbox;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Default implementation of UrlUtilities.
 */
public class DefaultUrlUtilities implements UrlUtilities {

    @Override
    public boolean isLocalPathReference(URL url) {
        boolean isLocalPathReference = false;
        
        if (pointsToFile(url) || pointsToArchive(url)) {
            isLocalPathReference = true;
        }
        
        return isLocalPathReference;
    }

    private static boolean pointsToArchive(URL url) {
        return PROTOCOL_JAR.equalsIgnoreCase(url.getProtocol());
    }

    private static boolean pointsToFile(URL url) {
        return (PROTOCOL_FILE.equalsIgnoreCase(url.getProtocol()) 
                    && (!url.getPath().isEmpty()));
    }

    @Override
    public Path getLocalPathReference(URL url) throws NotLocalFileReferenceException {
        if (!isLocalPathReference(url)) {
            throw new NotLocalFileReferenceException(url);
        }
        
        String path = url.getPath();
        if (path.startsWith(PROTOCOL_FILE + ":")) {
            path = path.substring(PROTOCOL_FILE.length() + 1);
        }
        
        if (path.indexOf("!/") > 0) {
            path = path.substring(0, path.lastIndexOf("!/"));
        }
        
        return Paths.get(path);
    }
    
}
