package ru.yandex.practicum.exception;

public class EmptyDictionaryException extends RuntimeException {
    public EmptyDictionaryException(String message) {
        super(message);
    }
}