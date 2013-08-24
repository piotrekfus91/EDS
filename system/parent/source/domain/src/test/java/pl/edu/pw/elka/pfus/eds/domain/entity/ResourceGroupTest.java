package pl.edu.pw.elka.pfus.eds.domain.entity;

import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

public class ResourceGroupTest {
    @Test
    public void testGetAllDocuments() throws Exception {
        Directory parent = new Directory();
        Directory subdir = new Directory();
        parent.addSubdirectory(subdir);
        subdir.setParentDirectory(parent);

        Document inParent = new Document();
        Document inSubdir = new Document();
        inParent.setDirectory(parent);
        parent.addDocument(inParent);
        inSubdir.setDirectory(subdir);
        subdir.addDocument(inSubdir);

        Document common = new Document();

        ResourceGroup resourceGroup = new ResourceGroup();
        resourceGroup.addDirectory(parent);
        resourceGroup.addDocument(common);

        assertThat(resourceGroup.getAllDocuments()).containsOnly(inParent, inSubdir, common);
    }
}
