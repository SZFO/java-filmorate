package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.exception.DuplicateFoundException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {
    private int userId = 1;
    private final Map<Integer, User> users = new HashMap<>();

    @Override
    public List<User> findAll() {
        log.info("Текущее количество пользователей : {}", users.size());
        return new ArrayList<>(users.values());
    }

    @Override
    public User create(User user) {
        if (users.containsKey(user.getId())) {
            throw new DuplicateFoundException(String.format("Пользователь с id %s уже существует.", user.getId()));
        } else {
            user.setId(userId++);
            users.put(user.getId(), user);
            log.info("Добавлен пользователь: {}", user);
            return user;
        }
    }

    @Override
    public User updateUser(User user) {
        if (!users.containsKey(user.getId())) {
            log.warn("Ошибка обновления данных пользователя: " + user.getName());
            throw new NotFoundException("Некорректно указан id у пользователя: " + user.getName());
        }
        users.put(user.getId(), user);
        log.info("Обновлены данные пользователя: {}", user);
        return user;
    }

    @Override
    public void deleteUser(int id) {
        if (!users.containsKey(id)) {
            log.warn("Ошибка удаления пользователя по id");
            throw new NotFoundException(String.format("Пользователь с id %s отсутствует в базе данных.", id));
        }
        users.remove(id);
    }

    @Override
    public User findById(int id) {
        return Optional.ofNullable(users.get(id))
                .orElseThrow(() -> {
                    log.warn("Ошибка поиска пользователя по id");
                    throw new NotFoundException(String.format("Пользователь с id %s отсутствует в базе данных.", id));
                });
    }

    @Override
    public void addFriend(int id, int friendId) {
        if (!users.containsKey(id) || !users.containsKey(friendId)) {
            log.warn("Ошибка добавления в друзья. Проверьте корректность ввода ID пользователя и друга.");
            throw new NotFoundException(String.format("Проверьте корректность ввода ID пользователя = %s и " +
                    " ID друга = %s.", id, friendId));
        }
        users.get(id).addFriend(friendId);
        users.get(friendId).addFriend(id);
    }

    @Override
    public void deleteFriend(int id, int friendId) {
        if (!users.containsKey(id) || !users.containsKey(friendId)) {
            log.warn("Ошибка удаления из друзей. Проверьте корректность ввода ID пользователя и друга.");
            throw new NotFoundException(String.format("Проверьте корректность ввода ID пользователя = %s и " +
                    " ID друга = %s.", id, friendId));
        }
        users.get(id).deleteFriend(friendId);
        users.get(friendId).deleteFriend(id);
    }

    @Override
    public List<User> getFriends(int id) {
        if (!users.containsKey(id)) {
            log.warn("Ошибка получения списка друзей пользователя по его id");
            throw new NotFoundException(String.format("Пользователь с id %s отсутствует в базе данных.", id));
        }
        return users.get(id).getFriends().stream()
                .map(users::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getCommonFriends(int id, int otherId) {
        if (!users.containsKey(id) || !users.containsKey(otherId)) {
            log.warn("Ошибка получения списка общих друзей. Проверьте корректность ввода ID обоих пользователей.");
            throw new NotFoundException(String.format("Проверьте корректность ввода ID первого " +
                    "пользователя = %s и ID второго пользователя = %s.", id, otherId));
        }
        return users.get(id).getFriends().stream()
                .filter(x -> users.get(otherId).getFriends().contains(x))
                .map(users::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}