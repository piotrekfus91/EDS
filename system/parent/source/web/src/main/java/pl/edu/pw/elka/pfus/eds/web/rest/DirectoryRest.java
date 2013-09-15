package pl.edu.pw.elka.pfus.eds.web.rest;

import org.apache.log4j.Logger;
import pl.edu.pw.elka.pfus.eds.domain.entity.Directory;
import pl.edu.pw.elka.pfus.eds.domain.entity.FileSystemEntry;
import pl.edu.pw.elka.pfus.eds.logic.directory.DirectoryService;
import pl.edu.pw.elka.pfus.eds.logic.exception.LogicException;
import pl.edu.pw.elka.pfus.eds.web.rest.json.JsonDirectoryExporter;
import pl.edu.pw.elka.pfus.eds.web.rest.json.JsonFileSystemEntryListExporter;
import pl.edu.pw.elka.pfus.eds.web.rest.json.JsonResultExporter;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static pl.edu.pw.elka.pfus.eds.web.rest.Rest.responseWithContent;

@Path("/directories")
public class DirectoryRest {
    private static final Logger logger = Logger.getLogger(DirectoryRest.class);
    private DirectoryService directoryService;
    private JsonFileSystemEntryListExporter fileSystemEntryListExporter;
    private JsonDirectoryExporter directoryExporter;
    private JsonResultExporter resultExporter;

    @Inject
    public DirectoryRest(DirectoryService directoryService, JsonFileSystemEntryListExporter fileSystemEntryListExporter,
                         JsonDirectoryExporter directoryExporter, JsonResultExporter resultExporter) {
        this.directoryService = directoryService;
        this.fileSystemEntryListExporter = fileSystemEntryListExporter;
        this.directoryExporter = directoryExporter;
        this.resultExporter = resultExporter;
    }

    @GET
    @Path("/root")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRootDirectoryAndSessionFiles() {
        List<FileSystemEntry> result = directoryService.getRootDirectoryAndSessionDocuments();
        String exported = fileSystemEntryListExporter.exportSuccess(result);
        return responseWithContent(exported);
    }

    @GET
    @Path("/{directoryId: \\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDirectoriesById(@PathParam("directoryId") int directoryId) {
        List<FileSystemEntry> fileSystemEntriesOfDirectory = directoryService.getFileSystemEntries(directoryId);
        String exportedFileSystemEntries = fileSystemEntryListExporter.exportSuccess(fileSystemEntriesOfDirectory);
        return responseWithContent(exportedFileSystemEntries);
    }

    @GET
    @Path("/single/{directoryId: \\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSingleDirectoryById(@PathParam("directoryId") int directoryId) {
        Directory directory = directoryService.getById(directoryId);
        String exportedDirectory = directoryExporter.exportSuccess(directory);
        return responseWithContent(exportedDirectory);
    }

    @POST
    @Path("/create/{parentDirectoryId: \\d+}/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addDirectory(@PathParam("parentDirectoryId") int parentDirectoryId,
                                 @PathParam("name") String name) {
        String exported;
        Directory directory = null;
        try {
            directory = directoryService.add(parentDirectoryId, name);
            exported = directoryExporter.exportSuccess(directory);
        } catch (LogicException e) {
            logger.error(e.getMessage(), e);
            exported = directoryExporter.exportFailure(e);
        }
        return responseWithContent(exported);
    }

    @PUT
    @Path("/move/{directoryId: \\d+}/{destinationDirectoryId: \\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response moveDirectory(@PathParam("directoryId") int directoryId,
                                  @PathParam("destinationDirectoryId") int destinationDirectoryId) {
        String exported;
        try {
            directoryService.move(directoryId, destinationDirectoryId);
            exported = resultExporter.exportSuccess(null);
        } catch (LogicException e) {
            logger.error(e.getMessage(), e);
            exported = resultExporter.exportFailure(e);
        }
        return responseWithContent(exported);
    }

    @DELETE
    @Path("/delete/{directoryId: \\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteDirectoryById(@PathParam("directoryId") int directoryId) {
        String exported;
        try {
            Directory parentDirectory = directoryService.delete(directoryId);
            List<FileSystemEntry> fileSystemEntries = parentDirectory.getFileSystemEntries();
            exported = fileSystemEntryListExporter.exportSuccess(fileSystemEntries);
        } catch (LogicException e) {
            logger.error(e.getMessage(), e);
            exported = fileSystemEntryListExporter.exportFailure(e);
        }
        return responseWithContent(exported);
    }

    @PUT
    @Path("/rename/{directoryId: \\d+}/{newName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response renameDirectory(@PathParam("directoryId") int directoryId, @PathParam("newName") String newName) {
        String exported;
        try {
            Directory renamedDirectory = directoryService.rename(directoryId, newName);
            exported = directoryExporter.exportSuccess(renamedDirectory);
        } catch (LogicException e) {
            logger.error(e.getMessage(), e);
            exported = directoryExporter.exportFailure(e);
        }
        return responseWithContent(exported);
    }
}
