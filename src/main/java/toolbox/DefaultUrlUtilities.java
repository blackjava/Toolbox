package toolbox;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Default implementation of UrlUtilities.
 */
public class DefaultUrlUtilities implements UrlUtilities {

    @Override
    public boolean isLocalPathReference(URL url) {
        boolean isLocalPathReference = false;
        
        if (pointsToFile(url) || pointsToArchive(url)) {
            isLocalPathReference = true;
        }
        
        return isLocalPathReference;
    }

    private static boolean pointsToArchive(URL url) {
        return PROTOCOL_JAR.equalsIgnoreCase(url.getProtocol());
    }

    private static boolean pointsToFile(URL url) {
        return (PROTOCOL_FILE.equalsIgnoreCase(url.getProtocol()) 
                    && (!url.getPath().isEmpty()));
    }

    @Override
    public Path getLocalPathReference(URL url) throws NotLocalFileReferenceException {
        if (!isLocalPathReference(url)) {
            throw new NotLocalFileReferenceException(url);
        }
        
        String path = url.getPath();
        path = removeProtocolPrefix(path);
        path = removeArchiveSubpath(path);
        path = removePathSeparatorBeforeDriveLetterAssignment(path);
        path = decodeUrlCharacters(path);
        
        return Paths.get(path);
    }

    private String decodeUrlCharacters(String url) {
        String result = url;
        
        result = result.replaceAll("%20", " ");
        result = result.replaceAll("%7B", "{");
        result = result.replaceAll("%7D", "}");
        
        return result;
    }

    private String removePathSeparatorBeforeDriveLetterAssignment(String path) {
        String result = path;
        
        if ((result.length() > 2) && (':' == result.charAt(2))) {
            result = result.substring(1);
        }
        
        return result;
    }

    private String removeArchiveSubpath(String path) {
        String result = path;
        
        if (result.indexOf("!/") > 0) {
            result = result.substring(0, result.lastIndexOf("!/"));
        }
        
        return result;
    }

    private String removeProtocolPrefix(String path) {
        String result = path;
        
        if (result.startsWith(PROTOCOL_FILE + ":")) {
            result = result.substring(PROTOCOL_FILE.length() + 1);
        }
        
        return result;
    }

    @Override
    public List<URL> getChildren(URL url) throws NotLocalFileReferenceException {
        List<URL> children = new ArrayList<>();
        
        Path localPath = getLocalPathReference(url);
        for (File child : localPath.toFile().listFiles()) {
            try {
                children.add(child.toURI().toURL());
            } catch (MalformedURLException ex) {
                // Could not create URL for file, skipping
            }
        }
        
        return children;
    }
    
}
