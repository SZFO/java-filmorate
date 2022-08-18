package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmService {
    List<Film> findAll();

    Film create(Film film);

    Film updateFilm(Film film);

    void deleteFilm(int id);

    Film findById(int id);

    void addLike(int filmId, int userId);

    void deleteLike(int filmId, int userId);

    List<Film> getPopular(int count);
}