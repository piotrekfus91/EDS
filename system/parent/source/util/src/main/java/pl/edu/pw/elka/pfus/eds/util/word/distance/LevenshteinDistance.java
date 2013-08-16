package pl.edu.pw.elka.pfus.eds.util.word.distance;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.primitives.Ints;

/**
 * Implementacja polegająca na algorytmie dystansu Levensteina.<br /><br />
 *
 * <a href="http://pl.wikipedia.org/wiki/Odleg%C5%82o%C5%9B%C4%87_Levenshteina">Opis</a>
 */
public class LevenshteinDistance implements WordDistance {
    @Override
    public int distance(String str1, String str2) {
        int[][] distanceMatrix = initDistanceMatrix(str1, str2);
        fullFillMatrix(str1, str2, distanceMatrix);
        return distanceMatrix[str1.length()][str2.length()];
    }

    private void fullFillMatrix(String str1, String str2, int[][] distanceMatrix) {
        for(int i = 1; i < str1.length() + 1; i++) {
            for(int j = 1; j < str2.length() + 1; j++) {
                int cost = countCost(str1, i, str2, j);

                distanceMatrix[i][j] = Ints.min(
                        distanceMatrix[i - 1][j] + 1,
                        distanceMatrix[i][j - 1] + 1,
                        distanceMatrix[i - 1][j - 1] + cost
                );
            }
        }
    }

    @VisibleForTesting
    int[][] initDistanceMatrix(String str1, String str2) {
        int[][] distanceMatrix = new int[str1.length() + 1][];
        for(int i = 0; i < distanceMatrix.length; i++)
            distanceMatrix[i] = new int[str2.length() + 1];

        for(int i = 0; i < str1.length() + 1; i++)
            distanceMatrix[i][0] = i;
        for(int j = 1; j < str2.length() + 1; j++)
            distanceMatrix[0][j] = j;

        return distanceMatrix;
    }

    @VisibleForTesting
    int countCost(String str1, int str1Pos, String str2, int str2Pos) {
        if(str1.charAt(str1Pos - 1) == str2.charAt(str2Pos - 1))
            return 0;
        else
            return 1;
    }
}
