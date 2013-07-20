package pl.edu.pw.elka.pfus.eds.domain.entity;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

public class DirectoryTest {

    private Directory directory;
    private Directory subdir;

    @BeforeMethod
    private void beforeMethod() {
        directory = new Directory();
        subdir = new Directory();
    }

    @Test
    public void empty() {
        assertThat(directory.isEmpty()).isTrue();
    }

    @Test(dependsOnMethods = "empty")
    public void notEmpty() {
        directory.addSubdirectory(subdir);

        assertThat(directory.isEmpty()).isFalse();
        assertThat(directory.size()).isEqualTo(1);
    }

    @Test(dependsOnMethods = "notEmpty")
    public void emptyAfterClear() {
        directory.addSubdirectory(subdir);
        directory.clear();

        assertThat(directory.isEmpty()).isTrue();
    }

    @Test
    public void testSetParent() throws Exception {
        subdir.setParentDirectory(directory);

        assertThat(subdir.getParentDirectory()).isSameAs(directory);
        assertThat(directory.getSubdirectories()).contains(subdir);
    }

    @Test
    public void testAddDirectory() throws Exception {
        directory.addSubdirectory(subdir);

        assertThat(subdir.getParentDirectory()).isSameAs(directory);
        assertThat(directory.getSubdirectories()).contains(subdir);
    }

    @Test
    public void testSetParentToNull() throws Exception {
        subdir.setParentDirectory(null);

        assertThat(directory.containsDirectory(subdir)).isFalse();
    }

    @Test
    public void testRemoveDirectory() throws Exception {
        directory.removeSubdirectory(subdir);

        assertThat(subdir.getParentDirectory()).isNull();
    }

    @Test
    public void testStringPathRootDir() throws Exception {
        assertThat(directory.getStringPath()).isEqualTo("/");
    }

    @Test
    public void testStringPathRootDirNotEmptyName() throws Exception {
        directory.setName("name");
        assertThat(directory.getStringPath()).isEqualTo("/name");
    }

    @Test
    public void testStringFullPath() throws Exception {
        directory.setName("parent");
        subdir.setName("child");
        directory.addSubdirectory(subdir);
        assertThat(subdir.getStringPath()).isEqualTo("/parent/child");
    }

    @Test
    public void testFindRootThisDirectory() throws Exception {
        assertThat(directory.findRoot()).isEqualTo(directory);
    }

    @Test
    public void testFindRootOfSubdirectory() throws Exception {
        directory.addSubdirectory(subdir);
        assertThat(subdir.findRoot()).isEqualTo(directory);
    }

    @Test
    public void testRecursiveOwner() throws Exception {
        directory.addSubdirectory(subdir);
        User user = new User();
        directory.setOwner(user);

        assertThat(directory.getOwner()).isSameAs(user);
        assertThat(subdir.getOwner()).isSameAs(user);
    }
}
