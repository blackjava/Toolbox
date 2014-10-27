package toolbox;

import java.util.List;

/**
 * ClassUtilities provides functionality for looking up classes dynamically.
 */
public interface ClassUtilities {
    String CLASS_PATH_SEPARATOR = ".";

    /**
     * Retrieve all classes found within a package.
     * 
     * @param packageName complete path of package name separated by '.' or '/'
     * @return all classes found in the package
     */
    List<Class> getClassesInPackage(String packageName);

    /**
     * Retrieve all classes of a given type found within a package. Returns
     * any class that implements or extends the specified type.
     * 
     * @param type the type of class to search for
     * @param packageName complete path of package name separated by '.' or '/'
     * @return all classes found in the package
     */
    List<Class> getClassesInPackage(Class type, String packageName);
}
