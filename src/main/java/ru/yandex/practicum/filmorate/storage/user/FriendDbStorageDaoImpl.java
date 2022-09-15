package ru.yandex.practicum.filmorate.storage.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.mapper.UserMapper;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class FriendDbStorageDaoImpl implements FriendStorageDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void addFriend(int id, int friendId) {
        String sql = "INSERT INTO friendships (user_id, friend_id, status) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, id, friendId, 1);
    }

    @Override
    public void deleteFriend(int id, int friendId) {
        String sql = "DELETE FROM friendships WHERE user_id = ? AND friend_id = ?";
        jdbcTemplate.update(sql, id, friendId);
    }

    @Override
    public List<User> getFriends(int id) {
        String sql = "SELECT u.* FROM users AS u WHERE u.id IN " +
                "(SELECT f.friend_id FROM friendships AS f WHERE f.user_id = ? AND f.status = TRUE)";

        return jdbcTemplate.query(sql, new UserMapper(), id);
    }

    @Override
    public List<User> getCommonFriends(int id, int otherId) {
        String sql = "SELECT  u.* " +
                "FROM friendships AS fs " +
                "JOIN users AS u ON fs.friend_id = u.id " +
                "WHERE fs.user_id = ? AND fs.friend_id IN (" +
                "SELECT friend_id FROM friendships WHERE user_id = ?)";

        return jdbcTemplate.query(sql, new UserMapper(), id, otherId);
    }
}