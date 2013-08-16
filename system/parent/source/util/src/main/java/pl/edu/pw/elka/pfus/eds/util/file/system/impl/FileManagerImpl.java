package pl.edu.pw.elka.pfus.eds.util.file.system.impl;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Joiner;
import org.apache.log4j.Logger;
import pl.edu.pw.elka.pfus.eds.util.StringHelper;
import pl.edu.pw.elka.pfus.eds.util.config.Config;
import pl.edu.pw.elka.pfus.eds.util.config.impl.DefaultClassLoaderPropertiesConfig;
import pl.edu.pw.elka.pfus.eds.util.file.system.FileManager;
import pl.edu.pw.elka.pfus.eds.util.file.system.PathCreator;
import pl.edu.pw.elka.pfus.eds.util.file.system.PathHelper;
import pl.edu.pw.elka.pfus.eds.util.file.system.exception.FileSystemException;
import pl.edu.pw.elka.pfus.eds.util.hash.ByteArrayHasher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FileManagerImpl implements FileManager {
    private static final Logger logger = Logger.getLogger(FileManagerImpl.class);
    private Config config;
    private ByteArrayHasher hasher;
    private PathCreator pathCreator;
    public final int PART_LENGTH;

    public FileManagerImpl(ByteArrayHasher hasher, PathCreator pathCreator) {
        this(new DefaultClassLoaderPropertiesConfig(), hasher, pathCreator);
    }

    public FileManagerImpl(Config config, ByteArrayHasher hasher, PathCreator pathCreator) {
        this.config = config;
        this.hasher = hasher;
        this.pathCreator = pathCreator;
        PART_LENGTH = config.getInt("part_length");
    }

    @Override
    public File create(byte[] input, String fileName) {
        String hash = hasher.getString(input);
        logger.info("trying to create file: " + fileName + ", with hash: " + hash);
        pathCreator.createPath(getFileSystemRoot(), splitHash(hash));
        return createFileFromByteArray(input, getFullPath(hash, fileName));
    }

    @Override
    public void delete(String name, String hash) {
        String fullPath = getFullPath(hash, name);
        logger.info("attempting to delete file: " + fullPath);
        File file = new File(fullPath);
        if(file.exists() && file.isFile()) {
            if (!file.delete()) {
                logger.warn("file hasn't been deleted, hash: " + hash + ", name: " + name);
            }
        } else {
            logger.warn("file not exists or isn't a file: " + fullPath);
        }
    }

    private File createFileFromByteArray(byte[] input, String fullPath) {
        File file = createEmptyFile(fullPath);
        try(OutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(input);
            return file;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new FileSystemException("Błąd przy tworzeniu pliku", e);
        }
    }

    private String getFullPath(String hash, String fileName) {
        String fileSystemRoot = StringHelper.decorateWithLeadingSlash(getFileSystemRoot());
        String hashedPath = StringHelper.decorateWithLeadingSlash(getHashedPath(hash));
        return fileSystemRoot + hashedPath + fileName;
    }

    private String getFileSystemRoot() {
        String fileSystemRoot = config.getString("file_system_root");
        return PathHelper.countFileSystemRoot(fileSystemRoot);
    }

    @VisibleForTesting
    String getHashedPath(String hash) {
        String[] splittedHash = splitHash(hash);
        return Joiner.on(File.separator).join(splittedHash);
    }

    @VisibleForTesting
    String[] splitHash(String hash) {
        int partNumber = getPartNumber(hash, PART_LENGTH);
        validatePartNumber(hash, partNumber);
        String[] parts = new String[partNumber];
        for(int partPosition = 0, partIndex = 0; partIndex < partNumber; partPosition+=PART_LENGTH, partIndex++) {
            parts[partIndex] = hash.substring(partPosition, partPosition + PART_LENGTH);
        }
        return parts;
    }

    @VisibleForTesting
    int getPartNumber(String hash, int partLength) {
        return (int) Math.ceil((double)hash.length() / partLength);
    }

    @VisibleForTesting
    void validatePartNumber(String hash, int partNumber) {
        if(hash.length() % partNumber != 0)
            throw new StringIndexOutOfBoundsException("Długość hasha musi być wielokrotnością długości części (part_length)");
    }

    private File createEmptyFile(String path) {
        logger.info("creating file: " + path);
        File file = new File(path);
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return file;
    }
}
