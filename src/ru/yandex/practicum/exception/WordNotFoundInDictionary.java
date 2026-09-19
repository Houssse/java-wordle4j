package ru.yandex.practicum.exception;

public class WordNotFoundInDictionary extends WordleGameException {
    public WordNotFoundInDictionary(String word) {
        super("Слова \"" + word + "\" нет в словаре");
    }
}