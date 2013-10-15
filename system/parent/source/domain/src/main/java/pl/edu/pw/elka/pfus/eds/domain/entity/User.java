package pl.edu.pw.elka.pfus.eds.domain.entity;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Transient;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Klasa reprezentująca systemowego użytkownika.
 */
public class User extends IdentifableEntity implements Named {
    private Integer id;
    @Length(min = 1, max = 32, message = "{user.name.length}")
    private String name;
    private String passwordValue;

    @Length(min = 1, max = 99, message = "{user.firstName.length}")
    private String firstName;

    @Length(min = 1, max = 99, message = "{user.lastName.length}")
    private String lastName;

    @Email(message = "{email.incorrect}")
    private String email;
    private boolean locked = false;
    private Date created;
    private Integer version;
    private Date lastLogin;
    private List<Directory> directories = new LinkedList<>();
    private List<Comment> comments = new LinkedList<>();
    private List<Document> documents = new LinkedList<>();
    private List<ResourceGroup> resourceGroups = new LinkedList<>();

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

    @Override
    public void removeFromAssociations() {
        directories.remove(this);
        comments.remove(this);
        documents.remove(this);
        resourceGroups.remove(this);
    }

    /**
     * Zwraca sformatowaną nazwę użytkownika.
     * Jeśli są podane imię, to zwraca imię i ewentualnie nazwisko,
     * w przeciwnym wypadku login.
     *
     * @return sformatowana nazwa użytkownika.
     */
    @Transient
    public String getFriendlyName() {
        if(!Strings.isNullOrEmpty(firstName))
            return (firstName + " " + Strings.nullToEmpty(lastName)).trim();
        else
            return name;
    }

    /**
     * Sprawdza czy ten użytkownik jest właścicielem katalogu.
     *
     * @param directory dany katalog.
     * @return true jeśli użytkownik jest właścicielem katalogu, w przeciwnym razie false.
     */
    public boolean isOwnerOfDirectory(Directory directory) {
        return this.equals(directory.getOwner());
    }

    /**
     * Sprawdza czy ten użytkownik jest właścicielem dokumentu.
     *
     * @param document dany dokument.
     * @return true jeśli użytkownik jest właścicielem dokumentu, w przeciwnym razie false.
     */
    public boolean isOwnerOfDocument(Document document) {
        return this.equals(document.getOwner());
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

    public List<Directory> getDirectories() {
        return ImmutableList.copyOf(directories);
    }

    public void setDirectories(List<Directory> directories) {
        this.directories = directories;
    }

    public void addDirectory(Directory directory) {
        directories.add(directory);
    }

    public void removeDirectory(Directory directory) {
        directories.remove(directory);
    }

    public List<Comment> getComments() {
        return ImmutableList.copyOf(comments);
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
    }

    public List<Document> getDocuments() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null)
            return false;
        if (name != null ? !name.equals(user.name) : user.name != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id : 0;
    }

    @Override
    public String toString() {
        return getFriendlyName();
    }
}