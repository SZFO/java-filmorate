package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {
    private int userId = 0;
    private static Map<Integer, User> users = new HashMap<>();

    @GetMapping
    public Collection<User> findAll() {
        log.info("Текущее количество пользователей : {}", users.size());
        return users.values();
    }

    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        validateUser(user);
        user.setId(++userId);
        if (!users.containsKey(user.getId())) {
            users.put(user.getId(), user);
        }
        log.info("Добавлен пользователь: {}", user);
        return user;
    }

    @PutMapping
    public User updateFilm(@Valid @RequestBody User user) {
        if (!users.containsKey(user.getId())) {
            throw new ValidationException("Некорректно указан id у пользователя: " + user);
        }
        validateUser(user);
        users.put(user.getId(), user);
        log.info("Обновлены данные пользователя: {}", user);
        return user;
    }

    private void validateUser(User user) {
        if ((user.getEmail().isEmpty()) || user.getEmail().isBlank() || (!user.getEmail().contains("@"))) {
            log.warn("Ошибка электронной почты пользователя " + user.getLogin());
            throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ @!");
        } else if ((user.getLogin().isEmpty()) || (user.getLogin().contains(" "))) {
            log.warn("Ошибка логина пользователя " + user.getLogin());
            throw new ValidationException("Логин не может быть пустым или содержать пробелы!");
        } else if (user.getBirthday().isAfter(LocalDate.now())) {
            log.warn("Ошибка даты рождения пользователя " + user.getLogin());
            throw new ValidationException("Дата рождения не может быть в будущем!");
        }
    }
}