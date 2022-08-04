package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {
    private int userId = 0;
    private final Map<Integer, User> users = new HashMap<>();

    @GetMapping
    public Collection<User> findAll() {
        log.info("Текущее количество пользователей : {}", users.size());
        return users.values();
    }

    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        user.setId(++userId);
        if (!users.containsKey(user.getId())) {
            users.put(user.getId(), user);
        }
        log.info("Добавлен пользователь: {}", user);
        return user;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        if (!users.containsKey(user.getId())) {
            log.warn("Ошибка обновления данных пользователя: " + user.getName());
            throw new NoSuchElementException("Некорректно указан id у пользователя: " + user.getName());
        }
        users.put(user.getId(), user);
        log.info("Обновлены данные пользователя: {}", user);
        return user;
    }
}