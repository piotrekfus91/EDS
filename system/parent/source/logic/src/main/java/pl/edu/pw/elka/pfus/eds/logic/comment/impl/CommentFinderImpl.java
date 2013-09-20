package pl.edu.pw.elka.pfus.eds.logic.comment.impl;

import org.objectledge.context.Context;
import pl.edu.pw.elka.pfus.eds.domain.dao.CommentDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.Comment;
import pl.edu.pw.elka.pfus.eds.logic.comment.CommentFinder;
import pl.edu.pw.elka.pfus.eds.security.SecurityFacade;
import pl.edu.pw.elka.pfus.eds.security.privilege.PrivilegeService;

import java.util.List;

public class CommentFinderImpl implements CommentFinder {
    private Context context;
    private SecurityFacade securityFacade;
    private CommentDao commentDao;
    private PrivilegeService privilegeService;

    public CommentFinderImpl(Context context, SecurityFacade securityFacade, CommentDao commentDao,
                             PrivilegeService privilegeService) {
        this.context = context;
        this.securityFacade = securityFacade;
        this.commentDao = commentDao;
        this.privilegeService = privilegeService;
    }

    @Override
    public List<Comment> getCommentsForDocument(int documentId) {
        return commentDao.getCommentsForDocument(documentId);
    }
}
