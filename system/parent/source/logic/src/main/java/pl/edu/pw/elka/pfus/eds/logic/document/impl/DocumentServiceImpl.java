package pl.edu.pw.elka.pfus.eds.logic.document.impl;

import pl.edu.pw.elka.pfus.eds.domain.entity.Document;
import pl.edu.pw.elka.pfus.eds.logic.document.DocumentDownloader;
import pl.edu.pw.elka.pfus.eds.logic.document.DocumentFinder;
import pl.edu.pw.elka.pfus.eds.logic.document.DocumentModifier;
import pl.edu.pw.elka.pfus.eds.logic.document.DocumentService;
import pl.edu.pw.elka.pfus.eds.logic.document.dto.DocumentNameBytesDto;

public class DocumentServiceImpl implements DocumentService {
    private DocumentFinder documentFinder;
    private DocumentModifier documentModifier;
    private DocumentDownloader documentDownloader;

    public DocumentServiceImpl(DocumentFinder documentFinder, DocumentModifier documentModifier,
                               DocumentDownloader documentDownloader) {
        this.documentFinder = documentFinder;
        this.documentModifier = documentModifier;
        this.documentDownloader = documentDownloader;
    }

    @Override
    public Document getById(int id) {
        return documentFinder.getById(id);
    }

    @Override
    public void create(String name, byte[] input) {
        documentModifier.create(name, input);
    }

    @Override
    public void rename(int documentId, String newName) {
        documentModifier.rename(documentId, newName);
    }

    @Override
    public void move(int documentId, int destinationDirectoryId) {
        documentModifier.move(documentId, destinationDirectoryId);
    }

    @Override
    public void delete(int documentId) {
        documentModifier.delete(documentId);
    }

    @Override
    public void cleanSessionDocuments() {
        documentModifier.cleanSessionDocuments();
    }

    @Override
    public DocumentNameBytesDto getDocumentNameAndBytesById(int documentId) {
        return documentDownloader.getDocumentNameAndBytesById(documentId);
    }
}
