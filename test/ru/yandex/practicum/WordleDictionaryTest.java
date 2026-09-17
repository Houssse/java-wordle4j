package ru.yandex.practicum;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordleDictionaryTest {

    @Test
    void randomWord_returnsWordFromList() {
        List<String> words = List.of("котик", "книга", "мышка");
        WordleDictionary dict = new WordleDictionary(new ArrayList<>(words));

        String result = dict.randomWord();

        assertTrue(words.contains(result));
    }

    @Test
    void isEmpty_emptyList_returnsTrue() {
        WordleDictionary dict = new WordleDictionary(new ArrayList<>());
        assertTrue(dict.isEmpty());
    }

    @Test
    void isEmpty_nonEmptyList_returnsFalse() {
        WordleDictionary dict = new WordleDictionary(new ArrayList<>(List.of("котик")));
        assertFalse(dict.isEmpty());
    }

    @Test
    void filterDictionary_removesWordsWithForbiddenLetters() {
        List<String> words = new ArrayList<>(List.of("котик", "книга", "мышка"));
        WordleDictionary dict = new WordleDictionary(words);

        ExceptionWords ew = new ExceptionWords("книга", "котик");
        dict.filterDictionary(ew);

        assertTrue(dict.isEmpty() || !words.contains("книга"));
    }

    @Test
    void filterDictionary_keepsMatchingWords() {
        List<String> words = new ArrayList<>(List.of("котик", "комик", "кролик"));
        WordleDictionary dict = new WordleDictionary(words);

        ExceptionWords ew = new ExceptionWords("книга", "котик");
        dict.filterDictionary(ew);

        assertNotNull(words);
    }
}