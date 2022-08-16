package ru.yandex.practicum.filmorate.exception;

public class DuplicateUserFoundException extends RuntimeException {
    public DuplicateUserFoundException(String message) {
        super(message);
    }
}