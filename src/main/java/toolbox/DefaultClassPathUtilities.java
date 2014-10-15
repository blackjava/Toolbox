package toolbox;

import toolbox.resource.CouldNotLookUpResourceException;
import toolbox.resource.ResourceNotFoundException;
import toolbox.resource.ResourceNotUniqueException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Default implementation of ClassPathUtilities.
 */
public class DefaultClassPathUtilities implements ClassPathUtilities {
    
    @Override
    public URL getResource(String resourcePath) throws 
            CouldNotLookUpResourceException, ResourceNotFoundException, ResourceNotUniqueException {
        List<URL> resources = getResources(resourcePath);
        
        if (resources.isEmpty()) {
            throw new ResourceNotFoundException(resourcePath);
        } else if (resources.size() > 1) {
            throw new ResourceNotUniqueException(resourcePath);
        } else {
            return resources.get(0);
        }
    }

    @Override
    public List<URL> getResources(String resourcePath) throws CouldNotLookUpResourceException {
        List<URL> resources = new ArrayList<>();
        
        try {
            Enumeration<URL> classLoaderResources = getClassLoader().getResources(resourcePath);
            while (classLoaderResources.hasMoreElements()) {
                resources.add(classLoaderResources.nextElement());
            }
        } catch (IOException ioe) {
            throw new CouldNotLookUpResourceException(ioe, resourcePath);
        }
        
        return resources;
    }

    private ClassLoader getClassLoader() {
        return this.getClass().getClassLoader();
    }
    
}
