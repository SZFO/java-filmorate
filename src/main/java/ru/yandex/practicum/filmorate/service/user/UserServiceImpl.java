package ru.yandex.practicum.filmorate.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.FriendStorageDao;
import ru.yandex.practicum.filmorate.storage.user.UserStorageDao;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserStorageDao userStorage;
    private final FriendStorageDao friendStorage;

    @Override
    public List<User> findAll() {
        return userStorage.findAll();
    }

    @Override
    public User create(User user) {
        if (user.getName().isBlank() || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        return userStorage.create(user);
    }

    @Override
    public User updateUser(User user) {
        return userStorage.updateUser(user)
                .orElseThrow(() -> {
                    log.warn("Ошибка обновления пользователя");
                    throw new NotFoundException(HttpStatus.NOT_FOUND,
                            String.format("Пользователь с id %s отсутствует в базе данных.", user.getId()));
                });
    }

    @Override
    public void deleteUser(int id) {
        userStorage.deleteUser(id);
    }

    @Override
    public User findById(int id) {
        return userStorage.findById(id)
                .orElseThrow(() -> {
                    log.warn("Ошибка поиска пользователя по id");
                    throw new NotFoundException(HttpStatus.NOT_FOUND,
                            String.format("Пользователь с id %s отсутствует в базе данных.", id));
                });
    }

    @Override
    public void addFriend(int id, int friendId) {
        try {
            friendStorage.addFriend(id, friendId);
        } catch (Exception e) {
            log.warn("Ошибка добавления в друзья. Проверьте корректность ввода ID пользователя и друга.");
            throw new NotFoundException(HttpStatus.NOT_FOUND,
                    "Пользователь или друг отсутствуют в базе данных.");
        }
    }

    @Override
    public void deleteFriend(int id, int friendId) {
        try {
            friendStorage.deleteFriend(id, friendId);
        } catch (Exception e) {
            log.warn("Ошибка удаления из друзей. Проверьте корректность ввода ID пользователя и друга.");
            throw new NotFoundException(HttpStatus.NOT_FOUND,
                    "Пользователь или друг отсутствуют в базе данных.");
        }
    }

    @Override
    public List<User> getFriends(int id) {
        return friendStorage.getFriends(id);
    }

    @Override
    public List<User> getCommonFriends(int id, int otherId) {
        return friendStorage.getCommonFriends(id, otherId);
    }
}