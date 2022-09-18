package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.MpaRating;

import java.util.List;
import java.util.Optional;

public interface MpaRatingStorageDao {
    List<MpaRating> getAll();

    Optional<MpaRating> getById(int id);
}