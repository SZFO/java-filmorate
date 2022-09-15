package ru.yandex.practicum.filmorate.service.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public List<Genre> findAll() {
        return genreStorageDao.findAll();
    }

    @Override
    public Genre findById(int id) {
        return genreStorageDao.findById(id)
                .orElseThrow(() -> {
                    log.warn("Ошибка поиска жанра фильма по id");
                    throw new NotFoundException(HttpStatus.NOT_FOUND,
                            String.format("Жанр с id %s отсутствует в базе данных.", id));
                });
    }
}