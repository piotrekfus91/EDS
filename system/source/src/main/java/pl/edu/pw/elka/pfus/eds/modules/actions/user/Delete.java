package pl.edu.pw.elka.pfus.eds.modules.actions.user;

import org.apache.log4j.Logger;
import org.hibernate.Transaction;
import org.objectledge.context.Context;
import org.objectledge.parameters.RequestParameters;
import org.objectledge.pipeline.ProcessingException;
import pl.edu.pw.elka.pfus.eds.domain.dao.UserDao;
import pl.edu.pw.elka.pfus.eds.ol.extension.AbstractAction;

public class Delete extends AbstractAction {
    private static final Logger logger = Logger.getLogger(Delete.class);

    private UserDao userDao;

    public Delete(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void process(Context context) throws ProcessingException {
        RequestParameters requestParameters = getRequestParameters(context);
        Long id = requestParameters.getLong("delete");
        Transaction tx = getSession(context).beginTransaction();
        try {
            userDao.deleteById(id);
        } catch(Exception e) {
            tx.rollback();
            logger.error(e.getMessage(), e);
        }
    }
}
