package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmStorageDao {
    List<Film> findAll();

    Film create(Film film);

    Optional<Film> updateFilm(Film film);

    void deleteFilm(int id);

    Optional<Film> findById(int id);

    List<Film> getPopular(int count);
}