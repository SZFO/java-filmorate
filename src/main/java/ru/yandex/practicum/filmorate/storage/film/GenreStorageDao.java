package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface GenreStorageDao {
    List<Genre> getAll();

    Optional<Genre> getById(int id);

    void set(Film film);

    Set<Genre> load(Film film);
}