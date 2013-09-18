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
        logger.info("indexing document: " + document.getName());
        setupIndexWriter();
        org.apache.lucene.document.Document luceneDocument = new org.apache.lucene.document.Document();

        Integer id = document.getId();
        String title = document.getName();
        String content = characterReplacer.replaceAll(extractor.extract(document));

        luceneDocument.add(new IntField(LuceneConstants.ID_FIELD, id, Field.Store.YES));
        luceneDocument.add(new StringField(LuceneConstants.TITLE_FIELD, title, Field.Store.YES));
        luceneDocument.add(new StringField(LuceneConstants.CONTENT_FIELD, content, Field.Store.YES));

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
