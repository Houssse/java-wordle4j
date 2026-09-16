package ru.yandex.practicum;

import java.util.*;

public class WordleDictionary {

    private List<String> words;

    public WordleDictionary(List<String> words) {
        this.words = words;
    }

    public String randomWord() {
        Random random = new Random();
        return words.get(random.nextInt(words.size()));
    }

    public void filterDictionary(ExceptionWords exceptionWord) {
        String forbidden = exceptionWord.getChars();
        LinkedHashMap<Character, List<Integer>> positions = exceptionWord.getCharPosition();
        LinkedHashMap<Character, List<Integer>> misplacements = exceptionWord.getMisplacements();

        List<String> filtered = new ArrayList<>();

        for (String word : words) {
            if (word.equals(exceptionWord.getWord())) {
                continue;
            }

            boolean valid = true;

            for (int i = 0; i < forbidden.length(); i++) {
                if (word.indexOf(forbidden.charAt(i)) != -1) {
                    valid = false;
                    break;
                }
            }

            if (valid) {
                for (Map.Entry<Character, List<Integer>> entry : positions.entrySet()) {
                    char c = entry.getKey();
                    for (int pos : entry.getValue()) {
                        if (word.charAt(pos) != c) {
                            valid = false;
                            break;
                        }
                    }
                    if (!valid) break;
                }
            }

            if (valid) {
                for (Map.Entry<Character, List<Integer>> entry : misplacements.entrySet()) {
                    char c = entry.getKey();
                    if (word.indexOf(c) == -1) {
                        valid = false;
                        break;
                    }
                    for (int pos : entry.getValue()) {
                        if (word.charAt(pos) == c) {
                            valid = false;
                            break;
                        }
                    }
                    if (!valid) break;
                }
            }

            if (valid) {
                filtered.add(word);
            }
        }

        words = filtered;
    }
}