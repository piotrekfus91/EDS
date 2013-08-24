package pl.edu.pw.elka.pfus.eds.domain.dao.impl;

import org.hibernate.Query;
import org.objectledge.context.Context;
import pl.edu.pw.elka.pfus.eds.domain.dao.IdentifableGenericDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.ResourceGroupDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.ResourceGroup;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.domain.session.SessionFactory;

import java.util.List;

public class HibernateResourceGroupDao extends IdentifableGenericDao<ResourceGroup> implements ResourceGroupDao {
    private static final String ALL_OF_FOUNDER_QUERY =
            "SELECT rg " +
            "FROM ResourceGroup rg " +
            "WHERE rg.founder.id = :userId";

    public HibernateResourceGroupDao(Context context, SessionFactory sessionFactory) {
        super(context, sessionFactory);
    }

    @Override
    public List<ResourceGroup> getAllOfFounder(User user) {
        return getAllOfFounder(user.getId());
    }

    @Override
    public List<ResourceGroup> getAllOfFounder(int userId) {
        Query query = session.createQuery(ALL_OF_FOUNDER_QUERY);
        query.setInteger("userId", userId);
        return query.list();
    }

    @Override
    protected Class<ResourceGroup> getEntityClass() {
        return ResourceGroup.class;
    }
}
