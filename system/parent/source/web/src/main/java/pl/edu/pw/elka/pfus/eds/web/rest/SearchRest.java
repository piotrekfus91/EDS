package pl.edu.pw.elka.pfus.eds.web.rest;

import org.apache.log4j.Logger;
import pl.edu.pw.elka.pfus.eds.logic.exception.LogicException;
import pl.edu.pw.elka.pfus.eds.logic.search.SearchService;
import pl.edu.pw.elka.pfus.eds.logic.search.dto.DocumentSearchDto;
import pl.edu.pw.elka.pfus.eds.web.rest.json.JsonDocumentSearchListExporter;
import pl.edu.pw.elka.pfus.eds.web.rest.json.JsonTagListExporter;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static pl.edu.pw.elka.pfus.eds.web.rest.Rest.responseWithContent;

@Path("/search")
public class SearchRest {
    private static final Logger logger = Logger.getLogger(SearchRest.class);

    private SearchService searchService;
    private JsonTagListExporter tagListExporter;
    private JsonDocumentSearchListExporter documentSearchListExporter;

    @Inject
    public SearchRest(SearchService searchService, JsonTagListExporter tagListExporter,
                      JsonDocumentSearchListExporter documentSearchListExporter) {
        this.searchService = searchService;
        this.tagListExporter = tagListExporter;
        this.documentSearchListExporter = documentSearchListExporter;
    }

    @GET
    @Path("/tags/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findTagsByName(@PathParam("name") String name) {
        String exported = tagListExporter.export(searchService.findTagsByName(name));
        return responseWithContent(exported);
    }

    @GET
    @Path("/title/{title}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findDocumentsByTitle(@PathParam("title") String title) {
        logger.info("searching for title: " + title);
        try {
            List<DocumentSearchDto> searchedDocuments = searchService.findByTitle(title);
            return responseFromSearchResults(searchedDocuments);
        } catch (LogicException e) {
            return responseWithContent(documentSearchListExporter.exportFailure(e));
        }
    }

    @GET
    @Path("/content/{content}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findDocumentsByContent(@PathParam("content") String content) {
        logger.info("searching for content: " + content);
        try {
            List<DocumentSearchDto> searchedDocuments = searchService.findByContent(content);
            return responseFromSearchResults(searchedDocuments);
        } catch (LogicException e) {
            return responseWithContent(documentSearchListExporter.exportFailure(e));
        }
    }

    private Response responseFromSearchResults(List<DocumentSearchDto> searchedDocuments) {
        String exported = documentSearchListExporter.exportSuccess(searchedDocuments);
        return responseWithContent(exported);
    }
}
