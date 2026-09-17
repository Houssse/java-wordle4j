package ru.yandex.practicum;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordleDictionaryLoaderTest {

    private static final String LOG_FILE = "test_loader.txt";
    private final GameLogger logger = new GameLogger(LOG_FILE);

    @AfterEach
    void tearDown() {
        logger.deleteLogFile();
    }

    @Test
    void loadDictionary_loadsWordsFromFile() {
        WordleDictionary dict = WordleDictionaryLoader.loadDictionary(logger);

        assertNotNull(dict);
        assertFalse(dict.isEmpty());
    }
}