package pl.edu.pw.elka.pfus.eds.web.servlet;

import org.apache.log4j.Logger;
import org.picocontainer.MutablePicoContainer;
import pl.edu.pw.elka.pfus.eds.logic.document.DocumentService;
import pl.edu.pw.elka.pfus.eds.web.container.ContainerFactory;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionDocumentsSessionListener implements HttpSessionListener {
    private static final Logger logger = Logger.getLogger(SessionDocumentsSessionListener.class);

    @Override
    public void sessionCreated(HttpSessionEvent se) {
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        logger.info("deleting session");
        MutablePicoContainer container = ContainerFactory.getContainer();
        DocumentService documentService = (DocumentService) container.getComponentInstance(DocumentService.class);
        documentService.cleanSessionDocuments();
    }
}
