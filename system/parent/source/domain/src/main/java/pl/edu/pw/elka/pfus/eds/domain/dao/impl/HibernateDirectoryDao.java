package pl.edu.pw.elka.pfus.eds.domain.dao.impl;

import org.hibernate.Query;
import org.objectledge.context.Context;
import pl.edu.pw.elka.pfus.eds.domain.dao.DirectoryDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.IdentifableGenericDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.Directory;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.domain.session.SessionFactory;

import java.util.List;

public class HibernateDirectoryDao extends IdentifableGenericDao<Directory> implements DirectoryDao {
    public HibernateDirectoryDao(Context context, SessionFactory sessionFactory) {
        super(context, sessionFactory);
    }

    @Override
    public List<Directory> getRootDirectories(User user) {
        return getRootDirectories(user.getId());
    }

    @Override
    public List<Directory> getRootDirectories(int userId) {
        String ROOT_DIRECTORIES_QUERY =
                "SELECT dir " +
                "FROM Directory dir " +
                        "JOIN dir.owner own " +
                "WHERE own.id = :userId " +
                        "AND dir.parentDirectory IS NULL";
        Query query = session.createQuery(ROOT_DIRECTORIES_QUERY);
        query.setInteger("userId", userId);
        return query.list();
    }

    @Override
    public List<Directory> getSubdirectories(Directory directory) {
        return getSubdirectories(directory.getId());
    }

    @Override
    public List<Directory> getSubdirectories(int directoryId) {
        String SUBDIRECTORIES_QUERY =
                "SELECT dir " +
                "FROM Directory dir " +
                "WHERE dir.parentDirectory.id = :directoryId";
        Query query = session.createQuery(SUBDIRECTORIES_QUERY);
        query.setInteger("directoryId", directoryId);
        return query.list();
    }

    @Override
    public Directory getDirectoryWithSubdirectoriesAndOwner(int directoryId) {
        String DIRECTORY_WITH_SUBDIRECTORIES_AND_OWNER =
                "SELECT dir " +
                "FROM Directory dir " +
                        "JOIN dir.owner " +
                        "JOIN dir.subdirectories " +
                "WHERE dir.id = :directoryId";
        Query query = session.createQuery(DIRECTORY_WITH_SUBDIRECTORIES_AND_OWNER);
        query.setParameter("directoryId", directoryId);
        return (Directory) query.uniqueResult();
    }

    @Override
    protected Class<Directory> getEntityClass() {
        return Directory.class;
    }
}
