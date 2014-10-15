package toolbox.resource;

/**
 * ResourceNotFoundException occurs when the application expected to find a 
 * specific resource, but the resource could not be located in the system.
 */
public class ResourceNotFoundException extends ResourceException {

    public ResourceNotFoundException(String resourcePath) {
        super(resourcePath);
    }
    
}
