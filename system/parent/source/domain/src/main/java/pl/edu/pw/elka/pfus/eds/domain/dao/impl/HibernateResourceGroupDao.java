package pl.edu.pw.elka.pfus.eds.domain.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
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

    private HibernateNamedDao<ResourceGroup> namedDao;

    public HibernateResourceGroupDao(Context context, SessionFactory sessionFactory) {
        super(context, sessionFactory);
        namedDao = new HibernateNamedDao<>(session, ResourceGroup.class);
    }

    public HibernateResourceGroupDao(Session session) {
        super(session);
        namedDao = new HibernateNamedDao<ResourceGroup>(session, ResourceGroup.class);
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
    public ResourceGroup findByName(String name) {
        return namedDao.findByName(name);
    }

    @Override
    protected Class<ResourceGroup> getEntityClass() {
        return ResourceGroup.class;
    }
}
