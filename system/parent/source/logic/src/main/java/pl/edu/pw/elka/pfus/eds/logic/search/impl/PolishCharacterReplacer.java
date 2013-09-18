package pl.edu.pw.elka.pfus.eds.logic.search.impl;

import java.util.HashMap;
import java.util.Map;

public class PolishCharacterReplacer extends AbstractCharacterReplacer {
    @Override
    protected Map<Character, Character> getReplacementsMap() {
        Map<Character, Character> replacements = new HashMap<>();
        replacements.put('ę', 'e');
        replacements.put('ó', 'o');
        replacements.put('ą', 'a');
        replacements.put('ś', 's');
        replacements.put('ł', 'l');
        replacements.put('ż', 'z');
        replacements.put('ź', 'z');
        replacements.put('ć', 'c');
        replacements.put('ń', 'n');
        return replacements;
    }
}
