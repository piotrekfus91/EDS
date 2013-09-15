package pl.edu.pw.elka.pfus.eds.web.rest;

import org.apache.log4j.Logger;
import pl.edu.pw.elka.pfus.eds.domain.entity.Tag;
import pl.edu.pw.elka.pfus.eds.logic.exception.LogicException;
import pl.edu.pw.elka.pfus.eds.logic.tag.TagService;
import pl.edu.pw.elka.pfus.eds.web.rest.json.JsonResultExporter;
import pl.edu.pw.elka.pfus.eds.web.rest.json.JsonTagExporter;
import pl.edu.pw.elka.pfus.eds.web.rest.json.JsonTagListExporter;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static pl.edu.pw.elka.pfus.eds.web.rest.Rest.responseWithContent;

@Path("/tags")
public class TagRest {
    private static final Logger logger = Logger.getLogger(TagRest.class);
    private TagService tagService;
    private JsonTagExporter tagExporter;
    private JsonTagListExporter tagListExporter;
    private JsonResultExporter resultExporter;

    @Inject
    public TagRest(TagService tagService, JsonTagExporter tagExporter, JsonTagListExporter tagListExporter,
                   JsonResultExporter resultExporter) {
        this.tagService = tagService;
        this.tagExporter = tagExporter;
        this.tagListExporter = tagListExporter;
        this.resultExporter = resultExporter;
    }

    @GET
    @Path("/name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByName(@PathParam("name") String name) {
        Tag tag = tagService.getTagWithLoadedDocuments(name);
        String exported = tagExporter.export(tag);
        return responseWithContent(exported);
    }

    @GET
    @Path("/autocomplete")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllForAutoComplete(@QueryParam("term") String word) {
        List<Tag> tags = tagService.getSimilars(word);
        String exported = tagListExporter.export(tags);
        return responseWithContent(exported);
    }

    @GET
    @Path("/tagCloud")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllForTagCloud() {
        List<Tag> tags = tagService.getAllWithLoadedDocuments();
        String exported = tagListExporter.export(tags);
        return responseWithContent(exported);
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
        return responseWithContent(exported);
    }
}
