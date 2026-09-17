package ru.yandex.practicum;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExceptionWords {

    private final String word;
    private final String mask;
    private final String absentChars;
    private final LinkedHashMap<Character, List<Integer>> correctPositions = new LinkedHashMap<>();
    private final LinkedHashMap<Character, List<Integer>> misplacedPositions = new LinkedHashMap<>();

    public ExceptionWords(String word, String answer) {
        this.word = word;
        this.mask = buildMask(word, answer);
        this.absentChars = buildAbsentChars(word, mask);
        fillPositions(word, mask);
    }

    private static String buildMask(String word, String answer) {
        int n = word.length();
        char[] mask = new char[n];
        int[] remaining = new int[Character.MAX_VALUE];

        for (int i = 0; i < n; i++) {
            remaining[answer.charAt(i)]++;
        }

        for (int i = 0; i < n; i++) {
            if (word.charAt(i) == answer.charAt(i)) {
                mask[i] = '+';
                remaining[answer.charAt(i)]--;
            }
        }

        for (int i = 0; i < n; i++) {
            if (mask[i] != 0) continue;
            char c = word.charAt(i);
            if (remaining[c] > 0) {
                mask[i] = '^';
                remaining[c]--;
            } else {
                mask[i] = '-';
            }
        }

        return new String(mask);
    }

    private static String buildAbsentChars(String word, String mask) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            if (mask.charAt(i) == '-') {
                sb.append(word.charAt(i));
            }
        }
        return sb.toString();
    }

    private void fillPositions(String word, String mask) {
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (mask.charAt(i) == '+') {
                addPosition(correctPositions, c, i);
            } else if (mask.charAt(i) == '^') {
                addPosition(misplacedPositions, c, i);
            }
        }
    }

    private void addPosition(Map<Character, List<Integer>> map, char c, int pos) {
        map.computeIfAbsent(c, k -> new ArrayList<>()).add(pos);
    }

    public String getWord() {
        return word;
    }

    public String getMask() {
        return mask;
    }

    public String getAbsentChars() {
        return absentChars;
    }

    public LinkedHashMap<Character, List<Integer>> getCorrectPositions() {
        return correctPositions;
    }

    public LinkedHashMap<Character, List<Integer>> getMisplacedPositions() {
        return misplacedPositions;
    }
}