package pl.edu.pw.elka.pfus.eds.web.rest;

import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import pl.edu.pw.elka.pfus.eds.domain.entity.Directory;
import pl.edu.pw.elka.pfus.eds.domain.entity.FileSystemEntry;
import pl.edu.pw.elka.pfus.eds.logic.directory.DirectoryService;
import pl.edu.pw.elka.pfus.eds.logic.exception.LogicException;
import pl.edu.pw.elka.pfus.eds.web.rest.json.JsonDirectoryExporter;
import pl.edu.pw.elka.pfus.eds.web.rest.json.JsonDirectoryListExporter;
import pl.edu.pw.elka.pfus.eds.web.rest.json.JsonFileSystemEntryListExporter;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/directories")
public class DirectoryRest {
    private static final Logger logger = Logger.getLogger(DirectoryRest.class);
    private DirectoryService directoryService;
    private JsonDirectoryListExporter directoryListExporter;
    private JsonFileSystemEntryListExporter fileSystemEntryListExporter;
    private JsonDirectoryExporter directoryExporter;

    @Inject
    public DirectoryRest(DirectoryService directoryService, JsonDirectoryListExporter directoryListExporter,
                         JsonFileSystemEntryListExporter fileSystemEntryListExporter,
                         JsonDirectoryExporter directoryExporter) {
        this.directoryService = directoryService;
        this.directoryListExporter = directoryListExporter;
        this.fileSystemEntryListExporter = fileSystemEntryListExporter;
        this.directoryExporter = directoryExporter;
    }

    @GET
    @Path("/root")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRootDirectories() {
        Directory userRootDirectory = directoryService.getRootDirectory();
        List<Directory> userRootDirectories = Lists.newArrayList(userRootDirectory);
        String exportedRootDirectories = directoryListExporter.exportSuccess(userRootDirectories);
        return Response.status(Response.Status.OK).entity(exportedRootDirectories).build();
    }

    @GET
    @Path("/{directoryId: \\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDirectoriesById(@PathParam("directoryId") int directoryId) {
        List<FileSystemEntry> fileSystemEntriesOfDirectory = directoryService.getFileSystemEntries(directoryId);
        String exportedFileSystemEntries = fileSystemEntryListExporter.exportSuccess(fileSystemEntriesOfDirectory);
        return Response.status(Response.Status.OK).entity(exportedFileSystemEntries).build();
    }

    @GET
    @Path("/single/{directoryId: \\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSingleDirectoryById(@PathParam("directoryId") int directoryId) {
        Directory directory = directoryService.getById(directoryId);
        String exportedDirectory = directoryExporter.exportSuccess(directory);
        return Response.status(Response.Status.OK).entity(exportedDirectory).build();
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
            exported = directoryExporter.exportFailure(e.getMessage(), null);
        }
        return Response.status(Response.Status.OK).entity(exported).build();
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
            exported = fileSystemEntryListExporter.exportFailure(e.getMessage(), null);
        }
        return Response.status(Response.Status.OK).entity(exported).build();
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
            exported = directoryExporter.exportFailure(e.getMessage(), null);
        }
        return Response.status(Response.Status.OK).entity(exported).build();
    }
}
