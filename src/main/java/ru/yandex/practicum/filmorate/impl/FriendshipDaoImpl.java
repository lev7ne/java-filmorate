package ru.yandex.practicum.filmorate.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.FriendshipDao;
import ru.yandex.practicum.filmorate.mapper.Mapper;
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
                Mapper::makeUser, id);
    }

    @Override
    public void add(Integer id, Integer friendId) {
        jdbcTemplate.update("INSERT INTO FRIENDSHIPS (USER_ID, FRIEND_ID, STATUS) VALUES (?, ?, FALSE)", id, friendId);

        List<Friendship> friendships = jdbcTemplate.query("SELECT * FROM FRIENDSHIPS WHERE USER_ID = ? AND FRIEND_ID = ?",
                Mapper::makeFriendship, friendId, id);

        if (!friendships.isEmpty()) {
            String updateFriendshipTrueQuery = "UPDATE FRIENDSHIPS SET STATUS = TRUE " +
                    "                           WHERE USER_ID = ? AND FRIEND_ID = ?";
            jdbcTemplate.update(updateFriendshipTrueQuery, friendId, id);
            jdbcTemplate.update(updateFriendshipTrueQuery, id, friendId);
        }
    }

    @Override
    public void delete(Integer id, Integer friendId) {
        jdbcTemplate.update("DELETE FROM FRIENDSHIPS WHERE USER_ID = ? AND FRIEND_ID = ?", id, friendId);
        String updateFriendshipFalseQuery = "UPDATE FRIENDSHIPS SET STATUS = TRUE " +
                "                            WHERE USER_ID = ? AND FRIEND_ID = ?";
        jdbcTemplate.update(updateFriendshipFalseQuery, friendId, id);

    }

    @Override
    public List<User> getCommonFriendship(Integer id, Integer friendId) {
        return jdbcTemplate.query("SELECT * " +
                        "              FROM USERS " +
                        "              WHERE USER_ID IN (SELECT DISTINCT(A.FRIEND_ID)" +
                        "                                FROM FRIENDSHIPS AS A, FRIENDSHIPS AS B" +
                        "                                WHERE A.USER_ID = ? AND B.USER_ID = ? AND A.FRIEND_ID = B.FRIEND_ID)",
                Mapper::makeUser, id, friendId);
    }
}


