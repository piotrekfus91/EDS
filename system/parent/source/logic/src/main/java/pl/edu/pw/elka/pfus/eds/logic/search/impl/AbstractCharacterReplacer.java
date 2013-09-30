package pl.edu.pw.elka.pfus.eds.logic.search.impl;

import pl.edu.pw.elka.pfus.eds.logic.search.NationalCharacterReplacer;

import java.util.Map;

public abstract class AbstractCharacterReplacer implements NationalCharacterReplacer {
    /**
     * Metoda powinna zwracać mapę par: znak do zastąpienia - znak zastępujący.
     *
     * @return mapa znaków do zmiany.
     */
    protected abstract Map<Character, Character> getReplacementsMap();

    @Override
    public String replaceAll(String input) {
        StringBuilder output = new StringBuilder();
        Map<Character, Character> replacements = getReplacementsMap();

        for(Character ch : input.toCharArray()) {
            if(replacements.containsKey(ch)) {
                output.append(replacements.get(ch));
            } else {
                output.append(ch);
            }
        }
        return output.toString();
    }
}
