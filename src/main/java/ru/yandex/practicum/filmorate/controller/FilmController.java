package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private int filmId = 0;
    private final Map<Integer, Film> films = new HashMap<>();

    @GetMapping
    public Collection<Film> findAll() {
        log.info("Текущее количество фильмов : {}", films.size());
        return films.values();
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        validate(film);
        film.setId(++filmId);
        if (!films.containsKey(film.getId())) {
            films.put(film.getId(), film);
        }
        log.info("Добавлен фильм: {}", film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        if (!films.containsKey(film.getId())) {
            log.warn("Ошибка обновления фильма: " + film.getName());
            throw new NoSuchElementException("Некорректно указан id у фильма: " + film.getName());
        }
        validate(film);
        films.put(film.getId(), film);
        log.info("Обновлен фильм: {}", film);
        return film;
    }

    private void validate(Film film) {
        if ((film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28)))) {
            log.warn("Ошибка даты фильма " + film.getName());
            throw new ValidationException("Дата релиза фильма — не раньше 28.12.1895");
        }
    }
}