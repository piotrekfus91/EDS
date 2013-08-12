package pl.edu.pw.elka.pfus.eds.web.rest;

import org.apache.log4j.Logger;
import pl.edu.pw.elka.pfus.eds.logic.document.DocumentService;
import pl.edu.pw.elka.pfus.eds.logic.exception.LogicException;
import pl.edu.pw.elka.pfus.eds.web.rest.json.JsonResultExporter;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/documents")
public class DocumentRest {
    private static final Logger logger = Logger.getLogger(DocumentRest.class);

    private DocumentService documentService;
    private JsonResultExporter resultExporter;

    @Inject
    public DocumentRest(DocumentService documentService, JsonResultExporter resultExporter) {
        this.documentService = documentService;
        this.resultExporter = resultExporter;
    }

    @PUT
    @Path("/rename/{documentId: \\d+}/{newName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response renameDocument(@PathParam("documentId") int documentId,
                                   @PathParam("newName") String newName) {
        logger.info("renaming document " + documentId + " to " + newName);
        String exported;
        try {
            documentService.rename(documentId, newName);
            exported = resultExporter.exportSuccess(null);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            exported = resultExporter.exportFailure(e.getMessage(), null);
        }
        return Response.status(Response.Status.OK).entity(exported).build();
    }

    @PUT
    @Path("/move/{documentId: \\d+}/{destinationDirectoryId: \\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response moveDocument(@PathParam("documentId") int documentId,
                                 @PathParam("destinationDirectoryId") int destinationDirectoryId) {
        logger.info("moving " + documentId + " to " + destinationDirectoryId);
        String exported;
        try {
            documentService.move(documentId, destinationDirectoryId);
            exported = resultExporter.exportSuccess(null);
        } catch (LogicException e) {
            logger.error(e.getMessage(), e);
            exported = resultExporter.exportFailure(e.getMessage(), null);
        }
        return Response.status(Response.Status.OK).entity(exported).build();
    }

    @DELETE
    @Path("/delete/{documentId: \\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteDocument(@PathParam("documentId") int documentId) {
        logger.info("deleting document: " + documentId);
        String exported;
        try {
            documentService.delete(documentId);
            exported = resultExporter.exportSuccess(null);
        } catch (LogicException e) {
            logger.error(e.getMessage(), e);
            exported = resultExporter.exportFailure(e.getMessage(), null);
        }
        return Response.status(Response.Status.OK).entity(exported).build();
    }
}
