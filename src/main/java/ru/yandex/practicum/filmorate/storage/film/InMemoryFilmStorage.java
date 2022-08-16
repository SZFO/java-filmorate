package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.DuplicateFilmFoundException;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private int filmId = 0;
    private final Map<Integer, Film> films = new HashMap<>();

    @Override
    public Collection<Film> findAll() {
        log.info("Текущее количество фильмов : {}", films.size());
        return films.values();
    }

    @Override
    public Optional<Film> create(Film film) {
        film.setId(++filmId);
        if (!films.containsKey(film.getId())) {
            return Optional.ofNullable(films.put(film.getId(), film));
        } else throw new DuplicateFilmFoundException(String.format("Фильм с id %s уже существует.", film.getId()));
    }

    @Override
    public Film updateFilm(Film film) {
        if (!films.containsKey(film.getId())) {
            log.warn("Ошибка обновления фильма: " + film.getName());
            throw new FilmNotFoundException("Некорректно указан id у фильма: " + film.getName());
        }
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public void deleteFilm(int id) {
        if (!films.containsKey(id)) {
            log.warn("Ошибка удаления фильма по id");
            throw new FilmNotFoundException(String.format("Фильм с id %s отсутствует в базе данных.", id));
        }
        films.remove(id);
    }

    @Override
    public Optional<Film> findById(int id) {
        if (!films.containsKey(id)) {
            log.warn("Ошибка поиска фильма по id");
            throw new FilmNotFoundException(String.format("Фильм с id %s отсутствует в базе данных.", id));
        }
        return Optional.of(films.get(id));
    }

    @Override
    public void addLike(int filmId, int userId) {
        if (!films.containsKey(filmId)) {
            log.warn("Ошибка добавления лайка к фильму по его id.");
            throw new FilmNotFoundException(String.format("Проверьте корректность ввода ID = %s фильма и " +
                    "ID = %s пользователя", filmId, userId));
        }
        if (userId < 1) {
            log.warn("ID пользователя должен быть равен или больше 1.");
            throw new UserNotFoundException(String.format("ID пользователя должен быть равен или " +
                    "больше 1. Введенный ID = %s", userId));
        }
        films.get(filmId).addLike(userId);
    }

    @Override
    public void deleteLike(int filmId, int userId) {
        if (!films.containsKey(filmId)) {
            log.warn("Ошибка удаления лайка у фильма по его id.");
            throw new FilmNotFoundException(String.format("Проверьте корректность ввода ID = %s фильма и " +
                    "ID = %s пользователя", filmId, userId));
        }
        if (userId < 1) {
            log.warn("ID пользователя должен быть равен или больше 1.");
            throw new UserNotFoundException(String.format("ID пользователя должен быть равен или " +
                    "больше 1. Введенный ID = %s", userId));
        }
        films.get(filmId).deleteLike(userId);
    }

    @Override
    public Collection<Film> getPopular(int count) {
        return films.values().stream()
                .sorted(Comparator.comparingInt((Film x) -> x.getLikes().size()).reversed())
                .limit(count)
                .collect(Collectors.toList());
    }
}