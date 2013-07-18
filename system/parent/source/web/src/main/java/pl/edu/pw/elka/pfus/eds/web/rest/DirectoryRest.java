package pl.edu.pw.elka.pfus.eds.web.rest;

import org.apache.log4j.Logger;
import pl.edu.pw.elka.pfus.eds.logic.directory.DirectoryFinder;
import pl.edu.pw.elka.pfus.eds.util.Constants;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

public class DirectoryRest {
    private static final Logger logger = Logger.getLogger(DirectoryRest.class);
    private DirectoryFinder directoryFinder;

    @Inject
    public DirectoryRest(DirectoryFinder directoryFinder) {
        this.directoryFinder = directoryFinder;
    }

    public Response getResponse(@Context HttpServletRequest request) {
        return Response.status(Response.Status.OK).entity("" + request.getSession().getAttribute(Constants.LOGGED_USER) + this).build();
    }
}
