package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.DuplicateFoundException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private int filmId = 1;
    private final Map<Integer, Film> films = new HashMap<>();

    @Override
    public List<Film> findAll() {
        log.info("Текущее количество фильмов : {}", films.size());

        return new ArrayList<>(films.values());
    }

    @Override
    public Film create(Film film) {
        if (films.containsKey(film.getId())) {
            throw new DuplicateFoundException(String.format("Фильм с id %s уже существует.", film.getId()));
        }
        film.setId(filmId++);
        films.put(film.getId(), film);
        log.info("Добавлен фильм: {}", film);

        return film;

    }

    @Override
    public Film updateFilm(Film film) {
        if (!films.containsKey(film.getId())) {
            log.warn("Ошибка обновления фильма: " + film.getName());
            throw new NotFoundException("Некорректно указан id у фильма: " + film.getName());
        }
        films.put(film.getId(), film);
        log.info("Обновлен фильм: {}", film);

        return film;
    }

    @Override
    public void deleteFilm(int id) {
        if (!films.containsKey(id)) {
            log.warn("Ошибка удаления фильма по id");
            throw new NotFoundException(String.format("Фильм с id %s отсутствует в базе данных.", id));
        }
        films.remove(id);
    }

    @Override
    public Film findById(int id) {
        return Optional.ofNullable(films.get(id))
                .orElseThrow(() -> {
                    log.warn("Ошибка поиска фильма по id");
                    throw new NotFoundException(String.format("Фильм с id %s отсутствует в базе данных.", id));
                });
    }

    @Override
    public void addLike(int filmId, int userId) {
        if (!films.containsKey(filmId)) {
            log.warn("Ошибка добавления лайка к фильму по его id.");
            throw new NotFoundException(String.format("Проверьте корректность ввода ID = %s фильма и " +
                    "ID = %s пользователя", filmId, userId));
        }
        if (userId < 1) {
            log.warn("ID пользователя должен быть равен или больше 1.");
            throw new NotFoundException(String.format("ID пользователя должен быть равен или " +
                    "больше 1. Введенный ID = %s", userId));
        }
        films.get(filmId).addLike(userId);
    }

    @Override
    public void deleteLike(int filmId, int userId) {
        if (!films.containsKey(filmId)) {
            log.warn("Ошибка удаления лайка у фильма по его id.");
            throw new NotFoundException(String.format("Проверьте корректность ввода ID = %s фильма и " +
                    "ID = %s пользователя", filmId, userId));
        }
        if (userId < 1) {
            log.warn("ID пользователя должен быть равен или больше 1.");
            throw new NotFoundException(String.format("ID пользователя должен быть равен или " +
                    "больше 1. Введенный ID = %s", userId));
        }
        films.get(filmId).deleteLike(userId);
    }

    @Override
    public List<Film> getPopular(int count) {
        return films.values().stream()
                .sorted(Comparator.comparingInt((Film x) -> x.getLikes().size()).reversed())
                .limit(count)
                .collect(Collectors.toList());
    }
}