package ru.yandex.practicum.filmorate.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exception.*;

@RestControllerAdvice
public class GlobalHandlerException {
    @ExceptionHandler({ValidationException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Ошибка валидации!")
    Exception ValidationException(final Exception e) {
        return e;
    }

    @ExceptionHandler({UserNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Пользователь с указанным id отсутствует в базе данных!")
    Exception UserNotFoundException(final Exception e) {
        return e;
    }

    @ExceptionHandler({FilmNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Фильм с указанным id отсутствует в базе данных!")
    Exception FilmNotFoundException(final Exception e) {
        return e;
    }

    @ExceptionHandler({DuplicateFilmFoundException.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Фильм с указанным id уже " +
            "присутствует в базе данных!")
    Exception DuplicateFilmFoundException(final Exception e) {
        return e;
    }

    @ExceptionHandler({DuplicateUserFoundException.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Пользователь с указанным id уже " +
            "присутствует в базе данных!")
    Exception DuplicateUserFoundException(final Exception e) {
        return e;
    }
}