package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> getAll();

    Genre getById(int id);
}