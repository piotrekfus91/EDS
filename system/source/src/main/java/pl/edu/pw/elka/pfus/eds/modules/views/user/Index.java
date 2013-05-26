package pl.edu.pw.elka.pfus.eds.modules.views.user;

import org.apache.log4j.Logger;
import org.objectledge.context.Context;
import org.objectledge.pipeline.ProcessingException;
import org.objectledge.templating.Template;
import org.objectledge.templating.TemplatingContext;
import org.objectledge.web.mvc.builders.BuildException;
import pl.edu.pw.elka.pfus.eds.domain.dao.UserDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.ol.extension.AbstractView;

import java.util.List;

public class Index extends AbstractView {
    private static final Logger logger = Logger.getLogger(Index.class);
    private UserDao userDao;

    public Index(Context context, UserDao userDao) {
        super(context);
        this.userDao = userDao;
    }

    @Override
    public String build(Template template, String embeddedBuildResults) throws BuildException, ProcessingException {
        TemplatingContext templatingContext = getTemplatingContext(context);
        List<User> users = userDao.getAllUsers();
        logger.info("Obtained: " + users);
        templatingContext.put("users", users);
        return super.build(template, embeddedBuildResults);
    }
}
