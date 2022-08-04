package ru.yandex.practicum.filmorate.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalHandlerException {
    @ExceptionHandler({ValidationException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Ошибка валидации!")
    Exception ValidationException(final Exception e) {
        return e;
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Запрашиваемый объект не обнаружен!")
    Exception NoSuchElementException(final Exception e) {
        return e;
    }
}