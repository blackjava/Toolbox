package toolbox;

import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import toolbox.resource.CouldNotLookUpResourceException;
import toolbox.resource.ResourceException;

/**
 * Default implementation of ClassUtilities.
 */
public class DefaultClassUtilities implements ClassUtilities {
    private static final String CLASS_FILE_EXTENSION = ".class";
    
    @Inject
    private ClassPathUtilities classPathUtilities;
    
    @Inject
    private UrlUtilities urlUtilities;

    @Override
    public List<Class> getClassesInPackage(String packageName) throws ResourceException, NotLocalFileReferenceException {
        List<Class> classes = new ArrayList<>();
        
        List<Path> rootLocations = getClassLoaderRootLocations();
        
        List<URL> resources = classPathUtilities.getResources(packageName.replaceAll("\\" + CLASS_PATH_SEPARATOR, RESOURCE_PATH_SEPARATOR));
        for (URL resource : resources) {
            List<URL> children = urlUtilities.getChildren(resource);
            for (URL child: children) {
                Path localPathReference = urlUtilities.getLocalPathReference(child);

                if (isClassFile(localPathReference)) {
                    for (Path root : rootLocations) {
                        if (localPathReference.startsWith(root)) {
                            Path classFile = localPathReference.subpath(root.getNameCount(), localPathReference.getNameCount());
                            
                            String result = getClassName(classFile);
                            
                            try {
                                classes.add(Class.forName(result.toString()));
                            } catch (ClassNotFoundException ex) {
                                // Skipping class
                            }
                        }
                    }
                    
                }
            }
        }
        
        return classes;
    }
    private static final String RESOURCE_PATH_SEPARATOR = "/";

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
    private static final String CLASS_PATH_SEPARATOR = ".";

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
