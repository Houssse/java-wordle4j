package ru.yandex.practicum.exception;

public class InvalidWordLengthException extends WordleGameException {
    public InvalidWordLengthException(int actual) {
        super("Слово должно состоять из 5 букв, а введено " + actual);
    }
}