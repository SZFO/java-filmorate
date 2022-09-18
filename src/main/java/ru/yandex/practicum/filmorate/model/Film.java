package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
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

    private MpaRating mpa;

    private Set<Genre> genres;
}