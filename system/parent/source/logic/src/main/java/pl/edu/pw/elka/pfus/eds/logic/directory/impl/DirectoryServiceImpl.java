package pl.edu.pw.elka.pfus.eds.logic.directory.impl;

import pl.edu.pw.elka.pfus.eds.domain.entity.Directory;
import pl.edu.pw.elka.pfus.eds.domain.entity.FileSystemEntry;
import pl.edu.pw.elka.pfus.eds.logic.directory.DirectoryFinder;
import pl.edu.pw.elka.pfus.eds.logic.directory.DirectoryModifier;
import pl.edu.pw.elka.pfus.eds.logic.directory.DirectoryService;

import java.util.List;

public class DirectoryServiceImpl implements DirectoryService {
    private DirectoryFinder directoryFinder;
    private DirectoryModifier directoryModifier;

    public DirectoryServiceImpl(DirectoryFinder directoryFinder, DirectoryModifier directoryModifier) {
        this.directoryFinder = directoryFinder;
        this.directoryModifier = directoryModifier;
    }

    @Override
    public List<FileSystemEntry> getRootDirectoryAndSessionDocuments() {
        return directoryFinder.getRootDirectoryAndSessionDocuments();
    }

    @Override
    public List<Directory> getSubdirectories(Directory directory) {
        return directoryFinder.getSubdirectories(directory);
    }

    @Override
    public List<Directory> getSubdirectories(int directoryId) {
        return directoryFinder.getSubdirectories(directoryId);
    }

    @Override
    public List<FileSystemEntry> getFileSystemEntries(int directoryId) {
        return directoryFinder.getFileSystemEntries(directoryId);
    }

    @Override
    public Directory getById(int id) {
        return directoryFinder.getById(id);
    }

    @Override
    public Directory add(int parentDirectoryId, String name) {
        return directoryModifier.add(parentDirectoryId, name);
    }

    @Override
    public void move(int directoryId, int destinationDirectoryId) {
        directoryModifier.move(directoryId, destinationDirectoryId);
    }

    @Override
    public Directory delete(Directory directory) {
        return directoryModifier.delete(directory);
    }

    @Override
    public Directory delete(int id) {
        return directoryModifier.delete(id);
    }

    @Override
    public Directory rename(int id, String newName) {
        return directoryModifier.rename(id, newName);
    }
}
