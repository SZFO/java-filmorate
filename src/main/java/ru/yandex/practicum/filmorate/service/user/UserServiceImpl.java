package ru.yandex.practicum.filmorate.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public List<User> getAll() {
        return userStorage.getAll();
    }

    @Override
    public User create(User user) {
        if (user.getName().isBlank() || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        return userStorage.create(user);
    }

    @Override
    public User update(User user) {
        return userStorage.update(user)
                .orElseThrow(() -> {
                    log.warn("Ошибка обновления пользователя");
                    throw new NotFoundException(String.format("Пользователь с id %s отсутствует в базе данных.",
                            user.getId()));
                });
    }

    @Override
    public void delete(int id) {
        userStorage.delete(id);
    }

    @Override
    public User getById(int id) {
        return userStorage.getById(id)
                .orElseThrow(() -> {
                    log.warn("Ошибка поиска пользователя по id");
                    throw new NotFoundException(String.format("Пользователь с id %s отсутствует в базе данных.", id));
                });
    }

    @Override
    public void addFriend(int id, int friendId) {
        try {
            friendStorage.add(id, friendId);
        } catch (Exception e) {
            log.warn("Ошибка добавления в друзья. Проверьте корректность ввода ID пользователя и друга.");
            throw new NotFoundException("Пользователь или друг отсутствуют в базе данных.");
        }
    }

    @Override
    public void deleteFriend(int id, int friendId) {
        try {
            friendStorage.delete(id, friendId);
        } catch (Exception e) {
            log.warn("Ошибка удаления из друзей. Проверьте корректность ввода ID пользователя и друга.");
            throw new NotFoundException("Пользователь или друг отсутствуют в базе данных.");
        }
    }

    @Override
    public List<User> getFriends(int id) {
        return friendStorage.getById(id);
    }

    @Override
    public List<User> getCommonFriends(int id, int otherId) {
        return friendStorage.getCommon(id, otherId);
    }
}