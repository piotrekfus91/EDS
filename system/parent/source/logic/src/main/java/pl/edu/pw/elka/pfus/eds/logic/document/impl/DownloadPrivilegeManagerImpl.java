package pl.edu.pw.elka.pfus.eds.logic.document.impl;

import pl.edu.pw.elka.pfus.eds.domain.dao.DocumentDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.Document;
import pl.edu.pw.elka.pfus.eds.domain.entity.ResourceGroup;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.logic.document.DownloadPrivilegeManager;
import pl.edu.pw.elka.pfus.eds.security.privilege.PrivilegeService;
import pl.edu.pw.elka.pfus.eds.security.privilege.Privileges;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class DownloadPrivilegeManagerImpl implements DownloadPrivilegeManager {
    private PrivilegeService privilegeService;
    private DocumentDao documentDao;

    public DownloadPrivilegeManagerImpl(PrivilegeService privilegeService, DocumentDao documentDao) {
        this.privilegeService = privilegeService;
        this.documentDao = documentDao;
    }

    @Override
    public boolean canDownload(User user, Document document) {
        if(user.isOwnerOfDocument(document))
            return true;

        if (isAccessibleByAnyGroup(user, document))
            return true;

        return false;
    }

    @Override
    public boolean canDownload(User user, int documentId) {
        Document document = documentDao.findById(documentId);
        return canDownload(user, document);
    }

    @Override
    public List<Document> filterOutInaccessibleDocuments(User user, List<Document> documents) {
        List<Document> filtered = new LinkedList<>();
        for(Document document : documents) {
            if(canDownload(user, document))
                filtered.add(document);
        }
        return filtered;
    }

    private boolean isAccessibleByAnyGroup(User user, Document document) {
        Set<ResourceGroup> documentGroups = document.getAllResourceGroups();
        for(ResourceGroup resourceGroup : documentGroups) {
            if(isAccessibleByGroup(resourceGroup, user))
                return true;
        }
        return false;
    }

    private boolean isAccessibleByGroup(ResourceGroup resourceGroup, User user) {
        return privilegeService.hasPrivilege(user.getName(), Privileges.DOWNLOAD_FILES, resourceGroup.getName());
    }
}
