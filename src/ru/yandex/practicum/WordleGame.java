package ru.yandex.practicum;

import java.util.ArrayList;
import java.util.List;

public class WordleGame {

    private static final int MAX_STEPS = 6;
    private static final int WORD_LENGTH = 5;

    private final String answer;
    private int steps;
    private final WordleDictionary dictionary;
    private boolean finished;
    private final List<ExceptionWords> exceptionWordsList;
    private final GameLogger logger;

    public WordleGame(GameLogger logger) {
        this.logger = logger;
        this.steps = 0;
        this.dictionary = WordleDictionaryLoader.loadDictionary(logger);
        this.answer = dictionary.randomWord();
        this.finished = false;
        this.exceptionWordsList = new ArrayList<>();
        logger.info("Игра началась, загадано слово из " + WORD_LENGTH + " букв");
    }

    public WordleGame(GameLogger logger, WordleDictionary dictionary, String answer) {
        this.logger = logger;
        this.dictionary = dictionary;
        this.answer = answer;
        this.steps = 0;
        this.finished = false;
        this.exceptionWordsList = new ArrayList<>();
    }

    public boolean isFinished() {
        return finished;
    }

    public String getAnswer() {
        return answer;
    }

    public int getSteps() {
        return steps;
    }

    public List<ExceptionWords> getExceptionWordsList() {
        return exceptionWordsList;
    }

    public String checkAnswer(String input) {
        input = input.toLowerCase().trim().replace("ё", "е");

        if (input.length() != WORD_LENGTH) {
            logger.error("Ошибка ввода: длина " + input.length());
            throw new WordleGameException("Слово должно состоять из 5 букв");
        }

        steps++;
        ExceptionWords exceptionWord = new ExceptionWords(input, answer);
        exceptionWordsList.add(exceptionWord);
        dictionary.filterDictionary(exceptionWord);

        logger.info("Ход " + steps + ": " + input);

        if (input.equals(answer)) {
            finished = true;
            logger.info("Победа! Ответ: " + answer);
            return "Верно, это " + answer;
        }

        if (steps == MAX_STEPS) {
            finished = true;
            logger.info("Проигрыш. Ответ был: " + answer);
            return "У вас закончились ходы. Ответ был " + answer;
        }

        String mask = toChar(exceptionWord.getWord());
        logger.info("Маска: " + mask);
        return mask;
    }

    public String giveHint() {
        if (dictionary.isEmpty()) {
            logger.error("Словарь пуст, подсказок нет");
            throw new WordleGameException("Больше нет подходящих слов");
        }
        return dictionary.randomWord();
    }

    public String toChar(String word) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == answer.charAt(i)) {
                sb.append("+");
            } else if (answer.indexOf(word.charAt(i)) != -1) {
                sb.append("^");
            } else {
                sb.append("-");
            }
        }

        return sb.toString();
    }
}