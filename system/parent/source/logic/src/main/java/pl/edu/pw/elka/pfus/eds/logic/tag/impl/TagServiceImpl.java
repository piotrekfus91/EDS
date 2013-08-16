package pl.edu.pw.elka.pfus.eds.logic.tag.impl;

import pl.edu.pw.elka.pfus.eds.domain.entity.Tag;
import pl.edu.pw.elka.pfus.eds.logic.tag.TagFinder;
import pl.edu.pw.elka.pfus.eds.logic.tag.TagModifier;
import pl.edu.pw.elka.pfus.eds.logic.tag.TagService;

import java.util.List;

public class TagServiceImpl implements TagService {
    private TagFinder tagFinder;
    private TagModifier tagModifier;

    public TagServiceImpl(TagFinder tagFinder, TagModifier tagModifier) {
        this.tagFinder = tagFinder;
        this.tagModifier = tagModifier;
    }

    @Override
    public List<Tag> getAll() {
        return tagFinder.getAll();
    }

    @Override
    public List<Tag> getSimilars(String value) {
        return tagFinder.getSimilars(value);
    }

    @Override
    public void addTagsToDocument(int documentId, String commaSeparatedTagList) {
        tagModifier.addTagsToDocument(documentId, commaSeparatedTagList);
    }
}
