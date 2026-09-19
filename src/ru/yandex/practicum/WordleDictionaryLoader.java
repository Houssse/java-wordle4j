package ru.yandex.practicum;

import ru.yandex.practicum.exception.DictionaryLoadException;
import ru.yandex.practicum.exception.EmptyDictionaryException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class WordleDictionaryLoader {

    private static final int WORD_LENGTH = 5;

    private final GameLogger logger;

    public WordleDictionaryLoader(GameLogger logger) {
        this.logger = logger;
    }

    public WordleDictionary load(String fileName) throws DictionaryLoadException {
        logger.info("Загрузка словаря из " + fileName);

        List<String> words = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8))) {

            String line;
            while ((line = reader.readLine()) != null) {
                line = normalize(line);
                if (line.length() == WORD_LENGTH) {
                    words.add(line);
                }
            }
        } catch (IOException e) {
            logger.error("Ошибка чтения файла словаря: " + e.getMessage());
            throw new DictionaryLoadException("Не удалось загрузить словарь из " + fileName, e);
        }

        if (words.isEmpty()) {
            logger.error("Словарь пуст: " + fileName);
            throw new EmptyDictionaryException("Словарь пуст: " + fileName);
        }

        logger.info("Загружено слов: " + words.size());
        return new WordleDictionary(words, logger);
    }

    public static String normalize(String word) {
        return word.trim().toLowerCase().replace("ё", "е");
    }
}