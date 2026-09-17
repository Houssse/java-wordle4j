package ru.yandex.practicum;

import ru.yandex.practicum.exception.WordleGameException;

import java.util.Scanner;

public class Wordle {

    private static final String DICTIONARY_FILE = "words_ru.txt";
    private static final String LOG_FILE = "game_log.txt";

    public static void main(String[] args) {
        GameLogger logger = new GameLogger(LOG_FILE);

        try {
            logger.info("Программа запущена");

            WordleDictionaryLoader loader = new WordleDictionaryLoader(logger);
            WordleDictionary dictionary = loader.load(DICTIONARY_FILE);

            boolean autoMode = args.length > 0 && "auto".equalsIgnoreCase(args[0]);

            if (autoMode) {
                runAutoMode(dictionary, logger);
            } else {
                runInteractiveMode(dictionary, logger);
            }

            logger.info("Программа завершена");
        } catch (Exception e) {
            logger.error("Непредвиденная ошибка: " + e);
            StringBuilder sb = new StringBuilder();
            for (StackTraceElement el : e.getStackTrace()) {
                sb.append("\tat ").append(el).append(System.lineSeparator());
            }
            logger.error(sb.toString());
        } finally {
            logger.flush();
        }
    }

    private static void runInteractiveMode(WordleDictionary dictionary, GameLogger logger) {
        WordleGame game = new WordleGame(dictionary, dictionary.randomWord(), logger);

        System.out.println("Я загадал слово из 5 букв. У вас 6 попыток.");
        System.out.println("Нажмите Enter без ввода, чтобы получить подсказку.");

        try (Scanner scanner = new Scanner(System.in)) {
            while (!game.isFinished()) {
                System.out.println();
                System.out.println("Ход " + (game.getSteps() + 1) + " из 6");

                String input = scanner.nextLine();

                if (input.isBlank()) {
                    try {
                        String hint = game.giveHint();
                        System.out.println("Подсказка: " + hint);
                    } catch (WordleGameException e) {
                        System.out.println(e.getMessage());
                        logger.error("Ошибка подсказки: " + e.getMessage());
                    }
                    continue;
                }

                try {
                    System.out.println(game.makeMove(input));
                } catch (WordleGameException e) {
                    System.out.println(e.getMessage());
                    logger.error("Игровая ошибка: " + e.getMessage());
                }
            }
        }

        System.out.println();
        System.out.println("Игра окончена!");
    }

    private static void runAutoMode(WordleDictionary dictionary, GameLogger logger) {
        System.out.println("Режим автопилота. Компьютер играет сам.");
        WordleGame game = new WordleGame(dictionary, dictionary.randomWord(), logger);
        boolean win = game.autoPlay();
        System.out.println(win ? "Автопилот победил!" : "Автопилот проиграл.");
    }
}