package pl.edu.pw.elka.pfus.eds.web.rest;

import org.apache.log4j.Logger;
import pl.edu.pw.elka.pfus.eds.domain.entity.Comment;
import pl.edu.pw.elka.pfus.eds.logic.comment.CommentService;
import pl.edu.pw.elka.pfus.eds.logic.exception.LogicException;
import pl.edu.pw.elka.pfus.eds.web.rest.json.JsonCommentListExporter;
import pl.edu.pw.elka.pfus.eds.web.rest.json.JsonResultExporter;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static pl.edu.pw.elka.pfus.eds.web.rest.Rest.responseWithContent;

@Path("/comments")
public class CommentRest {
    private static final Logger logger = Logger.getLogger(CommentRest.class);

    private CommentService commentService;
    private JsonCommentListExporter commentListExporter;
    private JsonResultExporter resultExporter;

    @Inject
    public CommentRest(CommentService commentService, JsonCommentListExporter commentListExporter,
                       JsonResultExporter resultExporter) {
        this.commentService = commentService;
        this.commentListExporter = commentListExporter;
        this.resultExporter = resultExporter;
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

    @POST
    @Path("/create/{documentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createComment(@PathParam("documentId") int documentId, String content) {
        logger.info("commenting document " + documentId + " with content: " + content);
        String exported;
        try {
            commentService.addComment(documentId, content);
            exported = resultExporter.exportSuccess(null);
        } catch (LogicException e) {
            exported = resultExporter.exportFailure(e);
        }
        return responseWithContent(exported);
    }
}
