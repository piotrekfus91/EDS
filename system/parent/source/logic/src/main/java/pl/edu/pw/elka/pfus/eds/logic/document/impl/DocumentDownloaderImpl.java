package pl.edu.pw.elka.pfus.eds.logic.document.impl;

import org.objectledge.context.Context;
import pl.edu.pw.elka.pfus.eds.domain.dao.DocumentDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.Document;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.logic.document.DocumentDownloader;
import pl.edu.pw.elka.pfus.eds.logic.document.dto.DocumentNameBytesDto;
import pl.edu.pw.elka.pfus.eds.logic.validator.LogicValidator;
import pl.edu.pw.elka.pfus.eds.security.SecurityFacade;
import pl.edu.pw.elka.pfus.eds.util.file.system.FileManager;

public class DocumentDownloaderImpl implements DocumentDownloader {
    private Context context;
    private SecurityFacade securityFacade;
    private DocumentDao documentDao;
    private FileManager fileManager;

    public DocumentDownloaderImpl(Context context, SecurityFacade securityFacade, DocumentDao documentDao,
                                  FileManager fileManager) {
        this.context = context;
        this.securityFacade = securityFacade;
        this.documentDao = documentDao;
        this.fileManager = fileManager;
    }

    @Override
    public DocumentNameBytesDto getDocumentNameAndBytesById(int documentId) {
        Document document = documentDao.findById(documentId);
        User currentUser = securityFacade.getCurrentUser(context);

        LogicValidator.validateOwnershipOverDocument(currentUser, document);

        byte[] bytes = fileManager.getAsByteArray(document.getFileSystemName(), document.getContentMd5());
        String name = document.getName();

        return new DocumentNameBytesDto(name, bytes);
    }
}
