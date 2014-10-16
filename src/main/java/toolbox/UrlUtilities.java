package toolbox;

import java.net.URL;
import java.nio.file.Path;
import java.util.List;

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

    /**
     * Retrieve a list of resources contained in or referenced by the given 
     * resource. If the resource is a folder, a list of files in the folder is 
     * returned. If the resource is an archive, a list of all the files in the 
     * archive is returned.
     * @param url parent resource
     * @return a list of child resources
     * @throws toolbox.NotLocalFileReferenceException if the URL does not point 
     * to the local file system
     */
    List<URL> getChildren(URL url) throws NotLocalFileReferenceException;
}
