package pl.edu.pw.elka.pfus.eds.domain.entity;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

public class UserTest {
    private User user;

    @BeforeMethod
    private void setUp() {
        user = new User();
        user.setName("name");
    }

    @Test
    public void testGetFriendlyNameForNullFirstName() throws Exception {
        assertThat(user.getFriendlyName()).isEqualTo("name");
    }

    @Test
    public void testGetFriendlyNameForNotNullFirstNameAndNullLastName() throws Exception {
        user.setFirstName("first");

        assertThat(user.getFriendlyName()).isEqualTo("first");
    }

    @Test
    public void testGetFriendlyNameForNotNullFirstAndLastName() throws Exception {
        user.setFirstName("first");
        user.setLastName("last");

        assertThat(user.getFriendlyName()).isEqualTo("first last");
    }

    @Test
    public void testIsOwnerForNotOwner() throws Exception {
        Directory directory = new Directory();

        assertThat(user.isOwnerOfDirectory(directory)).isFalse();
    }

    @Test
    public void testIsOwnerForOwner() throws Exception {
        Directory directory = new Directory();
        directory.setOwner(user);

        assertThat(user.isOwnerOfDirectory(directory)).isTrue();
    }
}
