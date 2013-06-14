package pl.edu.pw.elka.pfus.eds.modules.actions.user;

import org.apache.log4j.Logger;
import org.hibernate.Transaction;
import org.objectledge.context.Context;
import org.objectledge.intake.IntakeContext;
import org.objectledge.intake.IntakeTool;
import org.objectledge.intake.model.Group;
import org.objectledge.parameters.RequestParameters;
import org.objectledge.pipeline.ProcessingException;
import pl.edu.pw.elka.pfus.eds.domain.dao.UserDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.ol.extension.AbstractAction;

public class Edit extends AbstractAction {
    private static final Logger logger = Logger.getLogger(Edit.class);

    private UserDao userDao;

    public Edit(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void process(Context context) throws ProcessingException {
        RequestParameters requestParameters = getRequestParameters(context);
        IntakeTool intakeTool = IntakeContext.getIntakeContext(context).getIntakeTool();
        if(intakeTool.isAllValid()) {
            Transaction tx = getSession(context).beginTransaction();
            try {
                Group userGroup = intakeTool.get(User.class.getSimpleName(), IntakeTool.DEFAULT_KEY);
                Long id = (Long) userGroup.get("Id").getValue();
                User user = userDao.getById(id);
                userGroup.setProperties(user);
                tx.commit();
                getTemplatingContext(context).put("success", true);
            } catch(Exception e) {
                tx.rollback();
                logger.error(e.getMessage(), e);
                getTemplatingContext(context).put("success", false);
            }
        }
    }

}
