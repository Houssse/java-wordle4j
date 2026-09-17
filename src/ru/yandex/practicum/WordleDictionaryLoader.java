package ru.yandex.practicum;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class WordleDictionaryLoader {
    private static final String NAME_FILE = "words_ru.txt";
    private static final int WORD_LENGTH = 5;

    public static WordleDictionary loadDictionary(GameLogger logger) {
        logger.info("Загрузка словаря из " + NAME_FILE);

        List<String> words = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(NAME_FILE), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim().toLowerCase().replace("ё", "е");
                if (line.length() == WORD_LENGTH) {
                    words.add(line);
                }
            }
        } catch (IOException e) {
            logger.error("Ошибка чтения файла: " + e.getMessage());
            throw new UncheckedIOException("Ошибка чтения файла", e);
        }

        logger.info("Загружено слов: " + words.size());
        return new WordleDictionary(words);
    }
}