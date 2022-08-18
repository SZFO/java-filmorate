package ru.yandex.practicum.filmorate.exception;

public class DuplicateFoundException extends RuntimeException {
    public DuplicateFoundException(String message) {
        super(message);
    }
}