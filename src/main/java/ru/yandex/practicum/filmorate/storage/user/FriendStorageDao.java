package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface FriendStorageDao {
    void add(int id, int friendId);

    void delete(int id, int friendId);

    List<User> getById(int id);

    List<User> getCommon(int id, int otherId);
}