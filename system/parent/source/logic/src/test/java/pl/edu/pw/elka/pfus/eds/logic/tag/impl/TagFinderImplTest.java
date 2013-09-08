package pl.edu.pw.elka.pfus.eds.logic.tag.impl;

import org.objectledge.context.Context;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pl.edu.pw.elka.pfus.eds.domain.dao.TagDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.Tag;
import pl.edu.pw.elka.pfus.eds.logic.document.DownloadPrivilegeManager;
import pl.edu.pw.elka.pfus.eds.logic.tag.cache.TagCache;
import pl.edu.pw.elka.pfus.eds.security.SecurityFacade;
import pl.edu.pw.elka.pfus.eds.util.config.Config;
import pl.edu.pw.elka.pfus.eds.util.config.impl.MapConfig;
import pl.edu.pw.elka.pfus.eds.util.word.distance.LevenshteinDistance;
import pl.edu.pw.elka.pfus.eds.util.word.distance.WordDistance;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TagFinderImplTest {
    private Config config;
    private TagFinderImpl tagFinder;
    private TagCache tagCache;
    private WordDistance distance;
    private TagDao tagDao;
    private DownloadPrivilegeManager downloadPrivilegeManager;
    private SecurityFacade securityFacade;
    private Context context;

    @BeforeClass
    private void setUpClass() {
        Map<String, String> configMap = new HashMap<>();
        configMap.put("max_distance", "3");
        config = new MapConfig(configMap);
    }

    @BeforeMethod
    private void setUp() {
        distance = new LevenshteinDistance();
        tagCache = mock(TagCache.class);
        tagDao = mock(TagDao.class);
        downloadPrivilegeManager = mock(DownloadPrivilegeManager.class);
        securityFacade = mock(SecurityFacade.class);
        context = mock(Context.class);
        tagFinder = new TagFinderImpl(config, tagCache, distance, tagDao, downloadPrivilegeManager,
                securityFacade, context);
    }

    @Test(dataProvider = "similars")
    public void testIsSimilarForSimilar(String str1, String str2) throws Exception {
        assertThat(tagFinder.isSimilar(str1, str2)).isTrue();
    }

    @DataProvider
    private Object[][] similars() {
        return new Object[][]{
                {"pies", "pies"},
                {"granat", "granit"},
                {"orczyk", "oracz"},
        };
    }

    @Test
    public void testIsSimilarForNonSimilar() throws Exception {
        assertThat(tagFinder.isSimilar("xarkax", "ariada")).isFalse();
    }

    @Test
    public void testGetSimilar() throws Exception {
        List<Tag> similarTags = new LinkedList<>();
        List<Tag> nonSimilarTags = new LinkedList<>();
        List<Tag> allTags = new LinkedList<>();
        Tag similarTag = new Tag();
        similarTag.setValue("G R Ą Ń Ą T Ó");
        similarTags.add(similarTag);
        Tag nonSimilar = new Tag();
        nonSimilar.setValue("zażółć gęślą jaźń");
        nonSimilarTags.add(nonSimilar);

        allTags.addAll(similarTags);
        allTags.addAll(nonSimilarTags);

        when(tagCache.getAll()).thenReturn(allTags);

        assertThat(tagFinder.getSimilars("granit")).containsExactly(similarTag);
    }
}
