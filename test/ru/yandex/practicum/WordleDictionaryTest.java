package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WordleDictionaryTest {

    private WordleDictionary dictionary;  // <-- добавить это

    @BeforeEach
    void setUp(){
        List<String> words = List.of("Аббат", "Банка", "Волок",
                "Бич", "Сир", "чётки",
                "Апатия", "Букетик");
        dictionary = new WordleDictionary();
        dictionary.setWords(words);
    }

    @Test
    void testGetWordsFiltersCorrectly() {
        List<String> result = dictionary.getWords();

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
        assertEquals(1, word.split(" ").length, "Вернулось несколько слов ожидалось одно");
    }
}
