package pl.edu.pw.elka.pfus.eds.domain.entity;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Klasa reprezentująca systemowego użytkownika.
 */
public class User extends IdentifableEntity implements Named, Versionable {
    private Integer id;
    private String name;
    private String passwordValue;
    private String firstName;
    private String lastName;
    private String email;
    private boolean locked = false;
    private Date created;
    private Integer version;
    private Date lastLogin;
    private Set<Directory> directories = new HashSet<Directory>();
    private Set<Comment> comments = new HashSet<Comment>();
    private Set<Document> documents = new HashSet<Document>();
    private Set<ResourceGroup> resourceGroups = new HashSet<ResourceGroup>();

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

    /**
     * Zwraca sformatowaną nazwę użytkownika.
     * Jeśli są podane imię, to zwraca imię i ewentualnie nazwisko,
     * w przeciwnym wypadku login.
     *
     * @return sformatowana nazwa użytkownika.
     */
    public String getFriendlyName() {
        if(!Strings.isNullOrEmpty(firstName))
            return firstName + Strings.nullToEmpty(lastName);
        else
            return name;
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

    public String getPasswordValue() {
        return passwordValue;
    }

    public void setPasswordValue(String passwordValue) {
        this.passwordValue = passwordValue;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
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

    public Set<Comment> getComments() {
        return ImmutableSet.copyOf(comments);
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (locked != user.locked) return false;
        if (created != null ? !created.equals(user.created) : user.created != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (lastLogin != null ? !lastLogin.equals(user.lastLogin) : user.lastLogin != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (version != null ? !version.equals(user.version) : user.version != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, firstName, lastName, email, locked, lastLogin, created);
    }
}