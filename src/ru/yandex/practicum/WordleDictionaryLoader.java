package ru.yandex.practicum;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/*
этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками
    ему нужны методы по загрузке списка слов из файла по имени файла
    на выходе должен быть класс WordleDictionary
 */
public class WordleDictionaryLoader {
    private static final String NAME_FILE = "words_ru.txt";
    private static final int WORLD_LENGTH = 5;

    public static WordleDictionary loadDictionary() {
        List<String> words = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(NAME_FILE), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim().toLowerCase().replace("ё", "е");
                if (line.length() == WORLD_LENGTH) {
                    words.add(line);
                }
            }
        } catch (IOException e) {
            throw new WordleGameException("Ошибка чтения файла: " + e.getMessage());
        }

        return new WordleDictionary(words);
    }
}
