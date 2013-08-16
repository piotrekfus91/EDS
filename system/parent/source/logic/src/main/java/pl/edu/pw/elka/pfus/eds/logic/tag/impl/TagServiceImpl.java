package pl.edu.pw.elka.pfus.eds.logic.tag.impl;

import pl.edu.pw.elka.pfus.eds.domain.entity.Tag;
import pl.edu.pw.elka.pfus.eds.logic.tag.TagFinder;
import pl.edu.pw.elka.pfus.eds.logic.tag.TagService;

import java.util.List;

public class TagServiceImpl implements TagService {
    private TagFinder tagFinder;

    public TagServiceImpl(TagFinder tagFinder) {
        this.tagFinder = tagFinder;
    }

    @Override
    public List<Tag> getAll() {
        return tagFinder.getAll();
    }

    @Override
    public List<Tag> getSimilars(String value) {
        return tagFinder.getSimilars(value);
    }
}
