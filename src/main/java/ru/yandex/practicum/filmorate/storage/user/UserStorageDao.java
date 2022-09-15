package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface UserStorageDao {
    List<User> findAll();

    User create(User user);

    Optional<User> updateUser(User user);

    void deleteUser(int id);

    Optional<User> findById(int id);
}