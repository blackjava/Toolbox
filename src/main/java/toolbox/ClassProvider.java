package toolbox;

import static toolbox.ClassUtilities.CLASS_PATH_SEPARATOR;

import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;

/**
 * ClassProvider loads classes based on resources.
 */
public class ClassProvider {
    private static final String CLASS_FILE_EXTENSION = ".class";

    private final List<Path> classRootLocations;
    
    public ClassProvider(List<Path> classRootLocations) {
        this.classRootLocations = classRootLocations;
    }

    /**
     * Retrieve the class matching a path reference.
     * @param classFile path to the class resource
     * @return the matching class
     * @throws InvalidClassFileException if the specified file is not a java 
     * class
     */
    public Class getClass(Path classFile) throws InvalidClassFileException {
        try {
            if (isClassFile(classFile)) {
                for (Path root : classRootLocations) {
                    if (classFile.startsWith(root)) {
                        Path relativeClassFile = classFile.subpath(root.getNameCount(), classFile.getNameCount());
                        
                        String className = getClassName(relativeClassFile);
                        return Class.forName(className);
                    }
                }
            }
        } catch (ClassNotFoundException ex) {
            throw new InvalidClassFileException(classFile, ex);
        }
        
        throw new InvalidClassFileException(classFile);
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

    private boolean isClassFile(Path path) {
        return path.toString().toLowerCase().endsWith(CLASS_FILE_EXTENSION);
    }
}
