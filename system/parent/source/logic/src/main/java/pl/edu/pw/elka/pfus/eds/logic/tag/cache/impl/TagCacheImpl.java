package pl.edu.pw.elka.pfus.eds.logic.tag.cache.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableList;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import pl.edu.pw.elka.pfus.eds.domain.dao.TagDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.Tag;
import pl.edu.pw.elka.pfus.eds.logic.tag.cache.TagCache;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class TagCacheImpl implements TagCache {
    private static final Logger logger = Logger.getLogger(TagCacheImpl.class);
    private LoadingCache<Integer, Tag> cache;
    private TagCacheLoader loader;
    private TagDao tagDao;

    public TagCacheImpl(TagCacheLoader loader, TagDao tagDao) {
        this.loader = loader;
        this.tagDao = tagDao;
        initCache();
    }

    @Override
    public Tag get(Integer key) {
        try {
            return cache.get(key);
        } catch (ExecutionException e) {
            logger.warn("error in tag cache: " + e.getMessage(), e);
            return tagDao.findById(key);
        }
    }

    @Override
    public List<Tag> getAll() {
        Collection<Tag> tagCollection = cache.asMap().values();
        return ImmutableList.copyOf(tagCollection);
    }

    @Override
    public void rebuild() {
        List<Tag> tags = tagDao.getAll();
        Map<Integer, Tag> tagMap = new HashMap<>();
        for(Tag tag : tags) {
            tagMap.put(tag.getId(), tag);
        }
        cache.putAll(tagMap);
    }

    @Override
    public void setSession(Session session) {
        tagDao.setSession(session);
    }

    private void initCache() {
        cache = CacheBuilder.newBuilder().build(loader);
        rebuild();
    }
}
