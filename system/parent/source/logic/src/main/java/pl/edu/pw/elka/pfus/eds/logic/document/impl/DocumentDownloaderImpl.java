package pl.edu.pw.elka.pfus.eds.logic.document.impl;

import org.apache.log4j.Logger;
import org.objectledge.context.Context;
import pl.edu.pw.elka.pfus.eds.domain.dao.DocumentDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.Document;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.logic.document.DocumentDownloader;
import pl.edu.pw.elka.pfus.eds.logic.document.DownloadPrivilegeManager;
import pl.edu.pw.elka.pfus.eds.logic.document.dto.DocumentNameBytesDto;
import pl.edu.pw.elka.pfus.eds.logic.exception.InvalidPrivilegesException;
import pl.edu.pw.elka.pfus.eds.security.SecurityFacade;
import pl.edu.pw.elka.pfus.eds.util.file.system.FileManager;

public class DocumentDownloaderImpl implements DocumentDownloader {
    private static final Logger logger = Logger.getLogger(DocumentDownloaderImpl.class);

    private Context context;
    private SecurityFacade securityFacade;
    private DocumentDao documentDao;
    private FileManager fileManager;
    private DownloadPrivilegeManager downloadPrivilegeManager;

    public DocumentDownloaderImpl(Context context, SecurityFacade securityFacade, DocumentDao documentDao,
                                  FileManager fileManager, DownloadPrivilegeManager downloadPrivilegeManager) {
        this.context = context;
        this.securityFacade = securityFacade;
        this.documentDao = documentDao;
        this.fileManager = fileManager;
        this.downloadPrivilegeManager = downloadPrivilegeManager;
    }

    @Override
    public DocumentNameBytesDto getDocumentNameAndBytesById(int documentId) {
        Document document = documentDao.getDocumentWithGroups(documentId);
        User currentUser = securityFacade.getCurrentUser(context);
        logger.info(currentUser.getFriendlyName() + " is attempting to download file " + document.getName());

        if(!downloadPrivilegeManager.canDownload(currentUser, document))
            throw new InvalidPrivilegesException();

        byte[] bytes = fileManager.getAsByteArray(document.getFileSystemName(), document.getContentMd5());
        String name = document.getName();

        return new DocumentNameBytesDto(name, bytes);
    }
}
