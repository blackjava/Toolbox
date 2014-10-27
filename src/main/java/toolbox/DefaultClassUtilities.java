package toolbox;

import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import toolbox.resource.CouldNotLookUpResourceException;
import toolbox.resource.CouldNotOpenResourceException;

/**
 * Default implementation of ClassUtilities.
 */
public class DefaultClassUtilities implements ClassUtilities {
    private static final String RESOURCE_PATH_SEPARATOR = "/";
    
    @Inject
    private ClassPathUtilities classPathUtilities;
    
    @Inject
    private UrlUtilities urlUtilities;

    @Override
    public List<Class> getClassesInPackage(String packageName) {
        List<Class> classes = new ArrayList<>();

        try {
            List<URL> resources = classPathUtilities.getResources(packageName.replaceAll("\\" + CLASS_PATH_SEPARATOR, RESOURCE_PATH_SEPARATOR));
            classes = getClasses(resources);
        } catch (CouldNotLookUpResourceException | NotLocalFileReferenceException ex) {
            // Could not find classes for package path
        }
        
        return classes;
    }

    private List<Class> getClasses(List<URL> packages) throws CouldNotLookUpResourceException, NotLocalFileReferenceException {
        List<Class> classes = new ArrayList<>();
        
        ClassProvider classProvider = new ClassProvider(getClassLoaderRootLocations());
        for (URL resource : packages) {
            try {
                List<URL> children = urlUtilities.getChildren(resource);
                for (URL child: children) {
                    try {
                        Path localPathReference = urlUtilities.getLocalPathReference(child);
                        classes.add(classProvider.getClass(localPathReference));
                    } catch (InvalidClassFileException ex) {
                        // Invalid class, skipping
                    }
                }
            } catch (NotLocalFileReferenceException | CouldNotOpenResourceException ex) {
                // Not local resource, or not readable, skipping
            }
        }
        
        return classes;
    }

    private List<Path> getClassLoaderRootLocations() throws CouldNotLookUpResourceException, NotLocalFileReferenceException {
        List<Path> rootLocations = new ArrayList<>();
                
        List<URL> resources = classPathUtilities.getResources("");
        for (URL resource : resources) {
            rootLocations.add(urlUtilities.getLocalPathReference(resource));
        }

        return rootLocations;
    }

    @Override
    public List<Class> getClassesInPackage(Class type, String packageName) {
        List<Class> classes = new ArrayList<>();
        
        List<Class> classesInPackage = getClassesInPackage(packageName);
        for (Class classInPackage : classesInPackage) {
            if (type.isAssignableFrom(classInPackage)) {
                classes.add(classInPackage);
            }
        }
        
        return classes;
    }
}
