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

    public WordleGame() {
        this.steps = 0;
        this.dictionary = WordleDictionaryLoader.loadDictionary();
        this.answer = dictionary.randomWord();
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
            throw new WordleGameException("Слово должно состоять из 5 букв");
        }

        steps++;
        ExceptionWords exceptionWord = new ExceptionWords(input, answer);
        exceptionWordsList.add(exceptionWord);
        dictionary.filterDictionary(exceptionWord);

        if (input.equals(answer)) {
            finished = true;
            return "Верно, это " + answer;
        }

        if (steps == MAX_STEPS) {
            finished = true;
            return "У вас закончились ходы. Ответ был " + answer;
        }

        return toChar(exceptionWord.getWord());
    }

    public String giveHint() {
        if (dictionary.isEmpty()) {
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