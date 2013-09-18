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
import pl.edu.pw.elka.pfus.eds.logic.exception.InternalException;
import pl.edu.pw.elka.pfus.eds.logic.search.NationalCharacterReplacer;
import pl.edu.pw.elka.pfus.eds.logic.search.Searcher;
import pl.edu.pw.elka.pfus.eds.logic.search.dto.DocumentSearchDto;
import pl.edu.pw.elka.pfus.eds.util.config.Config;
import pl.edu.pw.elka.pfus.eds.util.file.system.PathHelper;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class LuceneSearcher implements Searcher {
    private static final Logger logger = Logger.getLogger(LuceneSearcher.class);

    private String indexDir;
    private IndexReader indexReader;
    private IndexSearcher indexSearcher;
    private Directory directory;
    private NationalCharacterReplacer characterReplacer;

    public LuceneSearcher(Config config, NationalCharacterReplacer characterReplacer) throws IOException {
        this.characterReplacer = characterReplacer;
        indexDir = config.getString("index_dir");
        indexDir = PathHelper.countFileSystemRoot(indexDir);
        directory = FSDirectory.open(new File(indexDir));
    }

    public LuceneSearcher(Directory directory, NationalCharacterReplacer characterReplacer) {
        this.directory = directory;
        this.characterReplacer = characterReplacer;
    }

    private void setupIndexReaderAndSearcher() throws IOException {
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

    private List<DocumentSearchDto> findByField(String value, final String FIELD) {
        try {
            setupIndexReaderAndSearcher();
            value = characterReplacer.replaceAll(value);
            try {
                List<DocumentSearchDto> searchedDocuments = new LinkedList<>();
                TopDocs topDocs = getTopDocs(value, FIELD);
                for(ScoreDoc scoreDoc : topDocs.scoreDocs) {
                    Document document = indexSearcher.doc(scoreDoc.doc);
                    DocumentSearchDto documentSearchDto = getDtoFromDocument(document);
                    searchedDocuments.add(documentSearchDto);
                }
                return searchedDocuments;
            } finally {
                indexReader.close();
            }
        } catch (IOException | ParseException e) {
            logger.error(e.getMessage(), e);
            throw new InternalException();
        }
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
        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_44);
        QueryParser queryParser = new QueryParser(Version.LUCENE_44, field, analyzer);
        queryParser.setDefaultOperator(QueryParser.Operator.OR);
        queryParser.setAllowLeadingWildcard(true);
        Query query = queryParser.parse("*" + titleQuery + "*");
        logger.info("parsed query for value <" + value + "> is: " + query);
        return indexSearcher.search(query, LuceneConstants.DEFAULT_HITS_NUMBER);
    }
}
