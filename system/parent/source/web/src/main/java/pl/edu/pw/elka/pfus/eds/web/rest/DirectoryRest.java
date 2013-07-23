package pl.edu.pw.elka.pfus.eds.web.rest;

import org.apache.log4j.Logger;
import pl.edu.pw.elka.pfus.eds.domain.entity.Directory;
import pl.edu.pw.elka.pfus.eds.domain.entity.FileSystemEntry;
import pl.edu.pw.elka.pfus.eds.logic.directory.DirectoryFinder;
import pl.edu.pw.elka.pfus.eds.web.rest.json.JsonDirectoryListExporter;
import pl.edu.pw.elka.pfus.eds.web.rest.json.JsonFileSystemEntryListExporter;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/directories")
public class DirectoryRest {
    private static final Logger logger = Logger.getLogger(DirectoryRest.class);
    private DirectoryFinder directoryFinder;
    private JsonDirectoryListExporter directoryListExporter;
    private JsonFileSystemEntryListExporter fileSystemEntryListExporter;

    @Inject
    public DirectoryRest(DirectoryFinder directoryFinder, JsonDirectoryListExporter directoryListExporter,
                         JsonFileSystemEntryListExporter fileSystemEntryListExporter) {
        this.directoryFinder = directoryFinder;
        this.directoryListExporter = directoryListExporter;
        this.fileSystemEntryListExporter = fileSystemEntryListExporter;
    }

    @GET
    @Path("/root")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRootDirectories() {
        List<Directory> userRootDirectories = directoryFinder.getRootDirectories();
        String exportedRootDirectories = directoryListExporter.export(userRootDirectories);
        return Response.status(Response.Status.OK).entity(exportedRootDirectories).build();
    }

    @GET
    @Path("/{directoryId: \\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDirectoriesById(@PathParam("directoryId")int directoryId) {
        List<FileSystemEntry> fileSystemEntriesOfDirectory = directoryFinder.getFileSystemEntries(directoryId);
        String exportedFileSystemEntries = fileSystemEntryListExporter.export(fileSystemEntriesOfDirectory);
        return Response.status(Response.Status.OK).entity(exportedFileSystemEntries).build();
    }
}
