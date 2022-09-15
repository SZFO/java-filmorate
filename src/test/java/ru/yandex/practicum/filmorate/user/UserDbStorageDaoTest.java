package ru.yandex.practicum.filmorate.user;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorageDao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserDbStorageDaoTest {
    private final UserStorageDao userStorage;
    private final User user1 = new User(1, "Vadim", "szfo", "vadimfaustov@yandex.ru",
            LocalDate.of(1990, 8, 26));
    private final User user2 = new User(2, "Alexey", "anonymous", "anonymous@yandex.ru",
            LocalDate.of(1987, 4, 12));

    @Test
    void getAllIfEmptyUsersTest() {
        assertEquals(Collections.EMPTY_LIST, new ArrayList<>(userStorage.findAll()));
    }

    @Test
    void createUserTest() {
        User testUser = userStorage.create(user1);

        assertEquals(testUser, userStorage.findById(testUser.getId()).get());
    }

    @Test
    void findUserByIdTest() {
        userStorage.create(user1);

        assertThat(userStorage.findById(1))
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("id", 1)
                );
    }

    @Test
    void getAllUserTest() {
        userStorage.create(user1);
        userStorage.create(user2);

        assertEquals(List.of(user1, user2), userStorage.findAll());
    }

    @Test
    void updateUserTest() {
        userStorage.create(user1);
        User updatedUser = new User(1, "Vadim", "szfo", "vadimfaustov@gmail.com",
                LocalDate.of(1990, 8, 26));
        userStorage.updateUser(updatedUser);

        assertThat(userStorage.findById(1))
                .isPresent()
                .hasValueSatisfying(user1 ->
                        assertThat(user1).hasFieldOrPropertyWithValue("email",
                                "vadimfaustov@gmail.com"));
    }

    @Test
    void deleteUserTest() {
        userStorage.create(user1);
        userStorage.create(user2);
        userStorage.deleteUser(user1.getId());

        assertEquals(List.of(user2), userStorage.findAll());
    }
}