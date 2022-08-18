package ru.yandex.practicum.filmorate.service.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Slf4j
@Service
public class UserServiceImplementation implements UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserServiceImplementation(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @Override
    public List<User> findAll() {
        return userStorage.findAll();
    }

    @Override
    public User create(User user) {
        return userStorage.create(user);
    }

    @Override
    public User updateUser(User user) {
        return userStorage.updateUser(user);
    }

    @Override
    public void deleteUser(int id) {
        userStorage.deleteUser(id);
        log.info(String.format("Удален пользователь с ID = %s.", id));
    }

    @Override
    public User findById(int id) {
        return userStorage.findById(id);
    }

    @Override
    public void addFriend(int id, int friendId) {
        userStorage.addFriend(id, friendId);
        log.info(String.format("Пользователю с ID = %s добавлен друзья пользователь с ID = %s", id, friendId));
    }

    @Override
    public void deleteFriend(int id, int friendId) {
        userStorage.deleteFriend(id, friendId);
        log.info(String.format("У пользователя с ID = %s удален из друзей пользователь с ID = %s", id, friendId));
    }

    @Override
    public List<User> getFriends(int id) {
        List<User> result = userStorage.getFriends(id);
        log.info(String.format("Получен список друзей пользователя с ID = %s", id));
        return result;
    }

    @Override
    public List<User> getCommonFriends(int id, int otherId) {
        List<User> result = userStorage.getCommonFriends(id, otherId);
        log.info(String.format("Получен список общих друзей пользователя с ID = %s и " + "пользователя с ID = %s",
                id, otherId));
        return result;
    }
}