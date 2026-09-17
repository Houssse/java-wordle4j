package ru.yandex.practicum;

import ru.yandex.practicum.exception.EmptyDictionaryException;
import ru.yandex.practicum.exception.InvalidWordLengthException;
import ru.yandex.practicum.exception.WordNotFoundInDictionary;
import ru.yandex.practicum.exception.WordleGameException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WordleGame {

    private static final int MAX_STEPS = 6;
    private static final int WORD_LENGTH = 5;

    private final String answer;
    private final WordleDictionary dictionary;
    private final GameLogger logger;

    private int steps;
    private boolean finished;
    private final List<ExceptionWords> history = new ArrayList<>();
    private final Set<String> usedHints = new HashSet<>();
    private final Set<String> usedInputs = new HashSet<>();

    public WordleGame(WordleDictionary dictionary, String answer, GameLogger logger) {
        if (dictionary == null || dictionary.isEmpty()) {
            throw new EmptyDictionaryException("Словарь пуст");
        }
        if (answer == null || answer.length() != WORD_LENGTH) {
            throw new InvalidWordLengthException(answer == null ? 0 : answer.length());
        }
        this.dictionary = dictionary;
        this.answer = answer;
        this.logger = logger;
        this.steps = 0;
        this.finished = false;
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

    public int getRemainingSteps() {
        return MAX_STEPS - steps;
    }

    public List<ExceptionWords> getHistory() {
        return List.copyOf(history);
    }

    public String makeMove(String rawInput) {
        if (finished) {
            throw new WordleGameException("Игра уже завершена");
        }

        String input = WordleDictionaryLoader.normalize(rawInput);

        // 1. Длина
        if (input.length() != WORD_LENGTH) {
            logger.error("Ошибка ввода: длина " + input.length());
            throw new InvalidWordLengthException(input.length());
        }

        // 2. Повторный ввод — ДО проверки наличия в словаре
        if (usedInputs.contains(input)) {
            logger.info("Слово уже вводилось: " + input);
            throw new WordleGameException("Это слово вы уже вводили");
        }

        // 3. Наличие в словаре
        if (!dictionary.contains(input)) {
            logger.error("Слова нет в словаре: " + input);
            throw new WordNotFoundInDictionary(input);
        }

        usedInputs.add(input);
        steps++;

        ExceptionWords ew = new ExceptionWords(input, answer);
        history.add(ew);
        dictionary.filterDictionary(ew);

        logger.info("Ход " + steps + ": " + input + " -> " + ew.getMask());

        if (input.equals(answer)) {
            finished = true;
            logger.info("Победа! Ответ: " + answer);
            return "Верно! Это " + answer;
        }

        if (steps >= MAX_STEPS) {
            finished = true;
            logger.info("Проигрыш. Ответ: " + answer);
            return "Ходы закончились. Ответ: " + answer;
        }

        return ew.getMask();
    }

    public String giveHint() {
        if (dictionary.isEmpty()) {
            logger.error("Словарь пуст, подсказок нет");
            throw new WordleGameException("Больше нет подходящих слов");
        }

        List<String> candidates = dictionary.getWords();

        for (String word : candidates) {
            if (!usedHints.contains(word) && !usedInputs.contains(word)) {
                usedHints.add(word);
                return word;
            }
        }
        for (String word : candidates) {
            if (!word.equals(answer)) {
                usedHints.add(word);
                return word;
            }
        }
        return answer;
    }

    public String toChar(String word) {
        return new ExceptionWords(word, answer).getMask();
    }

    public boolean autoPlay() {
        logger.info("Автопилот запущен, ответ: " + answer);

        while (!finished) {
            String hint;
            try {
                hint = giveHint();
            } catch (WordleGameException e) {
                logger.error("Автопилот: подсказок больше нет — " + e.getMessage());
                return false;
            }

            System.out.println("Автоход " + (steps + 1) + ": " + hint);

            String result;
            try {
                result = makeMove(hint);
            } catch (WordleGameException e) {
                logger.error("Автопилот: ошибка хода — " + e.getMessage());
                return false;
            }

            System.out.println(result);
        }

        boolean win = answer.equals(lastInput());
        logger.info("Автопилот завершён, победа=" + win + ", ходов=" + steps);
        return win;
    }

    private String lastInput() {
        if (history.isEmpty()) return null;
        return history.get(history.size() - 1).getWord();
    }
}