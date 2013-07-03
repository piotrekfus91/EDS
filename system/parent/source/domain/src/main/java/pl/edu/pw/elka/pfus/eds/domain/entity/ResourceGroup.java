package pl.edu.pw.elka.pfus.eds.domain.entity;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableSet;

import java.util.HashSet;
import java.util.Set;

/**
 * Klasa reprezentuje grupę zasobów.
 */
public class ResourceGroup extends IdentifableEntity implements Named, Versionable {
    private Integer id;
    private String name;
    private String description;
    private Integer version;
    private User founder;
    private Set<Directory> directories = new HashSet<Directory>();
    private Set<Document> documents = new HashSet<Document>();

    /**
     * Zwraca id encji.
     *
     *@return id encji.
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * Zwraca nazwę obiektu.
     * 
     * @return nazwa obiektu.
     */
    @Override
    public String getName() {
        return name;
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

    public void setVersion(Integer version) {
        this.version = version;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getFounder() {
        return founder;
    }

    public void setFounder(User founder) {
        this.founder = founder;
    }

    public Set<Directory> getDirectories() {
        return ImmutableSet.copyOf(directories);
    }

    public void setDirectories(Set<Directory> directories) {
        this.directories = directories;
    }

    public void addDirectory(Directory directory) {
        directories.add(directory);
    }

    public void removeDirectory(Directory directory) {
        directories.remove(directory);
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

        ResourceGroup that = (ResourceGroup) o;

        if (founder != null ? !founder.equals(that.founder) : that.founder != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, founder);
    }
}