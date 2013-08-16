package pl.edu.pw.elka.pfus.eds.util.word.distance;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

public class LevenshteinDistanceTest {
    private LevenshteinDistance distance;

    @BeforeMethod
    private void setUp() {
        distance = new LevenshteinDistance();
    }

    @Test(dataProvider = "distances")
    public void testDistance(String str1, String str2, int expectedDistance) throws Exception {
        assertThat(distance.distance(str1, str2)).isEqualTo(expectedDistance);
    }

    @DataProvider
    private Object[][] distances() {
        return new Object[][]{
                {"pies", "pies", 0},
                {"granat", "granit", 1},
                {"orczyk", "oracz", 3},
                {"marka", "ariada", 4},
                {"kitten", "sitting", 3},
                {"sunday", "saturday", 3}
        };
    }

    @Test
    public void testInitDistanceMatrix() throws Exception {
        int[][] matrix = distance.initDistanceMatrix("ab", "abc");

        assertThat(matrix.length).isEqualTo("ab".length() + 1);
        for(int[] row : matrix) {
            assertThat(row.length).isEqualTo("abc".length() + 1);
        }
    }

    @Test(dataProvider = "equalsCosts")
    public void testCountCostForEquals(String str1, int str1Post, String str2, int str2Pos) throws Exception {
        assertThat(distance.countCost(str1, str1Post, str2, str2Pos)).isEqualTo(0);
    }

    @DataProvider
    private Object[][] equalsCosts() {
        return new Object[][]{
                {"abc", 1, "abc", 1},
                {"abc", 2, "abc", 2},
                {"abc", 3, "abc", 3},
        };
    }

    @Test(dataProvider = "nonEqualsCosts")
    public void testCountCostForNonEquals(String str1, int str1Pos, String str2, int str2Pos) throws Exception {
        assertThat(distance.countCost(str1, str1Pos, str2, str2Pos)).isEqualTo(1);
    }

    @DataProvider
    private Object[][] nonEqualsCosts() {
        return new Object[][]{
                {"abc", 1, "cab", 1},
                {"abc", 2, "cab", 2},
                {"abc", 3, "cab", 3},
        };
    }
}
