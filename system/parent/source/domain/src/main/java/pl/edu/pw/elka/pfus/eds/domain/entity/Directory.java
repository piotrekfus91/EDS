package pl.edu.pw.elka.pfus.eds.domain.entity;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;

import java.util.HashSet;
import java.util.Set;

/**
 * Klasa reprezentująca katalog użytkownika.
 */
public class Directory extends IdentifableEntity implements Versionable {
    private Integer id;
    private String name;
    private Integer version;
    private Set<ResourceGroup> resourceGroups = new HashSet<ResourceGroup>();
    private Set<Directory> subdirectories = new HashSet<Directory>();
    private Directory parentDirectory;
    private Set<Document> documents = new HashSet<Document>();

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        // logika jest w ponizszej metodzie
        subdirectories.add(directory);
        directory.setParentDirectory(this);
    }

    public void removeSubdirectory(Directory directory) {
        // jak wyzej
        subdirectories.remove(directory);
        directory.setParentDirectory(null);
    }

    public Directory getParentDirectory() {
        return parentDirectory;
    }

    public void setParentDirectory(Directory parentDirectory) {
        // dostajemy parent null - musimy usunac ze starego parenta biezacy
        if(parentDirectory == null && this.parentDirectory != null)
            this.parentDirectory.removeSubdirectory(this);

        // dostajemy nowy, nie nullowy parent, trzeba dodac do niego biezacy
        else if(parentDirectory != null && !parentDirectory.containsDirectory(this))
            parentDirectory.addSubdirectory(this);

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Directory directory = (Directory) o;

        if (name != null ? !name.equals(directory.name) : directory.name != null) return false;
        if (subdirectories != null ? !subdirectories.equals(directory.subdirectories) : directory.subdirectories != null)
            return false;

        return getStringPath().equals(directory.getStringPath());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, subdirectories, getStringPath());
    }
}