package pl.edu.pw.elka.pfus.eds.logic.tag.impl;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Splitter;
import org.apache.log4j.Logger;
import org.objectledge.context.Context;
import pl.edu.pw.elka.pfus.eds.domain.dao.DocumentDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.TagDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.Document;
import pl.edu.pw.elka.pfus.eds.domain.entity.Tag;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.logic.exception.InternalException;
import pl.edu.pw.elka.pfus.eds.logic.tag.TagModifier;
import pl.edu.pw.elka.pfus.eds.logic.tag.cache.TagCache;
import pl.edu.pw.elka.pfus.eds.logic.validator.LogicValidator;
import pl.edu.pw.elka.pfus.eds.security.SecurityFacade;

import java.util.List;

import static pl.edu.pw.elka.pfus.eds.logic.error.handler.ErrorHandler.handle;

public class TagModifierImpl implements TagModifier {
    private static final Logger logger = Logger.getLogger(TagModifierImpl.class);

    private Context context;
    private SecurityFacade securityFacade;
    private DocumentDao documentDao;
    private TagDao tagDao;
    private TagCache tagCache;

    public TagModifierImpl(Context context, SecurityFacade securityFacade, DocumentDao documentDao, TagDao tagDao,
                           TagCache tagCache) {
        this.context = context;
        this.securityFacade = securityFacade;
        this.documentDao = documentDao;
        this.tagDao = tagDao;
        this.tagCache = tagCache;
    }

    @Override
    public void addTagsToDocument(int documentId, String commaSeparatedTagList) {
        tagDao.setSession(documentDao.getSession());
        documentDao.clear();
        User currentUser = securityFacade.getCurrentUser(context);
        Document document = documentDao.findById(documentId);
        LogicValidator.validateOwnershipOverDocument(currentUser, document);

        Iterable<String> tagValues = splitListByComma(commaSeparatedTagList);

        try {
            documentDao.beginTransaction();
            document.clearTags();
            List<Tag> allTags = tagDao.getAll();
            removeDocumentFromTags(document, allTags);
            for(String tagValue : tagValues) {
                boolean found = false;
                for(Tag tag : allTags) {
                    if(tagValue.equals(tag.getValue())) {
                        found = true;
                        if(!tag.getDocuments().contains(document))
                            tag.addDocument(document);
                        if(!document.getTags().contains(tag))
                            document.addTag(tag);
                    }
                    tagDao.persist(tag);
                }
                if(!found) {
                    createNewTagAndPersist(document, tagValue);
                }
            }
            documentDao.persist(document);
            documentDao.commitTransaction();
            tagCache.rebuild();
        } catch (Exception e) {
            handle(e, documentDao);
            throw new InternalException();
        }
    }

    @VisibleForTesting
    Iterable<String> splitListByComma(String commaSeparatedList) {
        return Splitter.on(",").omitEmptyStrings().trimResults().split(commaSeparatedList);
    }

    private void createNewTagAndPersist(Document document, String tagValue) {
        Tag tag = new Tag();
        tag.setValue(tagValue);
        tag.addDocument(document);
        document.addTag(tag);
        tagDao.persist(tag);
    }

    private void removeDocumentFromTags(Document document, List<Tag> tags) {
        for(Tag tag : tags) {
            tag.removeDocument(document);
        }
    }
}
