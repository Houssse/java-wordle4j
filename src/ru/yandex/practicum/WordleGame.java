package ru.yandex.practicum;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class WordleGame {
    private static final int MAX_ATTEMPTS = 6;
    private static final int WORD_LENGTH = 5;

    private String answer;
    private int steps;
    private WordleDictionary dictionary;
    private List<String> historyGame = new ArrayList<>();
    private PrintWriter log;
    private boolean isWin;

    public WordleGame(WordleDictionary dictionary, PrintWriter log) {
        this.dictionary = dictionary;
        this.log = log;
        this.answer = dictionary.getWord();
        this.steps = MAX_ATTEMPTS;
        log.println("Игра начата. Загадано слово из " + WORD_LENGTH + " букв");
    }

    public int getSteps() {
        return steps;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String processGuess(String guess) {
        if (guess.length() != WORD_LENGTH) {
            throw new InvalidWordLengthException("Слово должно быть из " + WORD_LENGTH + " букв");
        }
        if (!dictionary.checkAvailability(guess)) {
            throw new WordNotFoundInDictionaryException("Слово не найдено в словаре");
        }

        StringBuilder currentResult = new StringBuilder(WORD_LENGTH);
        for (int i = 0; i < WORD_LENGTH; i++) {
            if (guess.charAt(i) == answer.charAt(i)) {
                currentResult.append("+");
            } else if (answer.contains(String.valueOf(guess.charAt(i)))) {
                currentResult.append("^");
            } else {
                currentResult.append("-");
            }
        }

        if (currentResult.toString().equals("+++++")) {
            isWin = true;
        }


        historyGame.add(guess + " -> " + currentResult);
        log.println("Ход: " + guess + " -> " + currentResult);
        steps--;
        return currentResult.toString();
    }

    public boolean isWin() {
        return isWin;
    }

    public String getHint() {
        String hint = dictionary.getWord();

        StringBuilder result = new StringBuilder(WORD_LENGTH);
        for (int i = 0; i < WORD_LENGTH; i++) {
            if (hint.charAt(i) == answer.charAt(i)) {
                result.append("+");
            } else if (answer.contains(String.valueOf(hint.charAt(i)))) {
                result.append("^");
            } else {
                result.append("-");
            }
        }

        return hint + " -> " + result;
    }
}