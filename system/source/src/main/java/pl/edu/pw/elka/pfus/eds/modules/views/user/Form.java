package pl.edu.pw.elka.pfus.eds.modules.views.user;

import org.apache.log4j.Logger;
import org.objectledge.context.Context;
import org.objectledge.intake.IntakeContext;
import org.objectledge.intake.IntakeException;
import org.objectledge.intake.IntakeTool;
import org.objectledge.intake.model.Group;
import org.objectledge.parameters.RequestParameters;
import org.objectledge.pipeline.ProcessingException;
import org.objectledge.templating.Template;
import org.objectledge.web.mvc.builders.BuildException;
import pl.edu.pw.elka.pfus.eds.domain.dao.UserDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.ol.extension.AbstractView;

public class Form extends AbstractView {
    private static final Logger logger = Logger.getLogger(Form.class);
    private UserDao userDao;

    public Form(Context context, UserDao userDao) {
        super(context);
        this.userDao = userDao;
    }

    @Override
    public String build(Template template, String embeddedBuildResults) throws BuildException, ProcessingException {
        RequestParameters requestParameters = getRequestParameters(context);
        IntakeTool intakeTool = IntakeContext.getIntakeContext(context).getIntakeTool();
        User user = new User();
        if(requestParameters.isDefined("edit")) {
            long id = requestParameters.getLong("edit");
            getTemplatingContext(context).put("id", id);
            user = userDao.getById(id);
            try {
                Group userGroup = intakeTool.get(User.class.getSimpleName(), IntakeTool.DEFAULT_KEY);
                userGroup.getProperties(user);
            } catch (IntakeException e) {
                logger.error(e.getMessage(), e);
                throw new ProcessingException(e);
            }
        }
        return super.build(template, embeddedBuildResults);
    }
}
