package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Optional;

public interface FilmStorage {
    Collection<Film> findAll();

    Optional<Film> create(Film film);

    Film updateFilm(Film film);

    void deleteFilm(int id);

    Optional<Film> findById(int id);

    void addLike(int filmId, int userId);

    void deleteLike(int filmId, int userId);

    Collection<Film> getPopular(int count);
}
