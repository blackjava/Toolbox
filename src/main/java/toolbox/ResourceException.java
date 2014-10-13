package toolbox;

public class ResourceException extends Exception {
    private final String resourcePath;
    
    protected ResourceException(String resourcePath) {
        super();
        this.resourcePath = resourcePath;
    }

    protected ResourceException(Throwable cause, String resourcePath) {
        super(cause);
        this.resourcePath = resourcePath;
    }
    
    public String getResourcePath() {
        return resourcePath;
    }
}
