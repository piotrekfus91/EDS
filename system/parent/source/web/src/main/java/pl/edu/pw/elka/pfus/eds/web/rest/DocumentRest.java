package pl.edu.pw.elka.pfus.eds.web.rest;

import org.apache.log4j.Logger;
import pl.edu.pw.elka.pfus.eds.domain.entity.Document;
import pl.edu.pw.elka.pfus.eds.logic.document.DocumentService;
import pl.edu.pw.elka.pfus.eds.logic.document.dto.DocumentNameBytesDto;
import pl.edu.pw.elka.pfus.eds.logic.exception.LogicException;
import pl.edu.pw.elka.pfus.eds.web.rest.json.JsonDocumentExporter;
import pl.edu.pw.elka.pfus.eds.web.rest.json.JsonResultExporter;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static pl.edu.pw.elka.pfus.eds.web.rest.Rest.responseWithContent;

@Path("/documents")
public class DocumentRest {
    private static final Logger logger = Logger.getLogger(DocumentRest.class);

    private DocumentService documentService;
    private JsonDocumentExporter documentExporter;
    private JsonResultExporter resultExporter;

    @Inject
    public DocumentRest(DocumentService documentService, JsonDocumentExporter documentExporter,
                        JsonResultExporter resultExporter) {
        this.documentService = documentService;
        this.documentExporter = documentExporter;
        this.resultExporter = resultExporter;
    }

    @GET
    @Path("/single/{documentId: \\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDocumentById(@PathParam("documentId") int documentId) {
        String exported;
        try {
            Document document = documentService.getById(documentId);
            exported = documentExporter.exportSuccess(document);
        } catch (LogicException e) {
            logger.error(e.getMessage(), e);
            exported = documentExporter.exportFailure(e);
        }
        return responseWithContent(exported);
    }

    @GET
    @Path("/download/{documentId: \\d+}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadById(@PathParam("documentId") int documentId) {
        DocumentNameBytesDto dto;
        try {
            dto = documentService.getDocumentNameAndBytesById(documentId);
            return Response.status(Response.Status.OK)
                    .header("Content-Disposition", "attachment; filename=" + dto.getName()).entity(dto.getBytes())
                    .build();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
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
        } catch (LogicException e) {
            logger.error(e.getMessage(), e);
            exported = resultExporter.exportFailure(e);
        }
        return responseWithContent(exported);
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
            exported = resultExporter.exportFailure(e);
        }
        return responseWithContent(exported);
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
            exported = resultExporter.exportFailure(e);
        }
        return responseWithContent(exported);
    }
}
