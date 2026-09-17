package ru.yandex.practicum;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ru.yandex.practicum.exception.DictionaryLoadException;
import ru.yandex.practicum.exception.EmptyDictionaryException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class WordleDictionaryLoaderTest {

    private static final String LOG_FILE = "test_loader.txt";
    private GameLogger logger;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        logger = new GameLogger(LOG_FILE);
    }

    @AfterEach
    void tearDown() {
        logger.deleteLogFile();
    }

    private Path writeDictionary(String... lines) throws IOException {
        Path file = tempDir.resolve("words.txt");
        Files.write(file, String.join("\n", lines).getBytes(StandardCharsets.UTF_8));
        return file;
    }

    @Test
    void loadDictionary_loadsOnlyFiveLetterWords() throws IOException {
        Path file = writeDictionary("котик", "кот", "книга", "мышка", "ёжик");

        WordleDictionary dict = new WordleDictionaryLoader(logger).load(file.toString());

        assertNotNull(dict);
        assertFalse(dict.isEmpty());
        assertTrue(dict.contains("котик"));
        assertTrue(dict.contains("книга"));
        assertTrue(dict.contains("мышка"));
        assertFalse(dict.contains("кот"));
        assertFalse(dict.contains("ёжик"));
    }

    @Test
    void loadDictionary_normalizesYoToE() throws IOException {
        Path file = writeDictionary("ёжика");

        WordleDictionary dict = new WordleDictionaryLoader(logger).load(file.toString());

        assertTrue(dict.contains("ежика"));
        assertFalse(dict.contains("ёжика"));
    }

    @Test
    void loadDictionary_missingFile_throwsException() {
        String missing = tempDir.resolve("no_such.txt").toString();

        assertThrows(DictionaryLoadException.class,
                () -> new WordleDictionaryLoader(logger).load(missing));
    }

    @Test
    void loadDictionary_emptyFile_throwsException() throws IOException {
        Path file = writeDictionary();

        assertThrows(EmptyDictionaryException.class,
                () -> new WordleDictionaryLoader(logger).load(file.toString()));
    }

    @Test
    void normalize_trimsLowercasesReplacesYo() {
        assertEquals("ежик", WordleDictionaryLoader.normalize("  ЁЖИК  "));
        assertEquals("котик", WordleDictionaryLoader.normalize("Котик"));
    }
}