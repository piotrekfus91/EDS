package pl.edu.pw.elka.pfus.eds.logic.search.impl;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.edu.pw.elka.pfus.eds.domain.entity.Document;
import pl.edu.pw.elka.pfus.eds.logic.search.dto.DocumentSearchDto;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class SearchServiceImplTest {
    private PlainJavaTagSearcher tagSearcher;
    private LuceneIndexer indexer;
    private LuceneSearcher searcher;
    private SearchServiceImpl searchService;
    private Directory directory;

    @BeforeMethod
    public void setUp() throws Exception {
        directory = new RAMDirectory();
        tagSearcher = mock(PlainJavaTagSearcher.class);
        indexer = new LuceneIndexer(directory);
        searcher = new LuceneSearcher(directory);
        searchService = new SearchServiceImpl(indexer, searcher, tagSearcher);
    }

    @Test
    public void test() throws Exception {
        searchService.index(createDocument(1, "first"));
        searchService.index(createDocument(1, "first second"));
        searchService.index(createDocument(1, "third"));
        searchService.index(createDocument(1, "second fourth"));

        List<DocumentSearchDto> searchResult = searchService.findByTitle("first");
        assertThat(searchResult.size()).isEqualTo(2);

        searchResult = searchService.findByTitle("second");
        assertThat(searchResult.size()).isEqualTo(2);

        searchResult = searchService.findByTitle("third");
        assertThat(searchResult.size()).isEqualTo(1);

        searchResult = searchService.findByTitle("second first");
        assertThat(searchResult.size()).isEqualTo(3);
    }

    private Document createDocument(int id, String name) {
        Document document = new Document();
        document.setId(id);
        document.setName(name);
        return document;
    }
}
