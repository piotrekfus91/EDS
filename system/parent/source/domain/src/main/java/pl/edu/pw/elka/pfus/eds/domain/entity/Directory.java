package pl.edu.pw.elka.pfus.eds.domain.entity;

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

    public void addResourceGroup(ResourceGroup resourceGroup) {
        resourceGroups.add(resourceGroup);
    }

    public void removeResourceGroup(ResourceGroup resourceGroup) {
        resourceGroups.remove(resourceGroup);
    }

    public Set<Directory> getSubdirectories() {
        return ImmutableSet.copyOf(subdirectories);
    }

    public void addSubdirectory(Directory directory) {
        subdirectories.add(directory);
    }

    public void removeSubdirectory(Directory directory) {
        subdirectories.remove(directory);
    }

    public Directory getParentDirectory() {
        return parentDirectory;
    }

    public void setParentDirectory(Directory parentDirectory) {
        this.parentDirectory = parentDirectory;
    }

    public Set<Document> getDocuments() {
        return ImmutableSet.copyOf(documents);
    }

    public void addDocument(Document document) {
        documents.add(document);
    }

    public void removeDocument(Document document) {
        documents.remove(document);
    }
}