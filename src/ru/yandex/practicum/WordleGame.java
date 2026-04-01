package ru.yandex.practicum;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class WordleGame {

    private String answer;
    private int steps;
    private WordleDictionary dictionary;
    private List<String> historyGame = new ArrayList<>();
    private PrintWriter log;

    public WordleGame(WordleDictionary dictionary, PrintWriter log) {
        this.dictionary = dictionary;
        this.log = log;
        this.answer = dictionary.getWord();
        this.steps = 6;
        log.println("Игра начата. Загадано слово из 5 букв");
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
        if (guess.length() != 5) {
            throw new InvalidWordLengthException("Слово должно быть из 5 букв");
        }
        if (!dictionary.checkAvailability(guess)) {
            throw new WordNotFoundInDictionaryException("Слово не найдено в словаре");
        }

        StringBuilder currentResult = new StringBuilder(5);
        for (int i = 0; i < 5; i++) {
            if (guess.charAt(i) == answer.charAt(i)) {
                currentResult.append("+");
            } else if (answer.contains(String.valueOf(guess.charAt(i)))) {
                currentResult.append("^");
            } else {
                currentResult.append("-");
            }
        }

        historyGame.add(guess + " -> " + currentResult);
        log.println("Ход: " + guess + " -> " + currentResult);
        steps--;
        return currentResult.toString();
    }

    public String getHint() {
        String hint = dictionary.getWord();

        StringBuilder result = new StringBuilder(5);
        for (int i = 0; i < 5; i++) {
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