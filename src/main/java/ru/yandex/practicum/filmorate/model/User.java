package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class User {
    private int id;

    private String name;

    @NotNull
    @Pattern(regexp = "\\S+")
    @NotBlank(message = "Логин не может быть пустым или содержать пробелы!")
    private String login;

    @NotNull
    @NotBlank
    @Email(message = "Электронная почта не может быть пустой и должна содержать символ @!")
    private String email;

    @PastOrPresent(message = "Дата рождения не может быть в будущем!")
    private LocalDate birthday;
}