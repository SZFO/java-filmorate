package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface UserStorageDao {
    List<User> getAll();

    User create(User user);

    Optional<User> update(User user);

    void delete(int id);

    Optional<User> getById(int id);
}