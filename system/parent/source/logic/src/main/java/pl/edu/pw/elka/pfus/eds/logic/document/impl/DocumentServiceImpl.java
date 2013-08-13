package pl.edu.pw.elka.pfus.eds.logic.document.impl;

import pl.edu.pw.elka.pfus.eds.logic.document.DocumentModifier;
import pl.edu.pw.elka.pfus.eds.logic.document.DocumentService;

public class DocumentServiceImpl implements DocumentService {
    private DocumentModifier documentModifier;

    public DocumentServiceImpl(DocumentModifier documentModifier) {
        this.documentModifier = documentModifier;
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
}
