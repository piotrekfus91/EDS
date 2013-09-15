package pl.edu.pw.elka.pfus.eds.logic.error.handler;

import com.google.common.base.Throwables;
import org.apache.log4j.Logger;
import pl.edu.pw.elka.pfus.eds.domain.dao.Dao;
import pl.edu.pw.elka.pfus.eds.logic.exception.ConstraintsViolatedException;

public class ErrorHandler {
    private static final Logger logger = Logger.getLogger(ErrorHandler.class);

    public static void handle(Throwable t, Dao dao) {
        dao.rollbackTransaction();
        Throwables.propagateIfInstanceOf(t, ConstraintsViolatedException.class);
        logger.error(t.getMessage(), t);
    }
}
