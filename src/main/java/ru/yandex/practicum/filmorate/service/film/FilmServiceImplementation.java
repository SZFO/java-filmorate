package ru.yandex.practicum.filmorate.service.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.validator.FilmReleaseDateValidator;

import java.util.List;

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
    public List<Film> findAll() {
        return filmStorage.findAll();
    }

    @Override
    public Film create(Film film) {
        filmReleaseDateValidator.validate(film);
        return filmStorage.create(film);
    }

    @Override
    public Film updateFilm(Film film) {
        filmReleaseDateValidator.validate(film);
        return filmStorage.updateFilm(film);
    }

    @Override
    public void deleteFilm(int id) {
        filmStorage.deleteFilm(id);
        log.info(String.format("Удален фильм с ID = %s.", id));
    }

    @Override
    public Film findById(int id) {
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
    public List<Film> getPopular(int count) {
        List<Film> result = filmStorage.getPopular(count);
        log.info(String.format("Вывод первых %s фильмов по количеству лайков.", count));
        return result;
    }
}