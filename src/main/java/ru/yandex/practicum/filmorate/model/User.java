package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Data
public class User {
    private int id;
    @NotNull
    @NotBlank
    @Email(message = "Электронная почта не может быть пустой и должна содержать символ @!")
    private String email;
    @NotNull
    @NotBlank(message = "Логин не может быть пустым или содержать пробелы!")
    private String login;
    private String name;
    @PastOrPresent(message = "Дата рождения не может быть в будущем!")
    private LocalDate birthday;

    public User(int id, String email, String login, String name, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name.isEmpty() || name.isBlank() ? login : name;
        this.birthday = birthday;
    }
}