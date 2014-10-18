package toolbox;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;
import toolbox.resource.CouldNotLookUpResourceException;
import toolbox.resource.CouldNotOpenResourceException;
import toolbox.resource.ResourceException;

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
    
    @Test
    public void retrieve_files_in_directory_resource() throws MalformedURLException, NotLocalFileReferenceException, CouldNotOpenResourceException {
        SystemUtilities systemUtilities = new DefaultSystemUtilities();
        Path temporaryDirectory = systemUtilities.getTemporaryDirectory();
        File[] files = temporaryDirectory.toFile().listFiles();
        
        URL url = temporaryDirectory.toUri().toURL();
        List<URL> children = urlUtilities.getChildren(url);
        
        assertEquals(files.length, children.size());
        
        for (int index = 0; index < files.length; index++) {
            Path childPath = urlUtilities.getLocalPathReference(children.get(index));
            assertEquals(files[index].getAbsolutePath(), childPath.toString());
        }
    }

    @Test
    public void retrieve_files_in_archive_resource() throws MalformedURLException, NotLocalFileReferenceException, ResourceException {
        ClassPathUtilities classPathUtilities = new DefaultClassPathUtilities();
        URL testArchive = classPathUtilities.getResource("testarchive.jar");
        
        List<URL> children = urlUtilities.getChildren(testArchive);
        
        assertEquals(11, children.size());
        assertEquals(testArchive.toString() + "!/Nytt Microsoft Excel Worksheet.xlsx", children.get(0).toString());
        assertEquals(testArchive.toString() + "!/Nytt punktgrafikkbilde.bmp", children.get(1).toString());
        assertEquals(testArchive.toString() + "!/Nytt Rich Text-vindu.rtf", children.get(2).toString());
        assertEquals(testArchive.toString() + "!/Nytt tekstdokument.txt", children.get(3).toString());
        assertEquals(testArchive.toString() + "!/Nytt WinRAR ZIP archive.zip", children.get(4).toString());
        assertEquals(testArchive.toString() + "!/Ny mappe/", children.get(5).toString());
        assertEquals(testArchive.toString() + "!/Ny mappe/Nytt Microsoft Excel Worksheet.xlsx", children.get(6).toString());
        assertEquals(testArchive.toString() + "!/Ny mappe/Nytt punktgrafikkbilde.bmp", children.get(7).toString());
        assertEquals(testArchive.toString() + "!/Ny mappe/Nytt Rich Text-vindu.rtf", children.get(8).toString());
        assertEquals(testArchive.toString() + "!/Ny mappe/Nytt tekstdokument.txt", children.get(9).toString());
        assertEquals(testArchive.toString() + "!/Ny mappe/Nytt WinRAR ZIP archive.zip", children.get(10).toString());
    }

    @Test
    public void retrieve_files_in_folder_without_trailing_slash_inside_archive_resource() throws MalformedURLException, NotLocalFileReferenceException, ResourceException {
        ClassPathUtilities classPathUtilities = new DefaultClassPathUtilities();
        URL testArchive = new URL(classPathUtilities.getResource("testarchive.jar") + "!/Ny mappe");
        
        List<URL> children = urlUtilities.getChildren(testArchive);
        
        assertEquals(5, children.size());
        assertEquals(testArchive.toString() + "/Nytt Microsoft Excel Worksheet.xlsx", children.get(0).toString());
        assertEquals(testArchive.toString() + "/Nytt punktgrafikkbilde.bmp", children.get(1).toString());
        assertEquals(testArchive.toString() + "/Nytt Rich Text-vindu.rtf", children.get(2).toString());
        assertEquals(testArchive.toString() + "/Nytt tekstdokument.txt", children.get(3).toString());
        assertEquals(testArchive.toString() + "/Nytt WinRAR ZIP archive.zip", children.get(4).toString());
    }
    
    @Test
    public void retrieve_files_in_folder_with_trailing_slash_inside_archive_resource() throws MalformedURLException, NotLocalFileReferenceException, ResourceException {
        ClassPathUtilities classPathUtilities = new DefaultClassPathUtilities();
        URL testArchive = new URL(classPathUtilities.getResource("testarchive.jar") + "!/Ny mappe/");
        
        List<URL> children = urlUtilities.getChildren(testArchive);
        
        assertEquals(5, children.size());
        assertEquals(testArchive.toString() + "Nytt Microsoft Excel Worksheet.xlsx", children.get(0).toString());
        assertEquals(testArchive.toString() + "Nytt punktgrafikkbilde.bmp", children.get(1).toString());
        assertEquals(testArchive.toString() + "Nytt Rich Text-vindu.rtf", children.get(2).toString());
        assertEquals(testArchive.toString() + "Nytt tekstdokument.txt", children.get(3).toString());
        assertEquals(testArchive.toString() + "Nytt WinRAR ZIP archive.zip", children.get(4).toString());
    }
}
