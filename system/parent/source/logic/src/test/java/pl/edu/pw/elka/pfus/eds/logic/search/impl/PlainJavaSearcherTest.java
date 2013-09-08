package pl.edu.pw.elka.pfus.eds.logic.search.impl;

import com.google.common.collect.Lists;
import org.objectledge.context.Context;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.edu.pw.elka.pfus.eds.domain.entity.Tag;
import pl.edu.pw.elka.pfus.eds.logic.document.DownloadPrivilegeManager;
import pl.edu.pw.elka.pfus.eds.logic.tag.cache.TagCache;
import pl.edu.pw.elka.pfus.eds.security.SecurityFacade;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlainJavaSearcherTest {
    private PlainJavaSearcher searcher;
    private DownloadPrivilegeManager downloadPrivilegeManager;
    private Context context;
    private SecurityFacade securityFacade;
    private TagCache tagCache;
    private Tag lfcTag;
    private Tag wallpaperTag;
    private Tag scholarTag;
    private Tag compositeTag;

    @BeforeMethod
    private void setUp() {
        tagCache = mock(TagCache.class);
        when(tagCache.getAll()).thenReturn(getSampleTags());
        downloadPrivilegeManager = mock(DownloadPrivilegeManager.class);
        context = mock(Context.class);
        securityFacade = mock(SecurityFacade.class);
        searcher = new PlainJavaSearcher(downloadPrivilegeManager, tagCache, context, securityFacade);
    }

    @Test
    public void testFindTagsByNameForEmptyName() throws Exception {
        assertThat(searcher.findTagsByName("")).isEmpty();
        assertThat(searcher.findTagsByName(null)).isEmpty();
    }

    @Test
    public void testFindTagsByNameForExactlyMatchingName() throws Exception {
        assertThat(searcher.findTagsByName("LFC")).containsExactly(lfcTag);
    }

    @Test
    public void testFindTagsByNameForMatchingName() throws Exception {
        assertThat(searcher.findTagsByName("L")).containsExactly(lfcTag, scholarTag, compositeTag);
    }

    @Test
    public void testFindTagsForCaseInsensitive() throws Exception {
        assertThat(searcher.findTagsByName("lf")).containsExactly(lfcTag);
    }

    @Test
    public void testFindTagsByNameForMultiWord() throws Exception {
        assertThat(searcher.findTagsByName("jest zlozona")).containsExactly(compositeTag);
    }

    private List<Tag> getSampleTags() {
        lfcTag = new Tag();
        lfcTag.setValue("LFC");

        wallpaperTag = new Tag();
        wallpaperTag.setValue("tapety");

        scholarTag = new Tag();
        scholarTag.setValue("szkolne");

        compositeTag = new Tag();
        compositeTag.setValue("to jest złożona nazwa zawierająca polskie litery");

        return Lists.newArrayList(lfcTag, wallpaperTag, scholarTag, compositeTag);
    }
}
