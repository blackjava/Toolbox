package toolbox;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.Test;
import static org.junit.Assert.*;

public class UrlUtilitiesTest {
    private final UrlUtilities urlUtilities = new DefaultUrlUtilities();

    @Test
    public void http_url_is_not_recognized_as_local_path_reference() throws MalformedURLException {
        URL url = new URL("http://www.w3c.org");
        assertFalse(urlUtilities.isLocalPathReference(url));
    }

    @Test
    public void relative_file_url_is_not_recognized_as_local_path_reference() throws MalformedURLException {
        URL url = new URL("file://file.txt");
        assertFalse(urlUtilities.isLocalPathReference(url));
    }
    
    @Test
    public void absolute_file_url_is_recognized_as_local_path_reference() throws MalformedURLException {
        URL url = new URL("file:///file.txt");
        assertTrue(urlUtilities.isLocalPathReference(url));
    }

    @Test
    public void jar_url_is_recognized_as_local_path_reference() throws MalformedURLException {
        URL url = new URL("jar:file:/file.jar!/file.txt");
        assertTrue(urlUtilities.isLocalPathReference(url));
    }
    
    @Test
    public void trying_to_retrueve_http_url_as_path_causes_exception() throws MalformedURLException {
        NotLocalFileReferenceException exception = null;
        
        try {
            URL url = new URL("http://www.w3c.org");
            urlUtilities.getLocalPathReference(url);
        } catch (NotLocalFileReferenceException nlfre) {
            exception = nlfre;
        }
        
        assertNotNull(exception);
        assertEquals("http://www.w3c.org", exception.getReference());
    }
    
    @Test
    public void absolute_file_url_is_retrieved_as_path() throws MalformedURLException, NotLocalFileReferenceException {
        URL url = new URL("file:///file name.txt");
        Path result = urlUtilities.getLocalPathReference(url);
        
        assertNotNull(result);
        assertEquals(Paths.get("/file name.txt"), result);
    }

    @Test
    public void jar_url_is_retrieved_as_path() throws MalformedURLException, NotLocalFileReferenceException {
        URL url = new URL("jar:file:/file name.jar!/file.txt");
        Path result = urlUtilities.getLocalPathReference(url);
        
        
        assertNotNull(result);
        assertEquals(Paths.get("/file name.jar"), result);
    }
}
