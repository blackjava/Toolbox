package toolbox;

import java.net.URL;
import java.util.List;

/**
 * ResourceUtilities provides functionality for looking up resources on the 
 * class path.
 */
public interface ResourceUtilities {
    /**
     * Retrieve the unique resource matching the given path.
     * 
     */
    URL getResource(String resourcePath) throws CouldNotLookUpResourceException, 
            ResourceNotFoundException, ResourceNotUniqueException;
    
    /**
     * Retrieve all resources matching the given path.
     * @throws toolbox.CouldNotLookUpResourceException if the classloader is unable to access resources
     */
    List<URL> getResources(String resourcePath) throws CouldNotLookUpResourceException;
}
