package pl.edu.pw.elka.pfus.eds.domain.entity;

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

    public Set<Comment> getComments() {
        return ImmutableSet.copyOf(comments);
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

    public void addDocument(Document document) {
        documents.add(document);
    }

    public void removeDocument(Document document) {
        documents.remove(document);
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
}