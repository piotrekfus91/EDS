package pl.edu.pw.elka.pfus.eds.logic.search.impl;

import org.apache.log4j.Logger;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.WriteOutContentHandler;
import pl.edu.pw.elka.pfus.eds.domain.entity.Document;
import pl.edu.pw.elka.pfus.eds.logic.exception.IndexingException;
import pl.edu.pw.elka.pfus.eds.logic.search.Extractor;
import pl.edu.pw.elka.pfus.eds.util.file.system.FileManager;

import java.io.InputStream;
import java.io.StringWriter;

public class TikaExtractor implements Extractor {
    private static final Logger logger = Logger.getLogger(TikaExtractor.class);

    private FileManager fileManager;

    public TikaExtractor(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    @Override
    public String extract(Document document) {
        String fileName = document.getFileSystemName();
        String hash = document.getContentMd5();
        InputStream inputStream = fileManager.getAsStream(fileName, hash);
        return extract(inputStream);
    }

    @Override
    public String extract(InputStream inputStream) {
        Parser parser = new AutoDetectParser();
        Metadata metadata = new Metadata();
        StringWriter writer = new StringWriter();

        try {
            parser.parse(inputStream, new WriteOutContentHandler(writer), metadata, new ParseContext());
            logger.debug("extracted: " + writer.toString());
            return writer.toString().replaceAll("\\s+", " ");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new IndexingException();
        }
    }
}
