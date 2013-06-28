package pl.edu.pw.elka.pfus.eds.util;

import java.util.HashMap;
import java.util.Map;

public class ValueNormalizer {
    private static Map<Character, Character> nonAsciiChars;

    static {
        nonAsciiChars = new HashMap<>();
        initNontAsciiMap();
    }

    public static String normalizeValue(String originalValue) {
        StringBuilder builder = new StringBuilder();
        for(Character c : originalValue.toLowerCase().toCharArray()) {
            if(Character.isWhitespace(c))
                continue;

            if(nonAsciiChars.containsKey(c))
                c = nonAsciiChars.get(c);

            if(Character.isLetterOrDigit(c))
                builder.append(c);
        }
        return builder.toString();
    }

    private static void initNontAsciiMap() {
        nonAsciiChars.put('ą', 'a');
        nonAsciiChars.put('ć', 'c');
        nonAsciiChars.put('ę', 'e');
        nonAsciiChars.put('ł', 'l');
        nonAsciiChars.put('ń', 'n');
        nonAsciiChars.put('ó', 'o');
        nonAsciiChars.put('ś', 's');
        nonAsciiChars.put('ż', 'z');
        nonAsciiChars.put('ź', 'z');
    }
}
