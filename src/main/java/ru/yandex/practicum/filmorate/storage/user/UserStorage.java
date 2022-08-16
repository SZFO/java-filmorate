package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserStorage {
    Collection<User> findAll();

    Optional<User> create(User user);

    User updateUser(User user);

    void deleteUser(int id);

    Optional<User> findById(int id);

    void addFriend(int id, int friendId);

    void deleteFriend(int id, int friendId);

    Collection<User> getFriends(int id);

    Collection<User> getCommonFriends(int id, int otherId);
}