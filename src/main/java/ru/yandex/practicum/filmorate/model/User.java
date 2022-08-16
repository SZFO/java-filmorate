package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {
    private int id;
    @NotNull
    @NotBlank
    @Email(message = "Электронная почта не может быть пустой и должна содержать символ @!")
    private String email;
    @NotNull
    @Pattern(regexp = "\\S+")
    @NotBlank(message = "Логин не может быть пустым или содержать пробелы!")
    private String login;
    private String name;
    @PastOrPresent(message = "Дата рождения не может быть в будущем!")
    private LocalDate birthday;
    private Set<Integer> friends;

    public User(int id, String email, String login, String name, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name.isEmpty() || name.isBlank() ? login : name;
        this.birthday = birthday;
        this.friends = new HashSet<>();
    }

    public void addFriend(int friendId) {
        friends.add(friendId);
    }

    public void deleteFriend(int friendId) {
        friends.remove(friendId);
    }
}