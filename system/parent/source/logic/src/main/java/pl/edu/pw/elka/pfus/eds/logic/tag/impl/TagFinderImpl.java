package pl.edu.pw.elka.pfus.eds.logic.tag.impl;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.primitives.Ints;
import org.objectledge.context.Context;
import pl.edu.pw.elka.pfus.eds.domain.dao.TagDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.Document;
import pl.edu.pw.elka.pfus.eds.domain.entity.Tag;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.logic.document.DownloadPrivilegeManager;
import pl.edu.pw.elka.pfus.eds.logic.tag.TagFinder;
import pl.edu.pw.elka.pfus.eds.logic.tag.cache.TagCache;
import pl.edu.pw.elka.pfus.eds.security.SecurityFacade;
import pl.edu.pw.elka.pfus.eds.util.ValueNormalizer;
import pl.edu.pw.elka.pfus.eds.util.config.Config;
import pl.edu.pw.elka.pfus.eds.util.word.distance.WordDistance;

import java.util.LinkedList;
import java.util.List;

public class TagFinderImpl implements TagFinder {
    private Config config;
    private TagCache tagCache;
    private WordDistance distance;
    private TagDao tagDao;
    private DownloadPrivilegeManager downloadPrivilegeManager;
    private SecurityFacade securityFacade;
    private Context context;
    private final int MAX_DISTANCE;

    public TagFinderImpl(Config config, TagCache tagCache, WordDistance distance, TagDao tagDao,
                         DownloadPrivilegeManager downloadPrivilegeManager, SecurityFacade securityFacade,
                         Context context) {
        this.config = config;
        this.tagCache = tagCache;
        this.distance = distance;
        this.tagDao = tagDao;
        this.downloadPrivilegeManager = downloadPrivilegeManager;
        this.securityFacade = securityFacade;
        this.context = context;
        MAX_DISTANCE = this.config.getInt("max_distance");
    }

    @Override
    public Tag getTagWithLoadedDocuments(String value) {
        tagDao.clear();
        Tag tag = tagDao.findByValue(value);
        Tag detachedTag = Tag.from(tag);
        User currentUser = securityFacade.getCurrentUser(context);
        List<Document> filteredOutDocuments = downloadPrivilegeManager
                .filterOutInaccessibleDocuments(currentUser, tag.getDocuments());
        detachedTag.setDocuments(filteredOutDocuments);
        return detachedTag;
    }

    @Override
    public List<Tag> getAll() {
        return tagCache.getAll();
    }

    @Override
    public List<Tag> getAllWithLoadedDocuments() {
        List<Tag> tags = tagCache.getAll();
        for(Tag tag : tags) {
            tag.getDocuments();
        }
        return tags;
    }

    @Override
    public List<Tag> getSimilars(String value) {
        String normalizedValue = ValueNormalizer.normalizeValue(value);

        List<Tag> allTags = getAll();
        List<Tag> matchingTags = new LinkedList<>();

        for(Tag tag : allTags) {
            if(isSimilar(tag.getNormalizedValue(), normalizedValue))
                matchingTags.add(tag);
        }
        return matchingTags;
    }

    @VisibleForTesting
    boolean isSimilar(String value1, String value2) {
        int shorterLength = Ints.min(value1.length(), value2.length());
        // skracamy - w przeciwnym wypadku krotsze slowa niz 3 zawsze by sie wyswietlaly
        value1 = value1.substring(0, shorterLength);
        return distance.distance(value1, value2) <= MAX_DISTANCE;
    }
}
