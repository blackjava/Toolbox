package toolbox;

import java.net.URL;
import java.nio.file.Path;

/**
 * UrlUtilities provides functionality for working with URLs.
 */
public interface UrlUtilities {
    final String PROTOCOL_JAR = "jar";
    final String PROTOCOL_FILE = "file";
    
    /**
     * Determine whether or not a URL points to a resource on the local file 
     * system.
     * @param url the URL of the resource
     * @return true if the URL points to the local file system, false otherwise
     */
    boolean isLocalPathReference(URL url);
    
    /**
     * Retrieve a reference to the local file system based on a URL. If the URL
     * points to a file inside an archive, the path to the archive will be 
     * returned.
     * @param url the URL of the resource
     * @return a path pointing to the resource
     * @throws toolbox.NotLocalFileReferenceException if the URL does not point 
     * to the local file system
     */
    Path getLocalPathReference(URL url) throws NotLocalFileReferenceException;

}
