package pl.edu.pw.elka.pfus.eds.web.rest;

import org.apache.log4j.Logger;
import pl.edu.pw.elka.pfus.eds.logic.search.Searcher;
import pl.edu.pw.elka.pfus.eds.web.rest.json.JsonTagListExporter;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/search")
public class SearchRest {
    private static final Logger logger = Logger.getLogger(SearchRest.class);
    private Searcher searcher;
    private JsonTagListExporter tagListExporter;

    @Inject
    public SearchRest(Searcher searcher, JsonTagListExporter tagListExporter) {
        this.searcher = searcher;
        this.tagListExporter = tagListExporter;
    }

    @GET
    @Path("/tags/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findTagsByName(@PathParam("name") String name) {
        String exported = tagListExporter.export(searcher.findTagsByName(name));
        return Response.status(Response.Status.OK).entity(exported).build();
    }
}
