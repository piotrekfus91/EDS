package pl.edu.pw.elka.pfus.eds.web.rest;

import pl.edu.pw.elka.pfus.eds.domain.entity.Tag;
import pl.edu.pw.elka.pfus.eds.logic.tag.TagService;
import pl.edu.pw.elka.pfus.eds.web.rest.json.JsonTagListExporter;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/tags")
public class TagRest {
    private TagService tagService;
    private JsonTagListExporter tagListExporter;

    @Inject
    public TagRest(TagService tagService, JsonTagListExporter tagListExporter) {
        this.tagService = tagService;
        this.tagListExporter = tagListExporter;
    }

    @GET
    @Path("/autocomplete")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllForAutoComplete(@QueryParam("term") String word) {
        List<Tag> tags = tagService.getSimilars(word);
        String exported = tagListExporter.export(tags);
        return Response.status(Response.Status.OK).entity(exported).build();
    }
}
