package pl.edu.pw.elka.pfus.eds.logic.search.impl;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import pl.edu.pw.elka.pfus.eds.domain.entity.Document;
import pl.edu.pw.elka.pfus.eds.logic.search.Indexer;
import pl.edu.pw.elka.pfus.eds.util.config.Config;
import pl.edu.pw.elka.pfus.eds.util.file.system.PathHelper;

import java.io.File;
import java.io.IOException;

public class LuceneIndexer implements Indexer {
    private static final Logger logger = Logger.getLogger(LuceneIndexer.class);

    private String indexDir;
    private IndexWriter indexWriter;
    private Directory directory;

    public LuceneIndexer(Config config) throws IOException {
        indexDir = config.getString("index_dir");
        indexDir = PathHelper.countFileSystemRoot(indexDir);
        directory = FSDirectory.open(new File(indexDir));
    }

    public LuceneIndexer(Directory directory) {
        this.directory = directory;
    }

    @Override
    public void index(Document document) throws IOException {
        logger.info("indexing document: " + document.getName());
        setupIndexWriter();
        org.apache.lucene.document.Document luceneDocument = new org.apache.lucene.document.Document();
        luceneDocument.add(new IntField(LuceneConstants.ID_FIELD, document.getId(), Field.Store.YES));
        luceneDocument.add(new StringField(LuceneConstants.TITLE_FIELD, document.getName(), Field.Store.YES));
        indexWriter.addDocument(luceneDocument);
        indexWriter.commit();
        indexWriter.close();
    }

    private void setupIndexWriter() throws IOException {
        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_44);
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_44, analyzer);
        indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        indexWriter = new IndexWriter(directory, indexWriterConfig);
    }
}
