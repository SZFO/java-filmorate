package ru.yandex.practicum.filmorate.service.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.film.GenreStorageDao;

import java.util.List;

@Slf4j
@Service
public class GenreServiceImpl implements GenreService {
    private final GenreStorageDao genreStorageDao;

    @Autowired
    public GenreServiceImpl(GenreStorageDao genreStorageDao) {
        this.genreStorageDao = genreStorageDao;
    }

    @Override
    public List<Genre> getAll() {
        return genreStorageDao.getAll();
    }

    @Override
    public Genre getById(int id) {
        return genreStorageDao.getById(id)
                .orElseThrow(() -> {
                    log.warn("Ошибка поиска жанра фильма по id");
                    throw new NotFoundException(String.format("Жанр с id %s отсутствует в базе данных.", id));
                });
    }
}