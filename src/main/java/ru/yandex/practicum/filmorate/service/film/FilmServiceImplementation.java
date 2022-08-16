package ru.yandex.practicum.filmorate.service.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.validator.FilmReleaseDateValidator;

import java.util.Collection;
import java.util.Optional;

@Slf4j
@Service
public class FilmServiceImplementation implements FilmService {
    private final FilmStorage filmStorage;
    private final FilmReleaseDateValidator filmReleaseDateValidator;

    @Autowired
    public FilmServiceImplementation(FilmStorage filmStorage, FilmReleaseDateValidator filmReleaseDateValidator) {
        this.filmStorage = filmStorage;
        this.filmReleaseDateValidator = filmReleaseDateValidator;
    }

    @Override
    public Collection<Film> findAll() {
        return filmStorage.findAll();
    }

    @Override
    public Optional<Film> create(Film film) {
        filmReleaseDateValidator.validate(film);
        filmStorage.create(film);
        log.info("Добавлен фильм: {}", film);
        return Optional.of(film);
    }

    @Override
    public Film updateFilm(Film film) {
        filmReleaseDateValidator.validate(film);
        Film result = filmStorage.updateFilm(film);
        log.info("Обновлен фильм: {}", film);
        return result;
    }

    @Override
    public void deleteFilm(int id) {
        filmStorage.deleteFilm(id);
        log.info(String.format("Удален фильм с ID = %s.", id));
    }

    @Override
    public Optional<Film> findById(int id) {
        return filmStorage.findById(id);
    }

    @Override
    public void addLike(int filmId, int userId) {
        filmStorage.addLike(filmId, userId);
        log.info(String.format("Фильму с ID = %s добавлен лайк от пользователя с ID = %s", filmId, userId));
    }

    @Override
    public void deleteLike(int filmId, int userId) {
        filmStorage.deleteLike(filmId, userId);
        log.info(String.format("У фильма с ID = %s удален лайк от пользователя с ID = %s", filmId, userId));
    }

    @Override
    public Collection<Film> getPopular(int count) {
        Collection<Film> result = filmStorage.getPopular(count);
        log.info(String.format("Вывод первых %s фильмов по количеству лайков.", count));
        return result;
    }
}