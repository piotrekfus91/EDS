package pl.edu.pw.elka.pfus.eds.domain.entity;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Klasa reprezentująca katalog użytkownika.
 */
public class Directory extends IdentifableEntity implements Versionable, FileSystemEntry {
    private Integer id;
    private String name;
    private Integer version;
    private User owner;
    private Set<ResourceGroup> resourceGroups = new LinkedHashSet<>();
    private Set<Directory> subdirectories = new LinkedHashSet<>();
    private Directory parentDirectory;
    private Set<Document> documents = new LinkedHashSet<>();

    private static final String PATH_SEPARATOR = "/";

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
    public String getStringPath() {
        String parentPath = "";
        if(hasParent())
            parentPath = parentDirectory.getStringPath();

        String thisPath = "";
        if(Strings.isNullOrEmpty(name))
            thisPath = "/";
        else
            thisPath = "/" + name;

        return parentPath + thisPath;
    }

    public boolean isRootDirectory() {
        return parentDirectory == null;
    }

    /**
     * Zwraca listę wszystkich elementów systemu plików leżących w bieżącym katalogu,
     * to znaczy podkatalogów i plików.
     *
     * @return lista elementów systemu plików.
     */
    public List<FileSystemEntry> getSubdirectoriesAndDocuments() {
        return ImmutableList.<FileSystemEntry>builder().addAll(subdirectories).addAll(documents).build();
    }

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

    public Set<ResourceGroup> getResourceGroups() {
        return ImmutableSet.copyOf(resourceGroups);
    }

    public void setResourceGroups(Set<ResourceGroup> resourceGroups) {
        this.resourceGroups = resourceGroups;
    }

    public void addResourceGroup(ResourceGroup resourceGroup) {
        resourceGroups.add(resourceGroup);
    }

    public void removeResourceGroup(ResourceGroup resourceGroup) {
        resourceGroups.remove(resourceGroup);
    }

    public Set<Directory> getSubdirectories() {
        return ImmutableSet.copyOf(subdirectories);
    }

    public void setSubdirectories(Set<Directory> subdirectories) {
        this.subdirectories = subdirectories;
    }

    public void addSubdirectory(Directory directory) {
        subdirectories.add(directory);
        directory.parentDirectory = this;
        directory.owner = owner;
    }

    public void removeSubdirectory(Directory directory) {
        Set<Directory> newSubdirectories = Sets.newLinkedHashSet(this.subdirectories);
        newSubdirectories.remove(directory);
        this.subdirectories = newSubdirectories;
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

    public Set<Document> getDocuments() {
        return ImmutableSet.copyOf(documents);
    }

    public void setDocuments(Set<Document> documents) {
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

//    @Override
//    public int hashCode() {
//        return Objects.hashCode(name, subdirectories, getStringPath());
//    }
}