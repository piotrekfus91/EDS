package pl.edu.pw.elka.pfus.eds.logic.search.impl;

import pl.edu.pw.elka.pfus.eds.domain.entity.Document;
import pl.edu.pw.elka.pfus.eds.domain.entity.Tag;
import pl.edu.pw.elka.pfus.eds.logic.search.Indexer;
import pl.edu.pw.elka.pfus.eds.logic.search.SearchService;
import pl.edu.pw.elka.pfus.eds.logic.search.Searcher;
import pl.edu.pw.elka.pfus.eds.logic.search.TagSearcher;
import pl.edu.pw.elka.pfus.eds.logic.search.dto.DocumentSearchDto;

import java.io.IOException;
import java.util.List;

public class SearchServiceImpl implements SearchService {
    private Indexer indexer;
    private Searcher searcher;
    private TagSearcher tagSearcher;

    public SearchServiceImpl(Indexer indexer, Searcher searcher, TagSearcher tagSearcher) {
        this.indexer = indexer;
        this.searcher = searcher;
        this.tagSearcher = tagSearcher;
    }

    @Override
    public void index(Document document) throws IOException {
        indexer.index(document);
    }

    @Override
    public void updateIndex(Document document) throws IOException {
        indexer.updateIndex(document);
    }

    @Override
    public void deleteFromIndex(int documentId) throws IOException {
        indexer.deleteFromIndex(documentId);
    }

    @Override
    public List<DocumentSearchDto> findByTitle(String title) {
        return searcher.findByTitle(title);
    }

    @Override
    public List<DocumentSearchDto> findByContent(String content) {
        return searcher.findByContent(content);
    }

    @Override
    public List<Tag> findTagsByName(String name) {
        return tagSearcher.findTagsByName(name);
    }
}
