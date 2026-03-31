package ru.yandex.practicum;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WordleDictionaryLoaderTest {
    private static Path testFile;
    private  static final List<String> testWords = List.of("Книг", "Дом", "Достопримечательность");


    @BeforeAll
    static void setUp() throws IOException {
        testFile = Files.createTempFile("test_words", ".txt");
        Files.write(testFile, testWords, StandardCharsets.UTF_8);
    }

    @AfterAll
    static void deleteFile() throws IOException {
        Files.deleteIfExists(testFile);
    }

    @Test
    void testReadingFileReturnsCorrectWords() throws IOException {
        WordleDictionaryLoader loader = new WordleDictionaryLoader();
        List<String> result = loader.readingFile(testFile);

        assertEquals(testWords, result, "Ошибка чтения/кодировки файла");
    }

}
