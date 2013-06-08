package pl.edu.pw.elka.pfus.eds.modules.actions.user;

import org.apache.log4j.Logger;
import org.hibernate.Transaction;
import org.objectledge.context.Context;
import org.objectledge.intake.IntakeContext;
import org.objectledge.intake.IntakeTool;
import org.objectledge.intake.model.Group;
import org.objectledge.parameters.RequestParameters;
import org.objectledge.pipeline.ProcessingException;
import org.objectledge.web.mvc.MVCContext;
import pl.edu.pw.elka.pfus.eds.domain.dao.UserDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.ol.extension.AbstractAction;

import java.util.Date;

public class Add extends AbstractAction {
    private static final Logger logger = Logger.getLogger(Add.class);

    private UserDao userDao;

    public Add(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void process(Context context) throws ProcessingException {
        IntakeTool intakeTool = IntakeContext.getIntakeContext(context).getIntakeTool();
        if(intakeTool.isAllValid()) {
            RequestParameters requestParameters = getRequestParameters(context);
            Transaction tx = getSession(context).beginTransaction();
            try {
                Group userGroup = intakeTool.get(User.class.getSimpleName(), IntakeTool.DEFAULT_KEY);
                User user;
                if(requestParameters.isDefined("id")) {
                    user = userDao.getById(requestParameters.getLong("id"));
                } else {
                    user = new User();
                    user.setRegistrationDate(new Date());
                }
                userGroup.setProperties(user);
                userDao.persist(user);
                tx.commit();
                getTemplatingContext(context).put("success", true);
                MVCContext.getMVCContext(context).setView("user.Index");
                MVCContext.getMVCContext(context).setAction("");
            } catch(Exception e) {
                tx.rollback();
                logger.error(e.getMessage(), e);
            }
        }
    }
}
