package pl.edu.pw.elka.pfus.eds.logic.tag.impl;

import pl.edu.pw.elka.pfus.eds.domain.entity.Tag;
import pl.edu.pw.elka.pfus.eds.logic.tag.TagFinder;
import pl.edu.pw.elka.pfus.eds.logic.tag.cache.TagCache;

import java.util.List;

public class TagFinderImpl implements TagFinder {
    private TagCache tagCache;

    public TagFinderImpl(TagCache tagCache) {
        this.tagCache = tagCache;
    }

    @Override
    public List<Tag> getAll() {
        return tagCache.getAll();
    }
}
