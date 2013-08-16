package pl.edu.pw.elka.pfus.eds.logic.tag.cache.impl;

import com.google.common.cache.CacheLoader;
import pl.edu.pw.elka.pfus.eds.domain.dao.TagDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.Tag;

public class TagCacheLoader extends CacheLoader<Integer, Tag> {
    private TagDao tagDao;

    public TagCacheLoader(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public Tag load(Integer key) throws Exception {
        return tagDao.findById(key);
    }
}
