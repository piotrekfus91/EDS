package pl.edu.pw.elka.pfus.eds.logic.validator;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.edu.pw.elka.pfus.eds.domain.entity.Directory;
import pl.edu.pw.elka.pfus.eds.domain.entity.Document;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.logic.exception.InvalidPrivilegesException;
import pl.edu.pw.elka.pfus.eds.logic.exception.ObjectNotFoundException;

import static org.fest.assertions.Assertions.assertThat;

public class LogicValidatorTest {
    private Directory directory;
    private Document document;
    private User user;
    
    @BeforeMethod
    private void setUp() {
        directory = new Directory();
        document = new Document();
        user = new User();
    }
    
    @Test
    public void testIsMoveToSameDirectoryExpectedTrue() throws Exception {
        directory.setId(1);

        assertThat(LogicValidator.isMoveToSameDirectory(1, directory)).isTrue();
    }

    @Test
    public void testIsMoveToSameDirectoryExpectedFalse() throws Exception {
        directory.setId(1);

        assertThat(LogicValidator.isMoveToSameDirectory(2, directory)).isFalse();
    }

    @Test
    public void testValidateOwnershipOverDocumentNoEx() throws Exception {
        document.setOwner(user);

        LogicValidator.validateOwnershipOverDocument(user, document);

        assertThat(true).isTrue();
    }

    @Test(expectedExceptions = InvalidPrivilegesException.class)
    public void testValidateOwnershipOverDocumentEx() throws Exception {
        LogicValidator.validateOwnershipOverDocument(user, document);
    }

    @Test
    public void testValidateOwnershipOverDirectoryNoEx() throws Exception {
        directory.setOwner(user);

        LogicValidator.validateOwnershipOverDirectory(user, directory);

        assertThat(true).isTrue();
    }

    @Test(expectedExceptions = InvalidPrivilegesException.class)
    public void testValidateOwnershipOverDirectoryEx() throws Exception {
        LogicValidator.validateOwnershipOverDirectory(user, directory);
    }

    @Test
    public void testValidateExistenceNoEx() throws Exception {
        LogicValidator.validateExistence(document);

        assertThat(true).isTrue();
    }

    @Test(expectedExceptions = ObjectNotFoundException.class)
    public void testValidateExistenceEx() throws Exception {
        LogicValidator.validateExistence(null);
    }

    @Test
    public void testIsFileWithNameInDirectoryForNoSuchFile() throws Exception {
        document.setName("name1");
        Document document2 = new Document();
        document2.setName("name2");
        directory.addDocument(document);
        directory.addDocument(document2);

        assertThat(LogicValidator.isFileWithNameInDirectory(directory, "name3")).isFalse();
    }

    @Test
    public void testIsFileWithNameInDirectoryForFile() throws Exception {
        document.setName("name");
        directory.addDocument(document);

        assertThat(LogicValidator.isFileWithNameInDirectory(directory, "name")).isTrue();
    }
}
