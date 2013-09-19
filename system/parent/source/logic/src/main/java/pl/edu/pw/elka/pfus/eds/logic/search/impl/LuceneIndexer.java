package pl.edu.pw.elka.pfus.eds.logic.search.impl;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.*;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import pl.edu.pw.elka.pfus.eds.domain.entity.Document;
import pl.edu.pw.elka.pfus.eds.logic.exception.IndexingException;
import pl.edu.pw.elka.pfus.eds.logic.search.Extractor;
import pl.edu.pw.elka.pfus.eds.logic.search.Indexer;
import pl.edu.pw.elka.pfus.eds.logic.search.NationalCharacterReplacer;
import pl.edu.pw.elka.pfus.eds.util.config.Config;
import pl.edu.pw.elka.pfus.eds.util.file.system.PathHelper;

import java.io.File;
import java.io.IOException;

public class LuceneIndexer implements Indexer {
    private static final Logger logger = Logger.getLogger(LuceneIndexer.class);

    private String indexDir;
    private IndexWriter indexWriter;
    private IndexSearcher indexSearcher;
    private IndexReader indexReader;
    private Directory directory;
    private Extractor extractor;
    private NationalCharacterReplacer characterReplacer;

    public LuceneIndexer(Config config, Extractor extractor, NationalCharacterReplacer characterReplacer)
            throws IOException {
        this.extractor = extractor;
        this.characterReplacer = characterReplacer;
        indexDir = config.getString("index_dir");
        indexDir = PathHelper.countFileSystemRoot(indexDir);
        directory = FSDirectory.open(new File(indexDir));
    }

    public LuceneIndexer(Directory directory, Extractor extractor, NationalCharacterReplacer characterReplacer) {
        this.directory = directory;
        this.extractor = extractor;
        this.characterReplacer = characterReplacer;
    }

    @Override
    public void index(Document document) throws IOException {
        try {
            logger.info("indexing document: " + document.getName());
            setupIndexer();
            org.apache.lucene.document.Document luceneDocument = getLuceneDocument(document);

            indexWriter.addDocument(luceneDocument);
        } finally {
            commitAndClose();
        }
    }

    @Override
    public void updateIndex(Document document) throws IOException {
        try {
            logger.info("update index for document: " + document.getName());
            setupIndexer();
            setupSearcher();

            Term term = getTermById(document.getId());
            org.apache.lucene.document.Document luceneDocument = findDocumentWithTerm(term);
            StringField titleField = (StringField) luceneDocument.getField(LuceneConstants.TITLE_FIELD);
            titleField.setStringValue(document.getName());
            indexWriter.updateDocument(term, luceneDocument);
        } finally {
            commitAndClose();
        }
    }

    @Override
    public void deleteFromIndex(int documentId) throws IOException {
        try {
            logger.info("delete from index: " + documentId);
            setupIndexer();
            setupSearcher();

            Term term = getTermById(documentId);
            indexWriter.deleteDocuments(term);
        } finally {
            commitAndClose();
        }
    }

    private void commitAndClose() throws IOException {
        indexWriter.commit();
        if(indexReader != null)
            indexReader.close();
        indexWriter.close();
    }

    private org.apache.lucene.document.Document getLuceneDocument(Document document) {
        org.apache.lucene.document.Document luceneDocument = new org.apache.lucene.document.Document();

        Integer id = document.getId();
        String title = document.getName();
        String content = characterReplacer.replaceAll(extractor.extract(document));

        luceneDocument.add(new IntField(LuceneConstants.ID_FIELD, id, Field.Store.YES));
        luceneDocument.add(new StringField(LuceneConstants.TITLE_FIELD, title, Field.Store.YES));
        luceneDocument.add(new StringField(LuceneConstants.CONTENT_FIELD, content, Field.Store.YES));
        return luceneDocument;
    }

    private void setupIndexer() throws IOException {
        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_44);
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_44, analyzer);
        indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        indexWriter = new IndexWriter(directory, indexWriterConfig);
    }

    private void setupSearcher() throws IOException {
        indexReader = DirectoryReader.open(directory);
        indexSearcher = new IndexSearcher(indexReader);
    }

    private org.apache.lucene.document.Document findDocumentWithTerm(Term term) throws IOException {
        TermQuery query = new TermQuery(term);
        TopDocs topDocs = indexSearcher.search(query, 10);
        if(topDocs.scoreDocs.length != 1)
            throw new IndexingException();
        return indexSearcher.doc(topDocs.scoreDocs[0].doc);
    }

    private Term getTermById(int id) {
        return new Term(LuceneConstants.ID_FIELD, ""+id);
    }
}
