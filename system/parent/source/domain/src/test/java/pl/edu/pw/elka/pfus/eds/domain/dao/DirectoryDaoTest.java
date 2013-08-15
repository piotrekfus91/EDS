package pl.edu.pw.elka.pfus.eds.domain.dao;

import org.objectledge.context.Context;
import org.testng.annotations.Test;
import pl.edu.pw.elka.pfus.eds.domain.dao.factory.DirectoryFactory;
import pl.edu.pw.elka.pfus.eds.domain.dao.factory.EntityFactory;
import pl.edu.pw.elka.pfus.eds.domain.dao.factory.UserFactory;
import pl.edu.pw.elka.pfus.eds.domain.dao.impl.HibernateDirectoryDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.Directory;
import pl.edu.pw.elka.pfus.eds.domain.entity.Document;
import pl.edu.pw.elka.pfus.eds.domain.entity.MimeType;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.domain.session.SessionFactory;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class DirectoryDaoTest extends IdentifableDaoTest<Directory, DirectoryDao> {
    private DirectoryDao directoryDao;
    private DirectoryFactory factory = new DirectoryFactory();
    private UserFactory userFactory = new UserFactory();

    @Test
    public void testWithSubdirectories() throws Exception {
        Directory directory = getSampleEntity();
        Directory subdirectory = getSampleEntity();
        directory.addSubdirectory(subdirectory);

        directoryDao.persist(directory);

        Directory newDirectory = directoryDao.findById(directory.getId());
        assertThat(newDirectory).isEqualTo(directory);
    }

    @Test
    public void testRootDirectoriesForUser() throws Exception {
        User user = userFactory.getSampleEntity();
        Directory firstRoot = factory.getSampleEntity();
        firstRoot.setName("firstRoot");
        firstRoot.setOwner(user);
        Directory firstChild = factory.getSampleEntity();
        firstChild.setName("firstChild");
        firstChild.setParentDirectory(firstRoot);
        firstChild.setOwner(user);
        directoryDao.persist(firstRoot);

        Directory expectedOnlyRoots = directoryDao.getRootDirectory(user.getId());

        assertThat(expectedOnlyRoots).isEqualTo(firstRoot);
    }

    @Test
    public void testSubdirectories() throws Exception {
        Directory parent = getFreeLevelStructure();
        List<Directory> expectedSubdirectories = new LinkedList(parent.getSubdirectories());
        List<Directory> actualSubdirectories = directoryDao.getSubdirectories(parent);

        assertThat(actualSubdirectories).isEqualTo(expectedSubdirectories);
    }

    @Test
    public void testWithSubsAndOwner() throws Exception {
        Directory expected = getFreeLevelStructure();

        Directory actual = directoryDao.getDirectoryWithSubdirectoriesAndOwner(expected.getId());
        assertThat(actual).isEqualTo(expected);
        assertThat(actual.getSubdirectories()).isEqualTo(expected.getSubdirectories());
        assertThat(actual.getOwner()).isEqualTo(expected.getOwner());
    }

    @Test
    public void testWithFileSystemEntriesAndOwner() throws Exception {
        Directory expected = getFreeLevelStructure();

        Directory actual = directoryDao.getDirectoryWithFileSystemEntriesAndOwner(expected.getId());
        assertThat(actual).isEqualTo(expected);
        assertThat(actual.getSubdirectories()).isEqualTo(expected.getSubdirectories());
        assertThat(actual.getDocuments()).isEqualTo(expected.getDocuments());
        assertThat(actual.getOwner()).isEqualTo(expected.getOwner());
    }

    @Override
    public DirectoryDao getDao() {
        return directoryDao;
    }

    @Override
    public void setDao(DirectoryDao dao) {
        this.directoryDao = dao;
    }

    @Override
    protected void prepareDao(SessionFactory sessionFactory, Context context) {
        directoryDao = new HibernateDirectoryDao(context, sessionFactory);
    }

    @Override
    protected EntityFactory<Directory> getFactory() {
        return factory;
    }

    @Override
    protected void updateEntity(Directory entity) {
        entity.setName(entity.getName() + entity.getName());
    }

    private Directory getFreeLevelStructure() {
        User user = userFactory.getSampleEntity();
        Directory parentDir = new Directory();
        parentDir.setName("parent");
        parentDir.setOwner(user);
        MimeType mimeType = new MimeType();
        mimeType.setDefaultExtension(".ext");
        mimeType.setType("word");
        Document document = new Document();
        document.setName("document");
        document.setCreated(new Date());
        document.setMimeType(mimeType);
        document.setContentMd5("md5:)");
        parentDir.addDocument(document);
        Directory firstSubdir = new Directory();
        firstSubdir.setName("firstSubdir");
        firstSubdir.setOwner(user);
        firstSubdir.setParentDirectory(parentDir);
        Directory secondSubdir = new Directory();
        secondSubdir.setName("secondSubdir");
        secondSubdir.setOwner(user);
        secondSubdir.setParentDirectory(parentDir);
        Directory subsubdir = new Directory();
        subsubdir.setName("subsubdir");
        subsubdir.setOwner(user);
        subsubdir.setParentDirectory(firstSubdir);
        directoryDao.persist(parentDir);
        return parentDir;
    }
}
