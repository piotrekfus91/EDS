package pl.edu.pw.elka.pfus.eds.web.rest;

import org.apache.log4j.Logger;
import pl.edu.pw.elka.pfus.eds.logic.directory.DirectoryFinder;
import pl.edu.pw.elka.pfus.eds.util.Constants;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/directories")
public class DirectoryRest {
    private static final Logger logger = Logger.getLogger(DirectoryRest.class);
    private DirectoryFinder finder;

    @Inject
    public DirectoryRest(DirectoryFinder finder) {
        this.finder = finder;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getResponse(@Context HttpServletRequest request) {
        return Response.status(Response.Status.OK).entity(request.getSession().getAttribute(Constants.LOGGED_USER)).build();
    }
}
