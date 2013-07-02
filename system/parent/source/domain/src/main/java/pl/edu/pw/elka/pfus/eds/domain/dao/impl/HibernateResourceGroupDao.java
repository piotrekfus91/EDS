package pl.edu.pw.elka.pfus.eds.domain.dao.impl;

import org.objectledge.context.Context;
import pl.edu.pw.elka.pfus.eds.domain.dao.IdentifableGenericDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.ResourceGroupDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.ResourceGroup;
import pl.edu.pw.elka.pfus.eds.domain.session.SessionFactory;

public class HibernateResourceGroupDao extends IdentifableGenericDao<ResourceGroup> implements ResourceGroupDao {
    public HibernateResourceGroupDao(Context context, SessionFactory sessionFactory) {
        super(context, sessionFactory);
    }

    @Override
    protected Class<ResourceGroup> getEntityClass() {
        return ResourceGroup.class;
    }
}
