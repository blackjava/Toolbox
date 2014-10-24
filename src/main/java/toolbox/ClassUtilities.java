package toolbox;

import java.util.List;

/**
 * ClassUtilities provides functionality for looking up classes dynamically.
 */
public interface ClassUtilities {
    /**
     * Retrieve all classes found within a package.
     * 
     * @param packageName complete path of package name separated by '.' or '/'
     * @return all classes found in the package
     */
    List<Class> getClassesInPackage(String packageName);
}
