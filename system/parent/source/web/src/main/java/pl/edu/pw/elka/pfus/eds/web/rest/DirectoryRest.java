package pl.edu.pw.elka.pfus.eds.web.rest;

import org.apache.log4j.Logger;
import pl.edu.pw.elka.pfus.eds.logic.directory.DirectoryFinder;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

@Path("/directories")
public class DirectoryRest {
    private static final Logger logger = Logger.getLogger(DirectoryRest.class);
    private DirectoryFinder directoryFinder;

    @Inject
    public DirectoryRest(DirectoryFinder directoryFinder) {
        this.directoryFinder = directoryFinder;
    }

    @GET
    public Response getResponse(@Context HttpServletRequest request) {
        return Response.status(Response.Status.OK).entity("jersey works correctly").build();
    }
}
