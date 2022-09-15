package ru.yandex.practicum.filmorate.user;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.FriendStorageDao;
import ru.yandex.practicum.filmorate.storage.user.UserStorageDao;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class FriendDbStorageDaoTest {
    private final UserStorageDao userStorage;
    private final FriendStorageDao friendStorage;
    private final User user = new User(1, "Vadim", "szfo", "vadimfaustov@yandex.ru",
            LocalDate.of(1990, 8, 26));
    private final User friend = new User(2, "Alexey", "anonymous", "anonymous@yandex.ru",
            LocalDate.of(1987, 4, 12));
    private final User commonFriend = new User(3, "Vladislav", "pablo", "pablo@yahoo.com",
            LocalDate.of(1994, 11, 8));

    @BeforeEach
    public void createUserAndFriend() {
        userStorage.create(user);
        userStorage.create(friend);
    }

    @Test
    void addFriendTest() {
        friendStorage.addFriend(user.getId(), friend.getId());

        assertEquals(List.of(friend), friendStorage.getFriends(user.getId()));
    }

    @Test
    void deleteFriendTest() {
        friendStorage.addFriend(user.getId(), friend.getId());
        friendStorage.deleteFriend(user.getId(), friend.getId());

        assertEquals(Collections.emptyList(), friendStorage.getFriends(user.getId()));

    }

    @Test
    void getCommonFriendsTest() {
        userStorage.create(commonFriend);
        friendStorage.addFriend(user.getId(), commonFriend.getId());
        friendStorage.addFriend(friend.getId(), commonFriend.getId());

        assertEquals(List.of(commonFriend), friendStorage.getCommonFriends(user.getId(), friend.getId()));
    }
}