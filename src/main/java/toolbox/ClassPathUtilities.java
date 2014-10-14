package toolbox;

import java.net.URL;
import java.util.List;

/**
 * ClassPathUtilities provides functionality for looking up resources on the
 * class path.
 */
public interface ClassPathUtilities {
    /**
     * Retrieve the unique resource matching the given path.
     * 
     * @param resourcePath name of the resource to locate
     * @return a URL pointing to the resource
     * 
     * @throws CouldNotLookUpResourceException if unable to access the class path
     * @throws ResourceNotFoundException if no resources matched the conditions
     * @throws ResourceNotUniqueException is more than one resource matched the conditions
     */
    URL getResource(String resourcePath) throws CouldNotLookUpResourceException, 
            ResourceNotFoundException, ResourceNotUniqueException;
    
    /**
     * Retrieve all resources matching the given path.
     * 
     * @param resourcePath name of the resources to locate
     * @return a list of URLs pointing to the resources
     * 
     * @throws CouldNotLookUpResourceException if unable to access the class path
     */
    List<URL> getResources(String resourcePath) throws CouldNotLookUpResourceException;
}
