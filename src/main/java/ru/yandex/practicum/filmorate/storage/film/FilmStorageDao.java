package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmStorageDao {
    List<Film> getAll();

    Film create(Film film);

    Optional<Film> update(Film film);

    void delete(int id);

    Optional<Film> getById(int id);

    List<Film> getPopular(int count);
}