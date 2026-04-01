package ru.yandex.practicum;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class WordleDictionaryLoader {
    private PrintWriter log;

    public WordleDictionaryLoader(PrintWriter log) {
        this.log = log;
    }

    public List<String> readingFile(Path nameFile) throws IOException {
        try {
            log.println("Загрузка словаря из: " + nameFile);
            return Files.readAllLines(nameFile, StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.println("Ошибка загрузки словаря: " + e.getMessage());
            throw e;
        }
    }
}