package ru.yandex.practicum;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ExceptionWords {
    private final String word;
    private final StringBuilder chars;
    private final LinkedHashMap<Character, List<Integer>> charPosition;
    private final LinkedHashMap<Character, List<Integer>> misplacements;

    public ExceptionWords(String word, String answer) {
        this.word = word;
        this.chars = new StringBuilder();
        this.charPosition = new LinkedHashMap<>();
        this.misplacements = new LinkedHashMap<>();

        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);

            if (answer.charAt(i) == c) {
                addPosition(charPosition, c, i);
            } else if (answer.indexOf(c) == -1) {
                chars.append(c);
            } else {
                addPosition(misplacements, c, i);
            }
        }
    }

    private void addPosition(LinkedHashMap<Character, List<Integer>> map, char c, int pos) {
        List<Integer> positions = map.get(c);
        if (positions == null) {
            positions = new ArrayList<>();
            map.put(c, positions);
        }
        positions.add(pos);
    }

    public String getWord() {
        return word;
    }

    public String getChars() {
        return chars.toString();
    }

    public LinkedHashMap<Character, List<Integer>> getCharPosition() {
        return charPosition;
    }

    public LinkedHashMap<Character, List<Integer>> getMisplacements() {
        return misplacements;
    }
}