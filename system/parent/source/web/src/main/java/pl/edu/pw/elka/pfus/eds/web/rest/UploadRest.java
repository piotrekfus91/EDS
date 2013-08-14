package pl.edu.pw.elka.pfus.eds.web.rest;

import com.google.common.io.ByteStreams;
import org.apache.log4j.Logger;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import pl.edu.pw.elka.pfus.eds.logic.document.DocumentService;
import pl.edu.pw.elka.pfus.eds.web.rest.json.JsonPluploadExporter;
import pl.edu.pw.elka.pfus.eds.web.rest.json.dto.plupload.PluploadJsonDto;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;

@Path("/upload")
public class UploadRest {
    private static final Logger logger = Logger.getLogger(UploadRest.class);
    private JsonPluploadExporter pluploadExporter;
    private DocumentService documentService;

    @Inject
    public UploadRest(JsonPluploadExporter pluploadExporter, DocumentService documentService) {
        this.pluploadExporter = pluploadExporter;
        this.documentService = documentService;
    }

    @POST
    public Response uploadFile(@FormDataParam("file") InputStream uploadedInputStream,
                               @FormDataParam("file")FormDataContentDisposition uploadedFileInfo) {
        logger.info("loading: " + uploadedFileInfo.getFileName());
        String exported;
        try {
            byte[] bytes = ByteStreams.toByteArray(uploadedInputStream);
            documentService.create(uploadedFileInfo.getFileName(), bytes);
            exported = pluploadExporter.export(new PluploadJsonDto());
        } catch (IOException e) {
            exported = pluploadExporter.export(new PluploadJsonDto(e.getMessage()));
            logger.error(e.getMessage(), e);
        }
        return Response.status(Response.Status.OK).entity(exported).build();
    }
}
