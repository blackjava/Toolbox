package toolbox;

import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;
import toolbox.resource.CouldNotLookUpResourceException;
import toolbox.resource.CouldNotOpenResourceException;

/**
 * Default implementation of ClassUtilities.
 */
public class DefaultClassUtilities implements ClassUtilities {
    private static final String CLASS_FILE_EXTENSION = ".class";
    private static final String CLASS_PATH_SEPARATOR = ".";
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
        } catch (CouldNotLookUpResourceException ex) {
            // Could not find classes for package path
        }
        
        return classes;
    }

    private List<Class> getClasses(List<URL> packages) {
        List<Class> classes = new ArrayList<>();
        
        for (URL resource : packages) {
            try {
                List<URL> children = urlUtilities.getChildren(resource);
                for (URL child: children) {
                    try {
                        classes.add(getClass(child));
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

    private Class getClass(URL classLocation) throws InvalidClassFileException {
        try {
            List<Path> rootLocations = getClassLoaderRootLocations();
            Path localPathReference = urlUtilities.getLocalPathReference(classLocation);
            
            if (isClassFile(localPathReference)) {
                for (Path root : rootLocations) {
                    if (localPathReference.startsWith(root)) {
                        Path classFile = localPathReference.subpath(root.getNameCount(), localPathReference.getNameCount());
                        
                        String className = getClassName(classFile);
                        return Class.forName(className);
                    }
                }
            }
        } catch (NotLocalFileReferenceException | ClassNotFoundException | CouldNotLookUpResourceException ex) {
            throw new InvalidClassFileException(classLocation, ex);
        }
        
        throw new InvalidClassFileException(classLocation);
    }

    private String getClassName(Path classFile) {
        StringBuilder result = new StringBuilder();

        Iterator<Path> iterator = classFile.iterator();
        while (iterator.hasNext()) {
            String token = iterator.next().toString();
            
            if (token.endsWith(CLASS_FILE_EXTENSION)) {
                token = token.substring(0, token.length() - CLASS_FILE_EXTENSION.length());
            }
            
            result.append(token);
            
            if (iterator.hasNext()) {
                result.append(CLASS_PATH_SEPARATOR);
            }
        }
        
        return result.toString();
    }

    private List<Path> getClassLoaderRootLocations() throws CouldNotLookUpResourceException, NotLocalFileReferenceException {
        List<Path> rootLocations = new ArrayList<>();
                
        List<URL> resources = classPathUtilities.getResources("");
        for (URL resource : resources) {
            rootLocations.add(urlUtilities.getLocalPathReference(resource));
        }

        return rootLocations;
    }

    private boolean isClassFile(Path path) {
        return path.toString().toLowerCase().endsWith(CLASS_FILE_EXTENSION);
    }
}
