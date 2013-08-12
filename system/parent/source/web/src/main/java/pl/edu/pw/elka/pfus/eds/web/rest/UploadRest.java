package pl.edu.pw.elka.pfus.eds.web.rest;

import com.google.common.io.ByteStreams;
import org.apache.log4j.Logger;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import pl.edu.pw.elka.pfus.eds.util.file.system.FileCreator;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;

@Path("/upload")
public class UploadRest {
    private static final Logger logger = Logger.getLogger(UploadRest.class);
    private FileCreator fileCreator;

    @Inject
    public UploadRest(FileCreator fileCreator) {
        this.fileCreator = fileCreator;
    }

    @POST
    public Response uploadFile(@FormDataParam("file") InputStream uploadedInputStream,
                               @FormDataParam("file")FormDataContentDisposition uploadedFileInfo) {
        logger.info("loading: " + uploadedFileInfo.getFileName());
        byte[] bytes = null;
        try {
            bytes = ByteStreams.toByteArray(uploadedInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileCreator.create(bytes, uploadedFileInfo.getFileName());
        return Response.status(Response.Status.OK).entity("{\"jsonrpc\" : \"2.0\", \"result\" : null, \"id\" : \"id\"}").build();
    }
}
