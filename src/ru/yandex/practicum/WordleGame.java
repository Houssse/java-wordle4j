package ru.yandex.practicum;

import java.util.ArrayList;
import java.util.List;

/*
в этом классе хранится словарь и состояние игры
    текущий шаг
    всё что пользователь вводил
    правильный ответ

в этом классе нужны методы, которые
    проанализируют совпадение слова с ответом
    предложат слово-подсказку с учётом всего, что вводил пользователь ранее

не забудьте про специальные типы исключений для игровых и неигровых ошибок
 */
public class WordleGame {

    private String answer;

    private int steps;

    private WordleDictionary dictionary;

    private List<String> historyGame = new ArrayList<>();

    public WordleGame(WordleDictionary dictionary) {
        this.dictionary = dictionary;
        this.answer = dictionary.getWord();
        this.steps = 6;
    }

    public int getSteps() {
        return steps;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String processGuess(String guess) {
        if (!dictionary.CheckAvailability(guess)) {
            throw new IllegalArgumentException("Слово не найдено в словаре");
        }

        StringBuilder currentResult = new StringBuilder(5);

        for (int i = 0; i < 5; i++){
            if (guess.charAt(i) == answer.charAt(i)) {
                currentResult.append("+");
            } else if (answer.contains(String.valueOf(guess.charAt(i)))) {
                currentResult.append("^");
            } else {
                currentResult.append("-");
            }
        }

        historyGame.add(guess + " -> " + currentResult);

        steps --;

        return currentResult.toString();
    }

}
