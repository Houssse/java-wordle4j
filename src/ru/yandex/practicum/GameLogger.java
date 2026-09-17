package ru.yandex.practicum;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GameLogger {
    private final String fileName;
    private final List<String> logs = new ArrayList<>();

    public GameLogger(String fileName) {
        this.fileName = fileName;
    }

    public void info(String message) {
        logs.add("[" + LocalDateTime.now() + "] [INFO] " + message);
    }

    public void error(String message) {
        logs.add("[" + LocalDateTime.now() + "] [ERROR] " + message);
    }

    public void flush() {
        try (FileWriter writer = new FileWriter(fileName, StandardCharsets.UTF_8, true)) {
            for (String line : logs) {
                writer.write(line + "\n");
            }
            logs.clear();
        } catch (IOException e) {
            System.out.println("Ошибка записи лога: " + e.getMessage());
        }
    }
}