package ru.yandex.practicum;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordleDictionaryTest {

    private static final String LOG_FILE = "test_dict_log.txt";
    private GameLogger logger;

    @BeforeEach
    void setUp() {
        logger = new GameLogger(LOG_FILE);
    }

    @AfterEach
    void tearDown() {
        logger.deleteLogFile();
    }

    @Test
    void randomWord_returnsWordFromList() {
        List<String> words = List.of("котик", "книга", "мышка");
        WordleDictionary dict = new WordleDictionary(new ArrayList<>(words), logger);
        String result = dict.randomWord();
        assertTrue(words.contains(result));
    }

    @Test
    void isEmpty_emptyList_returnsTrue() {
        WordleDictionary dict = new WordleDictionary(new ArrayList<>(), logger);
        assertTrue(dict.isEmpty());
    }

    @Test
    void contains_existingWord_returnsTrue() {
        WordleDictionary dict = new WordleDictionary(
                new ArrayList<>(List.of("котик", "книга")), logger);
        assertTrue(dict.contains("котик"));
        assertFalse(dict.contains("мышка"));
    }

    @Test
    void filterDictionary_removesWordsWithAbsentLetters() {
        WordleDictionary dict = new WordleDictionary(
                new ArrayList<>(List.of("котик", "книга", "мышка")), logger);

        ExceptionWords ew = new ExceptionWords("книга", "котик");
        dict.filterDictionary(ew);

        assertFalse(dict.contains("книга"));
    }

    @Test
    void filterDictionary_keepsAnswerInDictionary() {
        WordleDictionary dict = new WordleDictionary(
                new ArrayList<>(List.of("банан", "котик", "книга")), logger);

        ExceptionWords ew = new ExceptionWords("котик", "банан");
        dict.filterDictionary(ew);

        assertTrue(dict.contains("банан"), "Ответ не должен теряться");
    }

    @Test
    void filterDictionary_sizeDecreases() {
        WordleDictionary dict = new WordleDictionary(
                new ArrayList<>(List.of("котик", "книга", "мышка", "банан")), logger);
        int before = dict.size();

        ExceptionWords ew = new ExceptionWords("котик", "банан");
        dict.filterDictionary(ew);

        assertTrue(dict.size() <= before);
    }
}