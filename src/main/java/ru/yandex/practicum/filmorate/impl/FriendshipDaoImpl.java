package ru.yandex.practicum.filmorate.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.FriendshipDao;
import ru.yandex.practicum.filmorate.model.Friendship;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Component
public class FriendshipDaoImpl implements FriendshipDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FriendshipDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> getFriendship(Integer id) {
        return jdbcTemplate.query("SELECT U.USER_ID, U.USER_NAME, U.LOGIN, U.EMAIL, U.BIRTHDAY " +
                        "              FROM FRIENDSHIPS " +
                        "              RIGHT JOIN USERS U on U.USER_ID = FRIENDSHIPS.FRIEND_ID " +
                        "              WHERE FRIENDSHIPS.USER_ID = ?",
                (rs, rowNum) -> new User(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDate(5).toLocalDate()), id);
    }

    @Override
    public void addFriend(Integer id, Integer friendId) {
        jdbcTemplate.update("INSERT INTO FRIENDSHIPS (USER_ID, FRIEND_ID, STATUS) VALUES (?, ?, FALSE)", id, friendId);

        List<Friendship> friendships = jdbcTemplate.query("SELECT * FROM FRIENDSHIPS WHERE USER_ID = ? AND FRIEND_ID = ?", (rs, rowNum) -> new Friendship(
                rs.getInt(1),
                rs.getInt(2),
                rs.getBoolean(3)
        ), friendId, id);

        if (!friendships.isEmpty()) {
            jdbcTemplate.update("UPDATE FRIENDSHIPS SET STATUS = TRUE WHERE USER_ID = ? AND FRIEND_ID = ?", friendId, id);
            jdbcTemplate.update("UPDATE FRIENDSHIPS SET STATUS = TRUE WHERE USER_ID = ? AND FRIEND_ID = ?", id, friendId);
        }
    }

    @Override
    public void deleteFriend(Integer id, Integer friendId) {
        jdbcTemplate.update("DELETE FROM FRIENDSHIPS WHERE USER_ID = ? AND FRIEND_ID = ?", id, friendId);
        jdbcTemplate.update("UPDATE FRIENDSHIPS SET STATUS = FALSE WHERE USER_ID = ? AND FRIEND_ID = ?", friendId, id);

    }

    @Override
    public List<User> getCommonFriendship(Integer id, Integer friendId) {
        return jdbcTemplate.query("SELECT * " +
                "                  FROM USERS " +
                "                  WHERE USER_ID IN (SELECT DISTINCT(A.FRIEND_ID)" +
                "                                    FROM FRIENDSHIPS AS A, FRIENDSHIPS AS B" +
                "                                    WHERE A.USER_ID = ? AND B.USER_ID = ? AND A.FRIEND_ID = B.FRIEND_ID);", (rs, rowNum) -> new User(
                rs.getInt(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getDate(5).toLocalDate()), id, friendId);
    }
}


