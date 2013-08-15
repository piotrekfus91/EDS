package pl.edu.pw.elka.pfus.eds.logic.document.impl;

import pl.edu.pw.elka.pfus.eds.domain.entity.Document;
import pl.edu.pw.elka.pfus.eds.logic.document.DocumentFinder;
import pl.edu.pw.elka.pfus.eds.logic.document.DocumentModifier;
import pl.edu.pw.elka.pfus.eds.logic.document.DocumentService;

public class DocumentServiceImpl implements DocumentService {
    private DocumentFinder documentFinder;
    private DocumentModifier documentModifier;

    public DocumentServiceImpl(DocumentFinder documentFinder, DocumentModifier documentModifier) {
        this.documentFinder = documentFinder;
        this.documentModifier = documentModifier;
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
}
