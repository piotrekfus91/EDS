package pl.edu.pw.elka.pfus.eds.logic.document.impl;

import org.objectledge.context.Context;
import pl.edu.pw.elka.pfus.eds.domain.dao.DocumentDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.Document;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.logic.document.DocumentFinder;
import pl.edu.pw.elka.pfus.eds.logic.validator.LogicValidator;
import pl.edu.pw.elka.pfus.eds.security.SecurityFacade;

public class DocumentFinderImpl implements DocumentFinder {
    private DocumentDao documentDao;
    private SecurityFacade securityFacade;
    private Context context;

    public DocumentFinderImpl(Context context, DocumentDao documentDao, SecurityFacade securityFacade) {
        this.context = context;
        this.documentDao = documentDao;
        this.securityFacade = securityFacade;
    }

    @Override
    public Document getById(int id) {
        User currentUser = securityFacade.getCurrentUser(context);
        Document document = documentDao.findById(id);

        LogicValidator.validateOwnershipOverDocument(currentUser, document);

        document.getComments().size(); // aby zaladowac z bazy to co jest lazy
        document.getTags().size();
        return document;
    }
}
