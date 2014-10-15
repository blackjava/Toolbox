package toolbox.resource;

/**
 * ResourceNotUniqueException occurs when the application expected to find a
 * specific resource, but multiple resources in the system were found matching
 * the given condition.
 */
public class ResourceNotUniqueException extends ResourceException {

    public ResourceNotUniqueException(String resourcePath) {
        super(resourcePath);
    }
    
}
