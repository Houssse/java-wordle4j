package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class WordleDictionaryTest {

    private WordleDictionary dictionary;  // <-- добавить это

    @BeforeEach
    void setUp() {
        StringWriter stringWriter = new StringWriter();
        PrintWriter log = new PrintWriter(stringWriter);

        Set<String> words = Set.of("Аббат", "Банка", "Волок", "Бич", "Сир", "чётки", "Апатия", "Букетик");
        dictionary = new WordleDictionary(log);
        dictionary.setWords(words);
    }

    @Test
    void testGetWordsFiltersCorrectly() {
        Set<String> result = dictionary.getWords();

        for (String word : result) {
            assertEquals(5, word.length(), "Ошибка сортировки: Слово " + word +
                    " короче или длиннее ожидаемого");

            assertEquals(word.toLowerCase(), word, "Слово не в нижнем регистре: " + word);
        }

        assertTrue(result.contains("четки"), "Ошибка замены 'ё' на 'е'");

    }

    @Test
    void testGetWordReturnsSingleWord() {
        String word = dictionary.getWord();

        assertNotNull(word);
        assertEquals(5, word.length(), "Ожидалось слово длинной 5 букв" + word);
    }

    @Test
    void testCheckAvailability() {
        StringWriter stringWriter = new StringWriter();
        PrintWriter log = new PrintWriter(stringWriter);

        WordleDictionary dict = new WordleDictionary(log);
        dict.setWords(Set.of("кот", "ежики", "дом"));

        assertTrue(dict.checkAvailability("ежики"));
        assertTrue(dict.checkAvailability("ёжики"));  // замена ё на е
        assertTrue(dict.checkAvailability("ЕЖИКИ"));  // нижний регистр
        assertFalse(dict.checkAvailability("слоны"));
    }
}
