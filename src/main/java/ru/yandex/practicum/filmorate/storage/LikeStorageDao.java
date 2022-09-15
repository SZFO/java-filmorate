package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Like;

import java.util.List;

public interface LikeStorageDao {
    boolean addLike(int filmId, int userId);

    boolean deleteLike(int filmId, int userId);

    List<Like> getLikes(int filmId, int userId);
}