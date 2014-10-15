package toolbox;

import toolbox.resource.ResourceNotUniqueException;
import toolbox.resource.CouldNotLookUpResourceException;
import toolbox.resource.ResourceNotFoundException;
import java.net.URL;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class ClassPathUtilitiesTest {
    private final ClassPathUtilities resourceUtilities = new DefaultClassPathUtilities();
    
    @Test
    public void getresources_on_nonexistent_resource_returns_empty_list() throws CouldNotLookUpResourceException {
        List<URL> resources = resourceUtilities.getResources("nonexistentresource");
        
        assertEquals(0, resources.size());
    }

    @Test
    public void getresources_on_resource_returns_list_with_one_item() throws CouldNotLookUpResourceException {
        List<URL> resources = resourceUtilities.getResources("toolbox/ClassPathUtilitiesTest.class");
        
        assertEquals(1, resources.size());
        assertTrue(resources.get(0).toString().endsWith("/toolbox/ClassPathUtilitiesTest.class"));
    }

    @Test
    public void getresources_on_nonunique_resource_returns_list_of_items() throws CouldNotLookUpResourceException {
        List<URL> resources = resourceUtilities.getResources("");

        assertEquals(2, resources.size());
        assertTrue(resources.get(0).toString().endsWith("/test-classes/"));
        assertTrue(resources.get(1).toString().endsWith("/classes/"));
    }
    
    @Test
    public void getresource_on_nonexistent_resource_throws_exception() throws ResourceNotUniqueException, CouldNotLookUpResourceException {
        ResourceNotFoundException exception = null;

        try {
            resourceUtilities.getResource("nonexistentresource");
        } catch (ResourceNotFoundException rnfe) {
            exception = rnfe;
        }
        
        assertNotNull(exception);
        assertEquals("nonexistentresource", exception.getResourcePath());
    }

    @Test
    public void getresource_on_resource_returns_item() throws CouldNotLookUpResourceException, ResourceNotFoundException, ResourceNotUniqueException {
        URL resource = resourceUtilities.getResource("toolbox/ClassPathUtilitiesTest.class");
        
        assertNotNull(resource);
        assertTrue(resource.toString().endsWith("/toolbox/ClassPathUtilitiesTest.class"));
    }
    
    @Test
    public void getresource_on_nonunique_resource_throws_exception() throws ResourceNotFoundException, CouldNotLookUpResourceException {
        ResourceNotUniqueException exception = null;

        try {
            resourceUtilities.getResource("");
        } catch (ResourceNotUniqueException rnue) {
            exception = rnue;
        }
        
        assertNotNull(exception);
        assertEquals("", exception.getResourcePath());
    }
}
