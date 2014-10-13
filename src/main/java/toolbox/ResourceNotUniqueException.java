package toolbox;

import toolbox.ResourceException;

public class ResourceNotUniqueException extends ResourceException {
    public ResourceNotUniqueException(String resourcePath) {
        super(resourcePath);
    }
}
