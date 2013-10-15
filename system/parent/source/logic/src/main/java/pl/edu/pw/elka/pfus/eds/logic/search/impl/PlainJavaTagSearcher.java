package pl.edu.pw.elka.pfus.eds.logic.search.impl;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import org.objectledge.context.Context;
import pl.edu.pw.elka.pfus.eds.domain.dao.TagDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.Document;
import pl.edu.pw.elka.pfus.eds.domain.entity.Tag;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.logic.document.DownloadPrivilegeManager;
import pl.edu.pw.elka.pfus.eds.logic.search.TagSearcher;
import pl.edu.pw.elka.pfus.eds.logic.tag.cache.TagCache;
import pl.edu.pw.elka.pfus.eds.security.SecurityFacade;
import pl.edu.pw.elka.pfus.eds.util.ValueNormalizer;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Implementacja wyszukiwarki czystej javy.
 */
public class PlainJavaTagSearcher implements TagSearcher {
    private DownloadPrivilegeManager downloadPrivilegeManager;
    private TagDao tagDao;
    private TagCache tagCache;
    private Context context;
    private SecurityFacade securityFacade;

    public PlainJavaTagSearcher(DownloadPrivilegeManager downloadPrivilegeManager, TagDao tagDao, TagCache tagCache,
                                Context context, SecurityFacade securityFacade) {
        this.downloadPrivilegeManager = downloadPrivilegeManager;
        this.tagDao = tagDao;
        this.tagCache = tagCache;
        this.context = context;
        this.securityFacade = securityFacade;
    }

    @Override
    public List<Tag> findTagsByName(String name) {
        User currentUser = securityFacade.getCurrentUser(context);
        if(Strings.isNullOrEmpty(name))
            return new LinkedList<>();

        List<String> normalizedSplitNames = getNormalizedSplitNames(name);

        List<Tag> matchingTags = new LinkedList<>();
        for(Tag tag : tagCache.getAll()) {
            boolean match = true;
            for(String split : normalizedSplitNames) {
                if(!tag.getNormalizedValue().contains(split)) {
                    match = false;
                    break;
                }
            }
            if(match) {
                tag = tagDao.findByValue(tag.getValue());
                Tag detachedTag = Tag.from(tag);
                List<Document> filteredOutDocuments = downloadPrivilegeManager
                        .filterOutInaccessibleDocuments(currentUser, tag.getDocuments());
                detachedTag.setDocuments(filteredOutDocuments);
                matchingTags.add(detachedTag);
            }
        }
        return matchingTags;
    }

    private List<String> getNormalizedSplitNames(String name) {
        List<String> normalizedSplitNames = new LinkedList<>();
        Iterable <String> splitTagName = Splitter.on(Pattern.compile("\\s+"))
                .omitEmptyStrings().trimResults().split(name);
        for(String str : splitTagName) {
            normalizedSplitNames.add(ValueNormalizer.normalizeValue(str));
        }
        return normalizedSplitNames;
    }
}
