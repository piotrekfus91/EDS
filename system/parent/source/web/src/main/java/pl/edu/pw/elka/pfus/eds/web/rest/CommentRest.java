package pl.edu.pw.elka.pfus.eds.web.rest;

import org.apache.log4j.Logger;
import pl.edu.pw.elka.pfus.eds.domain.entity.Comment;
import pl.edu.pw.elka.pfus.eds.logic.comment.CommentService;
import pl.edu.pw.elka.pfus.eds.web.rest.json.JsonCommentListExporter;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static pl.edu.pw.elka.pfus.eds.web.rest.Rest.responseWithContent;

@Path("/comments")
public class CommentRest {
    private static final Logger logger = Logger.getLogger(CommentRest.class);

    private CommentService commentService;
    private JsonCommentListExporter commentListExporter;

    @Inject
    public CommentRest(CommentService commentService, JsonCommentListExporter commentListExporter) {
        this.commentService = commentService;
        this.commentListExporter = commentListExporter;
    }

    @GET
    @Path("/document/{documentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCommentsForDocument(@PathParam("documentId" )int documentId) {
        logger.info("retrieving comments for document: " + documentId);
        List<Comment> comments = commentService.getCommentsForDocument(documentId);
        String exported = commentListExporter.exportSuccess(comments);
        return responseWithContent(exported);
    }
}
