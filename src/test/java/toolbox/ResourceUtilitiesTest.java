package toolbox;

import java.net.URL;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class ResourceUtilitiesTest {
    
    @Test
    public void getresources_on_nonexistent_resource_returns_empty_list() throws CouldNotLookUpResourceException {
        ResourceUtilities resourceUtilities = new DefaultResourceUtilities();
        List<URL> resources = resourceUtilities.getResources("nonexistentresource");
        
        assertEquals(0, resources.size());
    }

    @Test
    public void getresources_on_resource_returns_list_with_one_item() throws CouldNotLookUpResourceException {
        ResourceUtilities resourceUtilities = new DefaultResourceUtilities();
        List<URL> resources = resourceUtilities.getResources("toolbox/ResourceUtilitiesTest.class");
        
        assertEquals(1, resources.size());
        assertTrue(resources.get(0).toString().endsWith("/toolbox/ResourceUtilitiesTest.class"));
    }

    @Test
    public void getresources_on_nonunique_resource_returns_list_of_items() throws CouldNotLookUpResourceException {
        ResourceUtilities resourceUtilities = new DefaultResourceUtilities();
        List<URL> resources = resourceUtilities.getResources("");

        assertEquals(2, resources.size());
        assertTrue(resources.get(0).toString().endsWith("/test-classes/"));
        assertTrue(resources.get(1).toString().endsWith("/classes/"));
    }
    
    @Test
    public void getresource_on_nonexistent_resource_throws_exception() throws ResourceNotUniqueException, CouldNotLookUpResourceException {
        ResourceNotFoundException exception = null;

        try {
            ResourceUtilities resourceUtilities = new DefaultResourceUtilities();
            resourceUtilities.getResource("nonexistentresource");
        } catch (ResourceNotFoundException rnfe) {
            exception = rnfe;
        }
        
        assertNotNull(exception);
        assertEquals("nonexistentresource", exception.getResourcePath());
    }

    @Test
    public void getresource_on_resource_returns_item() throws CouldNotLookUpResourceException, ResourceNotFoundException, ResourceNotUniqueException {
        ResourceUtilities resourceUtilities = new DefaultResourceUtilities();
        URL resource = resourceUtilities.getResource("toolbox/ResourceUtilitiesTest.class");
        
        assertNotNull(resource);
        assertTrue(resource.toString().endsWith("/toolbox/ResourceUtilitiesTest.class"));
    }
    
    @Test
    public void getresource_on_nonunique_resource_throws_exception() throws ResourceNotFoundException, CouldNotLookUpResourceException {
        ResourceNotUniqueException exception = null;

        try {
            ResourceUtilities resourceUtilities = new DefaultResourceUtilities();
            resourceUtilities.getResource("");
        } catch (ResourceNotUniqueException rnue) {
            exception = rnue;
        }
        
        assertNotNull(exception);
        assertEquals("", exception.getResourcePath());
    }
}
