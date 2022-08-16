package ru.yandex.practicum.filmorate.validator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

@Slf4j
@Component
public class FilmReleaseDateValidator {
    public void validate(Film film) {
        if ((film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28)))) {
            log.warn("Ошибка даты фильма " + film.getName());
            throw new ValidationException("Дата релиза фильма — не раньше 28.12.1895");
        }
    }
}