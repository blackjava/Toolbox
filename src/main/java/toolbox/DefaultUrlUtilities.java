package toolbox;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import toolbox.resource.CouldNotOpenResourceException;

/**
 * Default implementation of UrlUtilities.
 */
public class DefaultUrlUtilities implements UrlUtilities {
    private static final String ARCHIVE_CONTENT_SEPARATOR = "!/";
    private static final String PATH_SEPARATOR = "/";

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
        
        if (result.indexOf(ARCHIVE_CONTENT_SEPARATOR) > 0) {
            result = result.substring(0, result.lastIndexOf(ARCHIVE_CONTENT_SEPARATOR));
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
    public List<URL> getChildren(URL resource) throws NotLocalFileReferenceException, CouldNotOpenResourceException {
        List<URL> children;
        
        File localResource = getLocalPathReference(resource).toFile();
        
        if (localResource.isDirectory()) {
            children = getFilesInDirectory(localResource);
        } else if (pointsToFileInsideArchive(resource)) {
            children = new ArrayList<>();
            
            String resourcePath = toString(resource);
            List<URL> archiveContent = getFilesInArchive(localResource);
            for (URL archivedFile : archiveContent) {
                if (isChild(resourcePath, archivedFile.toString())) {
                    children.add(archivedFile);
                }
            }
        } else {
            children = getFilesInArchive(localResource);
        }
        
        return children;
    }

    private static boolean pointsToFileInsideArchive(URL resource) {
        return resource.toString().contains(ARCHIVE_CONTENT_SEPARATOR);
    }

    private static boolean isChild(String parentPath, String filePath2) {
        String filePath = filePath2;
        if (filePath.startsWith(ARCHIVE_CONTENT_SEPARATOR)) {
            filePath = filePath.substring(2);
        }
        
        if (filePath.endsWith(PATH_SEPARATOR)) {
            filePath = filePath.substring(0, filePath.length() - 1);
        }
        
        return filePath.startsWith(parentPath) 
                && (filePath.length() > parentPath.length())
                && (filePath.indexOf(PATH_SEPARATOR, parentPath.length()) == -1);
    }

    private String toString(URL url) {
        String urlPath = url.toString();
        
        if (urlPath.contains(ARCHIVE_CONTENT_SEPARATOR) && !urlPath.endsWith(PATH_SEPARATOR)) {
            urlPath += PATH_SEPARATOR;
        }
        
        return urlPath;
    }

    private List<URL> getFilesInArchive(File localResource) throws CouldNotOpenResourceException {
        List<URL> children = new ArrayList<>();
        
        try {
            String urlPrefix = localResource.toURI().toURL().toString();
            
            ZipFile archive = new ZipFile(localResource);
            Enumeration<? extends ZipEntry> entries = archive.entries();
            while (entries.hasMoreElements()) {
                ZipEntry nextElement = entries.nextElement();
                String name = nextElement.getName();
                children.add(new URL(urlPrefix + ARCHIVE_CONTENT_SEPARATOR + name));
            }
        } catch (IOException ioe) {
            throw new CouldNotOpenResourceException(ioe, localResource.getPath());
        }
        
        return children;
    }

    private List<URL> getFilesInDirectory(File localResource) {
        List<URL> children = new ArrayList<>();
        
        for (File child : localResource.listFiles()) {
            try {
                children.add(child.toURI().toURL());
            } catch (MalformedURLException ex) {
                // Could not create URL for file, skipping
            }
        }
        
        return children;
    }
    
}
