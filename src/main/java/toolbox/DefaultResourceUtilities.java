package toolbox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Default implementation of ResourceUtilities.
 */
public class DefaultResourceUtilities implements ResourceUtilities {
    
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
            Enumeration<URL> classLoaderResources = this.getClass().getClassLoader().getResources(resourcePath);
            while (classLoaderResources.hasMoreElements()) {
                resources.add(classLoaderResources.nextElement());
            }
        } catch (IOException ioe) {
            throw new CouldNotLookUpResourceException(ioe, resourcePath);
        }
        
        return resources;
    }
    
}
