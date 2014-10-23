package toolbox;

import java.util.List;
import toolbox.resource.ResourceException;

/**
 * ClassUtilities provides functionality for looking up classes dynamically.
 */
public interface ClassUtilities {
    List<Class> getClassesInPackage(String packageName) throws ResourceException, NotLocalFileReferenceException;
}
