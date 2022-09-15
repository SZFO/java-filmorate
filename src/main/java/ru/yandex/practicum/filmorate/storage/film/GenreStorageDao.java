package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface GenreStorageDao {
    List<Genre> findAll();

    Optional<Genre> findById(int id);

    void setFilmGenre(Film film);

    Set<Genre> loadFilmGenre(Film film);
}