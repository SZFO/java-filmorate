package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    List<User> findAll();

    User create(User user);

    User updateUser(User user);

    void deleteUser(int id);

    User findById(int id);

    void addFriend(int id, int friendId);

    void deleteFriend(int id, int friendId);

    List<User> getFriends(int id);

    List<User> getCommonFriends(int id, int otherId);
}