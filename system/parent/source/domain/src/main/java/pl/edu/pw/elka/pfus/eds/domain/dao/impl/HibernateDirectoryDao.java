package pl.edu.pw.elka.pfus.eds.domain.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.objectledge.context.Context;
import pl.edu.pw.elka.pfus.eds.domain.dao.DirectoryDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.IdentifableGenericDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.Directory;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.domain.session.SessionFactory;
import pl.edu.pw.elka.pfus.eds.domain.validator.EntityValidator;

import java.util.List;

public class HibernateDirectoryDao extends IdentifableGenericDao<Directory> implements DirectoryDao {
    private static final String ROOT_DIRECTORIES_QUERY =
            "SELECT dir " +
            "FROM Directory dir " +
                    "JOIN dir.owner own " +
            "WHERE own.id = :userId " +
                    "AND dir.parentDirectory IS NULL " +
            "ORDER BY dir.name";

    private static final String SUBDIRECTORIES_QUERY =
            "SELECT dir " +
            "FROM Directory dir " +
            "WHERE dir.parentDirectory.id = :directoryId " +
            "ORDER BY dir.name";

    private static final String DIRECTORY_WITH_SUBDIRECTORIES_AND_OWNER =
            "SELECT dir " +
            "FROM Directory dir " +
                    "JOIN dir.owner " +
                    "LEFT JOIN dir.subdirectories AS subdirs " +
            "WHERE dir.id = :directoryId " +
            "ORDER BY subdirs.name";

    private static final String DIRECTORIES_WITH_SUBDIRECTORIES_FILES_AND_OWNER =
            "SELECT dir " +
            "FROM Directory dir " +
                "JOIN dir.owner " +
                "LEFT JOIN dir.subdirectories AS subdirs " +
                "LEFT JOIN dir.documents AS docs " +
            "WHERE dir.id = :directoryId " +
            "ORDER BY subdirs.name, docs.name";

    public HibernateDirectoryDao(Context context, SessionFactory sessionFactory, EntityValidator validator) {
        super(context, sessionFactory, validator);
    }

    public HibernateDirectoryDao(Session session, EntityValidator validator) {
        super(session, validator);
    }

    @Override
    public Directory getRootDirectory(User user) {
        return getRootDirectory(user.getId());
    }

    @Override
    public Directory getRootDirectory(int userId) {
        Query query = session.createQuery(ROOT_DIRECTORIES_QUERY);
        query.setInteger("userId", userId);
        return (Directory) query.uniqueResult();
    }

    @Override
    public List<Directory> getSubdirectories(Directory directory) {
        return getSubdirectories(directory.getId());
    }

    @Override
    public List<Directory> getSubdirectories(int directoryId) {
        Query query = session.createQuery(SUBDIRECTORIES_QUERY);
        query.setInteger("directoryId", directoryId);
        return query.list();
    }

    @Override
    public Directory getDirectoryWithSubdirectoriesAndOwner(int directoryId) {
        Query query = session.createQuery(DIRECTORY_WITH_SUBDIRECTORIES_AND_OWNER);
        query.setParameter("directoryId", directoryId);
        return (Directory) query.uniqueResult();
    }

    @Override
    public Directory getDirectoryWithFileSystemEntriesAndOwner(int directoryId) {
        Query query = session.createQuery(DIRECTORIES_WITH_SUBDIRECTORIES_FILES_AND_OWNER);
        query.setInteger("directoryId", directoryId);
        return (Directory) query.uniqueResult();
    }

    @Override
    protected Class<Directory> getEntityClass() {
        return Directory.class;
    }
}
