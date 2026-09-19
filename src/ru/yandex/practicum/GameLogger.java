package ru.yandex.practicum;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

public class GameLogger implements AutoCloseable {

    private final String fileName;
    private final PrintWriter writer;

    public GameLogger(String fileName) {
        this.fileName = fileName;
        try {
            this.writer = new PrintWriter(
                    new FileWriter(fileName, StandardCharsets.UTF_8), true);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось создать лог-файл: " + fileName, e);
        }
    }

    public void info(String message) {
        writer.println(format("INFO", message));
    }

    public void error(String message) {
        writer.println(format("ERROR", message));
    }

    private String format(String level, String message) {
        return "[" + LocalDateTime.now() + "] [" + level + "] " + message;
    }

    public void flush() {
        writer.flush();
    }

    @Override
    public void close() {
        writer.close();
    }

    public void deleteLogFile() {
        close();
        try {
            Files.deleteIfExists(Paths.get(fileName));
        } catch (IOException e) {
            System.err.println("Не удалось удалить лог-файл: " + e.getMessage());
        }
    }
}