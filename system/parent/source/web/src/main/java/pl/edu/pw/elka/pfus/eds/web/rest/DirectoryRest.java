package pl.edu.pw.elka.pfus.eds.web.rest;

import org.apache.log4j.Logger;
import pl.edu.pw.elka.pfus.eds.domain.entity.Directory;
import pl.edu.pw.elka.pfus.eds.logic.directory.DirectoryFinder;
import pl.edu.pw.elka.pfus.eds.web.rest.json.JsonDirectoryListExporter;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/directories")
public class DirectoryRest {
    private static final Logger logger = Logger.getLogger(DirectoryRest.class);
    private DirectoryFinder directoryFinder;
    private JsonDirectoryListExporter directoryListExporter;

    @Inject
    public DirectoryRest(DirectoryFinder directoryFinder, JsonDirectoryListExporter directoryListExporter) {
        this.directoryFinder = directoryFinder;
        this.directoryListExporter = directoryListExporter;
    }

    @GET
    @Path("/root")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response getRootDirectories() {
        List<Directory> userRootDirectories = directoryFinder.getRootDirectories();
        String exportedRootDirectories = directoryListExporter.export(userRootDirectories);
        return Response.status(Response.Status.OK).entity(exportedRootDirectories).build();
    }
}
