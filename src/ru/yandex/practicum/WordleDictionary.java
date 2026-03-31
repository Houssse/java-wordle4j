package ru.yandex.practicum;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс может содержать рутинные функции по сравнению слов, букв и т.д.
 */
public class WordleDictionary {

    private List<String> words;

    public void setWords(List<String> words) {
        this.words = words;
    }

    public String getWord() {
        List<String> filtered = getWords();
        Random random = new Random();
        return filtered.get(random.nextInt(filtered.size()));
    }

    public List<String> getWords() {
        List<String> filtered = new ArrayList<>();
        for (String word : words) {
            if (word.length() == 5) {
                filtered.add(word.toLowerCase().replace('ё', 'е'));
            }
        }
        return filtered;
    }

}
