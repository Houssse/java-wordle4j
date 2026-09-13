package ru.yandex.practicum;

import java.util.LinkedHashMap;

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

    private boolean fished;

    public WordleGame() {
        this.steps = 0;
        this.dictionary = WordleDictionaryLoader.loadDictionary();
        this.answer = dictionary.randomWord();
        this.fished = false;

    }

    public boolean isFished() {
        return fished;
    }

    public int getSteps() {
        return steps;
    }

    public String checkAnswer(String input) {
        if (input.length() == 5) {
            if (input.isEmpty() || input.isBlank()) {
                return "Вы не ввели слово";
            } else {
                input = input.toLowerCase().trim().replace("ё", "е");
                if (input.equals(answer)) {
                    fished = true;
                    return "Верно это " + answer;
                } else {
                    steps++;
                    if (steps == 6) {
                        fished = true;
                        return "у вас закончились ходы. Ответ был " + answer;
                    }

                    return this.toChar(input);
                }
            }
        } else {
            return "Слово должно состоять из 5 букв";
        }
    }

    private String toChar(String input) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == answer.charAt(i)) {
                // буква на своём месте
                sb.append("+");
            } else if (answer.indexOf(input.charAt(i)) != -1) {
                // буква есть в слове, но не на этом месте
                sb.append("^");
            } else {
                // буквы нет
                sb.append("-");
            }
        }

        return sb.toString();
    }
}
