package pl.edu.pw.elka.pfus.eds.domain.entity;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Klasa reprezentująca katalog użytkownika.
 */
public class Directory extends IdentifableEntity implements Versionable, FileSystemEntry {
    private Integer id;

    @NotNull(message = "{file.system.name}")
    @Length(min = 1, max = 30, message = "{file.system.name}")
    private String name;
    private Integer version;

    @NotNull(message = "{document.owner.not.empty}")
    private User owner;

    private List<ResourceGroup> resourceGroups = new LinkedList<>();
    private List<Directory> subdirectories = new LinkedList<>();
    private Directory parentDirectory;
    private List<Document> documents = new LinkedList<>();

    /**
     * Zwraca id encji.
     *
     * @return id encji.
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * Zwraca wersję encji na potrzeby optymistycznego blokowania.
     *
     * @return wersja.
     */
    @Override
    public Integer getVersion() {
        return version;
    }

    /**
     * Zwraca nazwę w systemie plików.
     *
     * @return nazwa w systemie plików.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Zwraca ścieżkę w postaci stringa z separatorem {@see PATH_SEPARATOR}.
     * Przykładowa ścieżka: /parent/firstChild/thisDirectory.
     *
     * @return ścieżka w postaci string.
     */
    @Override
    @Transient
    public String getStringPath() {
        String parentPath = "";
        if(hasParent())
            parentPath = parentDirectory.getStringPath();

        String thisPath;
        if(Strings.isNullOrEmpty(name))
            thisPath = FileSystemEntry.PATH_SEPARATOR;
        else if(parentPath.endsWith(FileSystemEntry.PATH_SEPARATOR))
            thisPath = name;
        else if(FileSystemEntry.PATH_SEPARATOR.equals(name))
            thisPath = FileSystemEntry.PATH_SEPARATOR;
        else
            thisPath = FileSystemEntry.PATH_SEPARATOR + name;

        return parentPath + thisPath;
    }

    @Override
    public void removeFromAssociations() {
        for(ResourceGroup resourceGroup : resourceGroups) {
            if(resourceGroup.getDirectories().contains(this))
                resourceGroup.removeDirectory(this);
        }
        resourceGroups.clear();
    }

    @Transient
    public boolean isRootDirectory() {
        return parentDirectory == null;
    }

    /**
     * Zwraca listę wszystkich elementów systemu plików leżących w bieżącym katalogu,
     * to znaczy podkatalogów i plików.
     *
     * @return lista elementów systemu plików.
     */
    @Transient
    public List<FileSystemEntry> getFileSystemEntries() {
        List<Directory> subdirectories = Lists.newLinkedList(this.subdirectories);
        cleanCollectionsFromNulls(subdirectories);
        List<Document> documents = Lists.newLinkedList(this.documents);
        cleanCollectionsFromNulls(documents);
        return ImmutableList.<FileSystemEntry>builder().addAll(subdirectories).addAll(documents).build();
    }

    @Transient
    public boolean isEmpty() {
        return subdirectories.isEmpty();
    }

    public int size() {
        return subdirectories.size();
    }

    public void clear() {
        subdirectories.clear();
    }

    public boolean containsDirectory(Directory directory) {
        return subdirectories.contains(directory);
    }

    public boolean hasParent() {
        return parentDirectory != null;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        Directory rootDir = findRoot();
        recursiveSetOwner(rootDir, owner);
    }

    public List<ResourceGroup> getResourceGroups() {
        return ImmutableList.copyOf(resourceGroups);
    }

    public void setResourceGroups(List<ResourceGroup> resourceGroups) {
        this.resourceGroups = resourceGroups;
    }

    public void addResourceGroup(ResourceGroup resourceGroup) {
        resourceGroups.add(resourceGroup);
    }

    public void removeResourceGroup(ResourceGroup resourceGroup) {
        resourceGroups.remove(resourceGroup);
    }

    public List<Directory> getSubdirectories() {
        return ImmutableList.copyOf(subdirectories);
    }

    public void setSubdirectories(List<Directory> subdirectories) {
        this.subdirectories = subdirectories;
    }

    public void addSubdirectory(Directory directory) {
        subdirectories.add(directory);
        directory.parentDirectory = this;
        directory.owner = owner;
    }

    public void removeSubdirectory(Directory directory) {
        subdirectories.remove(directory);
        directory.parentDirectory = null;
    }

    public Directory getParentDirectory() {
        return parentDirectory;
    }

    public void setParentDirectory(Directory parentDirectory) {
        if(this.parentDirectory == null && parentDirectory == null)
            return;

        if(this.parentDirectory == null && parentDirectory != null) {
            this.parentDirectory = parentDirectory;
            this.owner = parentDirectory.getOwner();
            parentDirectory.addSubdirectory(this);
            return;
        }

        if(this.parentDirectory != null && parentDirectory == null) {
            this.parentDirectory.removeSubdirectory(this);
            this.parentDirectory = null;
            return;
        }

        // oba nie nulle
        this.parentDirectory.removeSubdirectory(this);
        parentDirectory.addSubdirectory(this);
        this.owner = parentDirectory.getOwner();
        this.parentDirectory = parentDirectory;
    }

    public List<Document> getDocuments() {
        documents.removeAll(Collections.singleton(null));
        return ImmutableList.copyOf(documents);
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public void addDocument(Document document) {
        documents.add(document);
    }

    public void removeDocument(Document document) {
        documents.remove(document);
    }

    Directory findRoot() {
        Directory rootDir = this;
        while(rootDir.getParentDirectory() != null)
            rootDir = rootDir.getParentDirectory();
        return rootDir;
    }

    void recursiveSetOwner(Directory directory, User owner) {
        directory.owner = owner;
        for(Directory subdir : directory.getSubdirectories()) {
            recursiveSetOwner(subdir, owner);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Directory directory = (Directory) o;

        if(id != null && directory.id != null)
            return id.equals(directory.id);

        boolean ownership = true; // domyslnie, aby nie negowalo nam zawsze warunku
        if(owner != null && directory.owner != null)
            ownership = owner.equals(directory.owner);

        return ownership && getStringPath().equals(directory.getStringPath());
    }

    // specjalnie - krzak hibernate
//    @Override
//    public int hashCode() {
//        return Objects.hashCode(name, subdirectories, getStringPath());
//    }
}