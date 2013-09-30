package pl.edu.pw.elka.pfus.eds.logic.search.impl;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.objectledge.context.Context;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.logic.document.DownloadPrivilegeManager;
import pl.edu.pw.elka.pfus.eds.logic.exception.DocumentNotExistsException;
import pl.edu.pw.elka.pfus.eds.logic.exception.InternalException;
import pl.edu.pw.elka.pfus.eds.logic.search.NationalCharacterReplacer;
import pl.edu.pw.elka.pfus.eds.logic.search.Searcher;
import pl.edu.pw.elka.pfus.eds.logic.search.dto.DocumentSearchDto;
import pl.edu.pw.elka.pfus.eds.security.SecurityFacade;
import pl.edu.pw.elka.pfus.eds.util.config.Config;
import pl.edu.pw.elka.pfus.eds.util.file.system.PathHelper;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import static pl.edu.pw.elka.pfus.eds.logic.error.handler.ErrorHandler.handle;

public class LuceneSearcher implements Searcher {
    private static final Logger logger = Logger.getLogger(LuceneSearcher.class);

    private String indexDir;
    private IndexReader indexReader;
    private IndexSearcher indexSearcher;
    private Directory directory;
    private NationalCharacterReplacer characterReplacer;
    private DownloadPrivilegeManager downloadPrivilegeManager;
    private SecurityFacade securityFacade;
    private Context context;

    public LuceneSearcher(Config config, NationalCharacterReplacer characterReplacer,
                          DownloadPrivilegeManager downloadPrivilegeManager,
                          SecurityFacade securityFacade, Context context) throws IOException {
        this.characterReplacer = characterReplacer;
        this.downloadPrivilegeManager = downloadPrivilegeManager;
        this.securityFacade = securityFacade;
        this.context = context;
        indexDir = config.getString("index_dir");
        indexDir = PathHelper.countFileSystemRoot(indexDir);
        directory = FSDirectory.open(new File(indexDir));
    }

    public LuceneSearcher(Directory directory, NationalCharacterReplacer characterReplacer,
                          DownloadPrivilegeManager downloadPrivilegeManager, SecurityFacade securityFacade,
                          Context context) {
        this.directory = directory;
        this.characterReplacer = characterReplacer;
        this.downloadPrivilegeManager = downloadPrivilegeManager;
        this.securityFacade = securityFacade;
        this.context = context;
    }

    private void setupSearcher() throws IOException {
        indexReader = DirectoryReader.open(directory);
        indexSearcher = new IndexSearcher(indexReader);
    }

    @Override
    public List<DocumentSearchDto> findByTitle(String title) {
        return findByField(title, LuceneConstants.TITLE_FIELD);
    }

    @Override
    public List<DocumentSearchDto> findByContent(String content) {
        return findByField(content, LuceneConstants.CONTENT_FIELD);
    }

    private List<DocumentSearchDto> findByField(String value, final String field) {
        User currentUser = securityFacade.getCurrentUser(context);

        try {
            setupSearcher();
            value = characterReplacer.replaceAll(value);
            try {
                return getSearchedDocuments(value, field, currentUser);
            } finally {
                indexReader.close();
            }
        } catch (Exception e) {
            handle(e);
            throw new InternalException();
        }
    }

    private List<DocumentSearchDto> getSearchedDocuments(String value, String field, User currentUser) throws IOException, ParseException {
        List<DocumentSearchDto> searchedDocuments = new LinkedList<>();
        TopDocs topDocs = getTopDocs(value, field);
        for(ScoreDoc scoreDoc : topDocs.scoreDocs) {
            Document document = indexSearcher.doc(scoreDoc.doc);
            StoredField idField = (StoredField) document.getField(LuceneConstants.ID_FIELD);
            int docId = idField.numericValue().intValue();
            try {
                if(downloadPrivilegeManager.canDownload(currentUser, docId)) {
                    DocumentSearchDto documentSearchDto = getDtoFromDocument(document);
                    searchedDocuments.add(documentSearchDto);
                }
            } catch (DocumentNotExistsException e) {
                logger.info("document has been removed: " + docId);
            }
        }
        return searchedDocuments;
    }

    private DocumentSearchDto getDtoFromDocument(Document document) {
        StoredField idField = (StoredField) document.getField(LuceneConstants.ID_FIELD);
        int docId = idField.numericValue().intValue();
        Field titleField = (Field) document.getField(LuceneConstants.TITLE_FIELD);
        String docTitle = titleField.stringValue();
        return new DocumentSearchDto(docId, docTitle);
    }

    private TopDocs getTopDocs(String value, String field) throws IOException, ParseException {
        Iterable<String> splitTitle = Splitter.on(Pattern.compile("\\s+")).omitEmptyStrings().trimResults().split(value);
        String titleQuery = Joiner.on("* *").join(splitTitle);
        Query query = prepareQuery(field, titleQuery);
        logger.info("parsed query for value <" + value + "> is: " + query);
        return indexSearcher.search(query, LuceneConstants.DEFAULT_HITS_NUMBER);
    }

    private Query prepareQuery(String field, String titleQuery) throws ParseException {
        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_44);
        QueryParser queryParser = new QueryParser(Version.LUCENE_44, field, analyzer);
        queryParser.setDefaultOperator(QueryParser.Operator.OR);
        queryParser.setAllowLeadingWildcard(true);
        return queryParser.parse("*" + titleQuery + "*");
    }
}
