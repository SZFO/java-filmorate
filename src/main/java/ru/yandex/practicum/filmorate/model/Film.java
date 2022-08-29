package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Data
public class Film {
    private int id;
    @NotNull
    @NotBlank(message = "Название не может быть пустым!")
    private String name;
    @Size(max = 200, message = "Максимальная длина описания — 200 символов!")
    private String description;
    private LocalDate releaseDate;
    @Positive(message = "Продолжительность фильма должна быть положительной!")
    private int duration;
    private Set<Integer> likes;
    private Set<Genre> genres;

    private MpaRating mpa;

    public Film(int id, String name, String description, LocalDate releaseDate, int duration, MpaRating mpa) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.mpa = mpa;
        likes = new HashSet<>();
        genres = new HashSet<>();
    }

    public void addLike(int id) {
        likes.add(id);
    }

    public void deleteLike(int id) {
        likes.remove(id);
    }
}