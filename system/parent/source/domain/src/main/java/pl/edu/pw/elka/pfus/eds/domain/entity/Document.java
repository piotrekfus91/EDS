package pl.edu.pw.elka.pfus.eds.domain.entity;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * Klasa reprezentująca dokument.
 */
public class Document extends IdentifableEntity implements FileSystemEntry {
    private Integer id;

    @NotNull(message = "{file.system.name}")
    @Length(min = 1, max = 255, message = "{file.system.name}")
    private String name;

    @NotNull(message = "{document.creation.date.not.empty}")
    private Date created;

    @NotNull(message = "{document.contentMd5.not.empty}")
    private String contentMd5;
    private Integer version;
    private List<ResourceGroup> resourceGroups = new LinkedList<>();
    private MimeType mimeType;
    private User owner;
    private Directory directory;
    private List<Tag> tags = new LinkedList<>();
    private List<Comment> comments = new LinkedList<>();

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
     * Zwraca nazwę, pod jaką leży plik w systemie plików.
     *
     * @return nazwa w systemie plikół.
     */
    @Transient
    public String getFileSystemName() {
        return "" + created.getTime();
    }

    /**
     * Zwraca informację czy dokument jest sesyjny.
     * Jako dokument sesyjny uważamy świeżo podgrane dokumenty,
     * nie leżące jeszcze w żadnym katalogu.
     * Dokumenty takie są usuwane na koniec sesji.
     *
     * @return czy jest dokument sesyjny.
     */
    @Transient
    public boolean isSessionDocument() {
        return directory == null;
    }

    @Transient
    @Override
    public String getStringPath() {
        String fileNameWithPath = FileSystemEntry.PATH_SEPARATOR + name;
        if(directory == null)
            return fileNameWithPath;
        else
            return Strings.nullToEmpty(directory.getStringPath()) + fileNameWithPath;
    }

    @Override
    public void removeFromAssociations() {
        for(ResourceGroup resourceGroup : resourceGroups) {
            if(resourceGroup.getDocuments().contains(this))
                resourceGroup.removeDocument(this);
        }
//        resourceGroups.clear();
        for(Tag tag : tags) {
            if(tag.getDocuments().contains(this))
                tag.removeDocument(this);
        }
//        tags.clear();
        mimeType.removeDocument(this);
        owner.removeDocument(this);
    }

    public void clearTags() {
        tags.clear();
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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getContentMd5() {
        return contentMd5;
    }

    public void setContentMd5(String contentMd5) {
        this.contentMd5 = contentMd5;
    }

    public List<ResourceGroup> getResourceGroups() {
        return ImmutableList.copyOf(resourceGroups);
    }

    @Transient
    public Set<ResourceGroup> getAllResourceGroups() {
        Set<ResourceGroup> allResourceGroups = new HashSet();
        allResourceGroups.addAll(resourceGroups);
        Directory directory = this.directory;
        while(!directory.isRootDirectory()) {
            allResourceGroups.addAll(directory.getResourceGroups());
            directory = directory.getParentDirectory();
        }
        return allResourceGroups;
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

    public MimeType getMimeType() {
        return mimeType;
    }

    public void setMimeType(MimeType mimeType) {
        this.mimeType = mimeType;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Directory getDirectory() {
        return directory;
    }

    public void setDirectory(Directory directory) {
        if(directory != null)
            owner = directory.getOwner();
        else
            owner = null;
        this.directory = directory;
    }

    public List<Tag> getTags() {
        return ImmutableList.copyOf(tags);
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void addTag(Tag tag) {
        tags.add(tag);
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Document document = (Document) o;

        if (contentMd5 != null ? !contentMd5.equals(document.contentMd5) : document.contentMd5 != null) return false;
        if (mimeType != null ? !mimeType.equals(document.mimeType) : document.mimeType != null) return false;
        if (name != null ? !name.equals(document.name) : document.name != null) return false;
        if (owner != null ? !owner.equals(document.owner) : document.owner != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, contentMd5, mimeType, owner);
    }
}