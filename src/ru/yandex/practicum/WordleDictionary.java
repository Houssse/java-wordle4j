package ru.yandex.practicum;

import java.util.*;

/*
этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс может содержать рутинные функции по сравнению слов, букв и т.д.
 */
public class WordleDictionary {

    private Set<String> words;

    public void setWords(Set<String> words) {
        this.words = words;
    }

    public String getWord() {
        List<String> wordList = new ArrayList<>(words);
        Random random = new Random();
        return wordList.get(random.nextInt(wordList.size()));
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

    public boolean CheckAvailability(String guess) {
        Set<String> filtered = getWords();
        return filtered.contains(guess.toLowerCase().replace('ё', 'е'));
    }
}
