package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Like;

import java.util.List;

public interface LikeStorageDao {
    boolean add(int filmId, int userId);

    boolean delete(int filmId, int userId);

    List<Like> get(int filmId, int userId);
}