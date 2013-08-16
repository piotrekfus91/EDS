package pl.edu.pw.elka.pfus.eds.web.rest;

import org.apache.log4j.Logger;
import pl.edu.pw.elka.pfus.eds.domain.entity.Tag;
import pl.edu.pw.elka.pfus.eds.logic.exception.LogicException;
import pl.edu.pw.elka.pfus.eds.logic.tag.TagService;
import pl.edu.pw.elka.pfus.eds.web.rest.json.JsonResultExporter;
import pl.edu.pw.elka.pfus.eds.web.rest.json.JsonTagListExporter;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/tags")
public class TagRest {
    private static final Logger logger = Logger.getLogger(TagRest.class);
    private TagService tagService;
    private JsonTagListExporter tagListExporter;
    private JsonResultExporter resultExporter;

    @Inject
    public TagRest(TagService tagService, JsonTagListExporter tagListExporter, JsonResultExporter resultExporter) {
        this.tagService = tagService;
        this.tagListExporter = tagListExporter;
        this.resultExporter = resultExporter;
    }

    @GET
    @Path("/autocomplete")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllForAutoComplete(@QueryParam("term") String word) {
        List<Tag> tags = tagService.getSimilars(word);
        String exported = tagListExporter.export(tags);
        return Response.status(Response.Status.OK).entity(exported).build();
    }

    @PUT
    @Path("/tagsToDocument/{documentId: \\d+}/{commaSeparatedTagList}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTagsOfDocument(@PathParam("documentId") int documentId,
                                         @PathParam("commaSeparatedTagList") String commaSeparatedTagList) {
        String exported;
        try {
            tagService.addTagsToDocument(documentId, commaSeparatedTagList);
            exported = resultExporter.exportSuccess(null);
        } catch (LogicException e) {
            logger.error(e.getMessage(), e);
            exported = resultExporter.exportFailure(e.getMessage(), null);
        }
        return Response.status(Response.Status.OK).entity(exported).build();
    }
}
