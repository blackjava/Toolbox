package toolbox.resource;

/**
 * ResourceException is an exception category indicating that something went
 * wrong while trying to use a resource. The type of the exception provides
 * additional information as to what went wrong.
 */
public abstract class ResourceException extends Exception {
    private final String resourcePath;
    
    protected ResourceException(String resourcePath) {
        super();
        this.resourcePath = resourcePath;
    }

    protected ResourceException(Throwable cause, String resourcePath) {
        super(cause);
        this.resourcePath = resourcePath;
    }
    
    /**
     * Retrieve the path of the resource.
     * @return the name of the resource
     */
    public String getResourcePath() {
        return resourcePath;
    }
}
