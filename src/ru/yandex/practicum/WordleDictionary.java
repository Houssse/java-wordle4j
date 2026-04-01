package ru.yandex.practicum;

import java.io.PrintWriter;
import java.util.*;

public class WordleDictionary {
    private Set<String> words;
    private PrintWriter log;

    public WordleDictionary(PrintWriter log) {
        this.log = log;
    }

    public void setWords(Set<String> words) {
        this.words = words;
        log.println("Словарь загружен. Всего слов: " + words.size());
        int filteredCount = getWords().size();
        log.println("Отфильтровано слов (5 букв): " + filteredCount);
    }

    public String getWord() {
        Set<String> filtered = getWords();
        List<String> wordList = new ArrayList<>(filtered);
        Random random = new Random();
        String word = wordList.get(random.nextInt(wordList.size()));
        log.println("Загадано слово из " + wordList.size() + " возможных");
        return word;
    }

    public Set<String> getWords() {
        Set<String> filtered = new HashSet<>();
        for (String word : words) {
            if (word.length() == 5) {
                filtered.add(word.toLowerCase().replace('ё', 'е'));
            }
        }
        return filtered;
    }

    public boolean checkAvailability(String guess) {
        Set<String> filtered = getWords();
        return filtered.contains(guess.toLowerCase().replace('ё', 'е'));
    }
}