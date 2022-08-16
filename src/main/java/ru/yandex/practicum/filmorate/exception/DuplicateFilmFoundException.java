package ru.yandex.practicum.filmorate.exception;

public class DuplicateFilmFoundException extends RuntimeException {
    public DuplicateFilmFoundException(String message) {
        super(message);
    }
}