package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.model.MpaRating;

import java.util.List;

public interface MpaRatingService {
    List<MpaRating> getAll();

    MpaRating getById(int id);
}