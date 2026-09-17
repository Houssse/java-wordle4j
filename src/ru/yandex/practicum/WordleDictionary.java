package ru.yandex.practicum;

import ru.yandex.practicum.exception.EmptyDictionaryException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class WordleDictionary {

    private final List<String> words;
    private final Set<String> wordSet;
    private final Random random = new Random();
    private final GameLogger logger;

    public WordleDictionary(List<String> words, GameLogger logger) {
        this.words = new ArrayList<>(words);
        this.wordSet = new HashSet<>(words);
        this.logger = logger;
    }

    public boolean isEmpty() {
        return words.isEmpty();
    }

    public int size() {
        return words.size();
    }

    public boolean contains(String word) {
        return wordSet.contains(word);
    }

    public List<String> getWords() {
        return List.copyOf(words);
    }

    public String randomWord() {
        if (words.isEmpty()) {
            throw new EmptyDictionaryException("Словарь пуст, невозможно выбрать слово");
        }
        return words.get(random.nextInt(words.size()));
    }

    public void filterDictionary(ExceptionWords exceptionWord) {
        String forbidden = exceptionWord.getAbsentChars();
        Map<Character, List<Integer>> correctPositions = exceptionWord.getCorrectPositions();
        Map<Character, List<Integer>> misplacedPositions = exceptionWord.getMisplacedPositions();

        List<String> filtered = new ArrayList<>();
        int before = words.size();

        for (String word : words) {
            if (word.equals(exceptionWord.getWord())) {
                continue;
            }
            if (isSuitable(word, forbidden, correctPositions, misplacedPositions)) {
                filtered.add(word);
            }
        }

        words.clear();
        words.addAll(filtered);

        wordSet.clear();
        wordSet.addAll(filtered);

        logger.info("Фильтрация словаря: " + before + " -> " + words.size());
    }

    private boolean isSuitable(String word,
                               String forbidden,
                               Map<Character, List<Integer>> correctPositions,
                               Map<Character, List<Integer>> misplacedPositions) {

        for (int i = 0; i < forbidden.length(); i++) {
            if (word.indexOf(forbidden.charAt(i)) != -1) {
                return false;
            }
        }

        for (Map.Entry<Character, List<Integer>> entry : correctPositions.entrySet()) {
            char c = entry.getKey();
            for (int pos : entry.getValue()) {
                if (word.charAt(pos) != c) {
                    return false;
                }
            }
        }

        for (Map.Entry<Character, List<Integer>> entry : misplacedPositions.entrySet()) {
            char c = entry.getKey();
            if (word.indexOf(c) == -1) {
                return false;
            }
            for (int pos : entry.getValue()) {
                if (word.charAt(pos) == c) {
                    return false;
                }
            }
        }

        return true;
    }
}