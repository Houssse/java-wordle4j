package ru.yandex.practicum;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameLoggerTest {

    private static final String FILE_NAME = "test_logger.txt";
    private GameLogger logger;

    @BeforeEach
    void setUp() {
        logger = new GameLogger(FILE_NAME);
    }

    @AfterEach
    void tearDown() {
        logger.deleteLogFile();
    }

    @Test
    void info_writesInfoToFile() throws IOException {
        logger.info("тестовое сообщение");
        logger.flush();

        List<String> lines = Files.readAllLines(Paths.get(FILE_NAME));

        assertEquals(1, lines.size());
        assertTrue(lines.get(0).contains("[INFO]"));
        assertTrue(lines.get(0).contains("тестовое сообщение"));
    }

    @Test
    void error_writesErrorToFile() throws IOException {
        logger.error("тестовая ошибка");
        logger.flush();

        List<String> lines = Files.readAllLines(Paths.get(FILE_NAME));

        assertEquals(1, lines.size());
        assertTrue(lines.get(0).contains("[ERROR]"));
        assertTrue(lines.get(0).contains("тестовая ошибка"));
    }

    @Test
    void flush_writesMultipleMessages() throws IOException {
        logger.info("первое");
        logger.info("второе");
        logger.error("третье");
        logger.flush();

        List<String> lines = Files.readAllLines(Paths.get(FILE_NAME));

        assertEquals(3, lines.size());
    }

    @Test
    void flush_appendsToExistingFile() throws IOException {
        logger.info("первое");
        logger.flush();

        logger.info("второе");
        logger.flush();

        List<String> lines = Files.readAllLines(Paths.get(FILE_NAME));

        assertEquals(2, lines.size());
    }

    @Test
    void flush_clearsBuffer() throws IOException {
        logger.info("первое");
        logger.flush();
        logger.flush();

        List<String> lines = Files.readAllLines(Paths.get(FILE_NAME));

        assertEquals(1, lines.size());
    }

    @Test
    void deleteLogFile_removesFile() throws IOException {
        logger.info("сообщение");
        logger.flush();

        Path path = Paths.get(FILE_NAME);
        assertTrue(Files.exists(path));

        logger.deleteLogFile();

        assertFalse(Files.exists(path));
    }

    @Test
    void deleteLogFile_noFile_doesNotThrow() {
        assertDoesNotThrow(() -> logger.deleteLogFile());
    }

    @Test
    void log_containsTimestamp() throws IOException {
        logger.info("сообщение");
        logger.flush();

        List<String> lines = Files.readAllLines(Paths.get(FILE_NAME));

        assertTrue(lines.get(0).startsWith("["));
        assertTrue(lines.get(0).contains("]"));
    }
}