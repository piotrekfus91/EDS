package pl.edu.pw.elka.pfus.eds.logic.search.impl;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.objectledge.context.Context;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.edu.pw.elka.pfus.eds.domain.entity.Document;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.logic.document.DownloadPrivilegeManager;
import pl.edu.pw.elka.pfus.eds.logic.search.Extractor;
import pl.edu.pw.elka.pfus.eds.logic.search.NationalCharacterReplacer;
import pl.edu.pw.elka.pfus.eds.logic.search.dto.DocumentSearchDto;
import pl.edu.pw.elka.pfus.eds.security.SecurityFacade;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SearchServiceImplTest {
    private PlainJavaTagSearcher tagSearcher;
    private LuceneIndexer indexer;
    private LuceneSearcher searcher;
    private Extractor extractor;
    private NationalCharacterReplacer characterReplacer;
    private SearchServiceImpl searchService;
    private DownloadPrivilegeManager downloadPrivilegeManager;
    private SecurityFacade securityFacade;
    private Context context;
    private Directory directory;

    @BeforeMethod
    public void setUp() throws Exception {
        directory = new RAMDirectory();
        tagSearcher = mock(PlainJavaTagSearcher.class);
        extractor = mock(Extractor.class);
        downloadPrivilegeManager = mock(DownloadPrivilegeManager.class);
        securityFacade = mock(SecurityFacade.class);
        context = mock(Context.class);
        characterReplacer = new PolishCharacterReplacer();
        indexer = new LuceneIndexer(directory, extractor, characterReplacer);
        searcher = new LuceneSearcher(directory, characterReplacer, downloadPrivilegeManager, securityFacade, context);
        searchService = new SearchServiceImpl(indexer, searcher, tagSearcher);

        replyMocks();
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

        searchResult = searchService.findByContent("a");
        assertThat(searchResult.size()).isEqualTo(4);

        searchResult = searchService.findByContent("abcdef");
        assertThat(searchResult.size()).isEqualTo(1);

        searchResult = searchService.findByContent("zażółć");
        assertThat(searchResult.size()).isEqualTo(1);

        searchResult = searchService.findByContent("zazolc");
        assertThat(searchResult.size()).isEqualTo(1);

        searchResult = searchService.findByContent("dw");
        assertThat(searchResult.size()).isEqualTo(2);

        searchResult = searchService.findByContent("jfdksjfldsf");
        assertThat(searchResult.size()).isEqualTo(0);
    }

    private Document createDocument(int id, String name) {
        Document document = new Document();
        document.setId(id);
        document.setName(name);
        return document;
    }

    private void replyMocks() {
        when(extractor.extract(any(Document.class))).thenReturn("abcdef")
                .thenReturn("zażółć gęślą jaźń")
                .thenReturn("dwa razy");
        when(downloadPrivilegeManager.canDownload(any(User.class), anyInt())).thenReturn(true);
    }
}
