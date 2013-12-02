package pl.edu.pw.elka.pfus.eds.logic.tag.cache.impl;

import com.google.common.cache.CacheLoader;
import pl.edu.pw.elka.pfus.eds.domain.dao.TagDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.Tag;

public class TagCacheLoader extends CacheLoader<String, Tag> {
    private TagDao tagDao;

    public TagCacheLoader(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public Tag load(String value) throws Exception {
        return tagDao.findByValue(value);
    }
}
