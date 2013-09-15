package pl.edu.pw.elka.pfus.eds.util.file.system.impl;

import com.google.common.annotations.VisibleForTesting;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import pl.edu.pw.elka.pfus.eds.util.StringHelper;
import pl.edu.pw.elka.pfus.eds.util.config.Config;
import pl.edu.pw.elka.pfus.eds.util.file.system.PathCreator;
import pl.edu.pw.elka.pfus.eds.util.file.system.PathHelper;
import pl.edu.pw.elka.pfus.eds.util.file.system.exception.FileSystemException;

import java.io.File;
import java.io.IOException;

public class PathCreatorImpl implements PathCreator {
    private static final Logger logger = Logger.getLogger(PathCreatorImpl.class);
    private Config config;

    public PathCreatorImpl(Config config) {
        this.config = config;
    }

    @Override
    public void createNecessaryDirectories() {
        deleteDirectory(PathHelper.countFileSystemRoot(config.getString("file_system_root")));
        createDirectoryFromConfig("file_system_root");
        createDirectoryFromConfig("index_dir");
    }

    private void createDirectoryFromConfig(String configKey) {
        String fileSystemRoot = config.getString(configKey);
        fileSystemRoot = PathHelper.countFileSystemRoot(fileSystemRoot);
        createIfNotExists(fileSystemRoot);
    }

    @Override
    public void createPath(String rootPath, String[] hashedPath) {
        StringBuilder currentPath = new StringBuilder(StringHelper.decorateWithLeadingSlash(rootPath));
        for(String dirName : hashedPath) {
            createIfNotExists(currentPath.toString(), dirName);
            currentPath.append(StringHelper.decorateWithLeadingSlash(dirName));
        }
    }

    @VisibleForTesting
    void createIfNotExists(String path) {
        File dirFile = new File(path);
        if(!dirFile.exists()) {
            if(!dirFile.mkdir())
                throw new FileSystemException("Błąd przy tworzeniu katalogu: " + path);
            logger.info("created directory: " + path);
        }
    }

    @VisibleForTesting
    void createIfNotExists(String path, String dirName) {
        File rootPath = new File(path);
        if(!rootPath.exists() || !rootPath.isDirectory())
            throw new FileSystemException("Ścieżka nie istnieje lub nie jest katalogiem: " + path);

        String dirPath = StringHelper.decorateWithLeadingSlash(path) + dirName;
        createIfNotExists(dirPath);
    }

    private void deleteDirectory(String path) {
        File file = new File(path);
        try {
            if(file.exists())
                FileUtils.deleteDirectory(file);
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }
}
