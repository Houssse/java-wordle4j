package ru.yandex.practicum;

import java.util.Scanner;

public class Wordle {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        GameLogger logger = new GameLogger("game_log.txt");

        try {
            logger.info("Программа запущена");

            WordleGame game = new WordleGame(logger);

            System.out.println("Я загадал слово из 5 букв, у вас 6 ходов");

            while (!game.isFinished()) {
                System.out.println("Ход " + (game.getSteps() + 1));
                System.out.print("-> ");
                String answer = input.nextLine();

                try {
                    if (answer.isBlank()) {
                        String hint = game.giveHint();
                        System.out.println("Подсказка: " + hint);
                        System.out.println(game.checkAnswer(hint));
                    } else {
                        System.out.println(game.checkAnswer(answer));
                    }
                } catch (WordleGameException e) {
                    System.out.println(e.getMessage());
                    logger.error("Игровая ошибка: " + e.getMessage());
                }
            }

            System.out.println("Игра окончена!");
            logger.info("Игра завершена");
        } catch (Exception e) {
            logger.error("Непредвиденная ошибка: " + e.getMessage());
            e.printStackTrace();
        } finally {
            logger.flush();
        }
    }
}