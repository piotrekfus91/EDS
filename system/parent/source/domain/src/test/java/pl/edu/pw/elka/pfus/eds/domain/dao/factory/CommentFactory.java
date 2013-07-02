package pl.edu.pw.elka.pfus.eds.domain.dao.factory;

import pl.edu.pw.elka.pfus.eds.domain.entity.Comment;

import java.util.Date;

public class CommentFactory implements EntityFactory<Comment> {
    private UserFactory userFactory = new UserFactory();
    private DocumentFactory documentFactory = new DocumentFactory();

    @Override
    public Comment getSampleEntity() {
        Comment comment = new Comment();
        comment.setContent("content");
        comment.setCreated(new Date());
        comment.setUser(userFactory.getSampleEntity());
        comment.setDocument(documentFactory.getSampleEntity());
        return comment;
    }

}
