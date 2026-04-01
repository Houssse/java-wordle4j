package ru.yandex.practicum;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class Wordle {

    public static void main(String[] args) {
        try (PrintWriter log = new PrintWriter(new OutputStreamWriter(
                new FileOutputStream("game.log", true), StandardCharsets.UTF_8))) {

            log.println("=== Новая игра ===");

            try {
                WordleDictionaryLoader loader = new WordleDictionaryLoader(log);
                List<String> words = loader.readingFile(Paths.get("words_ru.txt"));

                WordleDictionary dictionary = new WordleDictionary(log);
                dictionary.setWords(new HashSet<>(words));

                WordleGame game = new WordleGame(dictionary, log);
                Scanner scanner = new Scanner(System.in);

                while (true) {
                    System.out.println("У вас " + game.getSteps() + " попыток");
                    System.out.print("Введите слово (или Enter для подсказки): ");

                    String guess = scanner.nextLine();

                    if (guess.isEmpty()) {
                        System.out.println("Подсказка: " + game.getHint());
                        continue;
                    }

                    try {
                        String result = game.processGuess(guess);
                        System.out.println(result);

                        if (result.equals("+++++")) {
                            System.out.println("Победа! Верное слово: " + guess);
                            log.println("Победа! Слово: " + guess);
                            break;
                        }
                    } catch (InvalidWordLengthException | WordNotFoundInDictionaryException e) {
                        System.out.println(e.getMessage());
                        log.println("Ошибка ввода: " + e.getMessage());
                    }

                    if (game.getSteps() == 0) {
                        System.out.println("Попытки закончились. Вы проиграли");
                        System.out.println("Верное слово: " + game.getAnswer());
                        log.println("Поражение. Загадано: " + game.getAnswer());
                        break;
                    }
                }

                log.println("Игра завершена");

            } catch (IOException e) {
                log.println("Критическая ошибка: " + e.getMessage());
                System.err.println("Ошибка: " + e.getMessage());
            }

        } catch (IOException e) {
            System.err.println("Ошибка при создании лог-файла: " + e.getMessage());
        }
    }
}