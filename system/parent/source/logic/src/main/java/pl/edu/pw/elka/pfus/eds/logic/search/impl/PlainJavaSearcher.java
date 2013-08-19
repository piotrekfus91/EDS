package pl.edu.pw.elka.pfus.eds.logic.search.impl;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import pl.edu.pw.elka.pfus.eds.domain.entity.Tag;
import pl.edu.pw.elka.pfus.eds.logic.search.Searcher;
import pl.edu.pw.elka.pfus.eds.logic.tag.cache.TagCache;
import pl.edu.pw.elka.pfus.eds.util.ValueNormalizer;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Implementacja wyszukiwarki czystej javy.
 */
public class PlainJavaSearcher implements Searcher {
    private TagCache tagCache;

    public PlainJavaSearcher(TagCache tagCache) {
        this.tagCache = tagCache;
    }

    @Override
    public List<Tag> findTagsByName(String name) {
        if(Strings.isNullOrEmpty(name))
            return new LinkedList<>();

        List<String> normalizedSplitedNames = getNormalizedSplittedNames(name);

        List<Tag> matchingTags = new LinkedList<>();
        for(Tag tag : tagCache.getAll()) {
            boolean match = true;
            for(String splitted : normalizedSplitedNames) {
                if(!tag.getNormalizedValue().contains(splitted)) {
                    match = false;
                    break;
                }
            }
            if(match)
                matchingTags.add(tag);
        }
        return matchingTags;
    }

    private List<String> getNormalizedSplittedNames(String name) {
        List<String> normalizedSplitedNames = new LinkedList<>();
        Iterable <String> splittedTagName = Splitter.on(Pattern.compile("\\s+"))
                .omitEmptyStrings().trimResults().split(name);
        for(String str : splittedTagName) {
            normalizedSplitedNames.add(ValueNormalizer.normalizeValue(str));
        }
        return normalizedSplitedNames;
    }
}
