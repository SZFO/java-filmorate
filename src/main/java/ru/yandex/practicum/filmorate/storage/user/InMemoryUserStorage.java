package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.DuplicateUserFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {
    private int userId = 0;
    private final Map<Integer, User> users = new HashMap<>();

    @Override
    public Collection<User> findAll() {
        log.info("Текущее количество пользователей : {}", users.size());
        return users.values();
    }

    @Override
    public Optional<User> create(User user) {
        user.setId(++userId);
        if (!users.containsKey(user.getId())) {
            return Optional.ofNullable(users.put(user.getId(), user));
        } else throw new DuplicateUserFoundException(String.format("Пользователь с id %s уже существует.",
                user.getId()));
    }

    @Override
    public User updateUser(User user) {
        if (!users.containsKey(user.getId())) {
            log.warn("Ошибка обновления данных пользователя: " + user.getName());
            throw new UserNotFoundException("Некорректно указан id у пользователя: " + user.getName());
        }
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public void deleteUser(int id) {
        if (!users.containsKey(id)) {
            log.warn("Ошибка удаления пользователя по id");
            throw new UserNotFoundException(String.format("Пользователь с id %s отсутствует в базе данных.", id));
        }
        users.remove(id);
    }

    @Override
    public Optional<User> findById(int id) {
        if (!users.containsKey(id)) {
            log.warn("Ошибка поиска пользователя по id");
            throw new UserNotFoundException(String.format("Пользователь с id %s отсутствует в базе данных.", id));
        }
        return Optional.of(users.get(id));
    }

    @Override
    public void addFriend(int id, int friendId) {
        if (!users.containsKey(id) || !users.containsKey(friendId)) {
            log.warn("Ошибка добавления в друзья. Проверьте корректность ввода ID пользователя и друга.");
            throw new UserNotFoundException(String.format("Проверьте корректность ввода ID пользователя = %s и " +
                    " ID друга = %s.", id, friendId));
        }
        users.get(id).addFriend(friendId);
        users.get(friendId).addFriend(id);
    }

    @Override
    public void deleteFriend(int id, int friendId) {
        if (!users.containsKey(id) || !users.containsKey(friendId)) {
            log.warn("Ошибка удаления из друзей. Проверьте корректность ввода ID пользователя и друга.");
            throw new UserNotFoundException(String.format("Проверьте корректность ввода ID пользователя = %s и " +
                    " ID друга = %s.", id, friendId));
        }
        users.get(id).deleteFriend(friendId);
        users.get(friendId).deleteFriend(id);
    }

    @Override
    public Collection<User> getFriends(int id) {
        if (!users.containsKey(id)) {
            log.warn("Ошибка получения списка друзей пользователя по его id");
            throw new UserNotFoundException(String.format("Пользователь с id %s отсутствует в базе данных.", id));
        }
        return users.get(id).getFriends().stream()
                .map(users::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<User> getCommonFriends(int id, int otherId) {
        if (!users.containsKey(id) || !users.containsKey(otherId)) {
            log.warn("Ошибка получения списка общих друзей. Проверьте корректность ввода ID обоих пользователей.");
            throw new UserNotFoundException(String.format("Проверьте корректность ввода ID первого " +
                    "пользователя = %s и ID второго пользователя = %s.", id, otherId));
        }
        return users.get(id).getFriends().stream()
                .filter(x -> users.get(otherId).getFriends().contains(x))
                .map(users::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}