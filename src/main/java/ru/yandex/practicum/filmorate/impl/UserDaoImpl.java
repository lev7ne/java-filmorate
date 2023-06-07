package ru.yandex.practicum.filmorate.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;

@Component
public class UserDaoImpl implements UserDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User create(User user) {
        jdbcTemplate.update("INSERT INTO USERS (USER_NAME, LOGIN, EMAIL, BIRTHDAY) VALUES (?, ?, ?, ?)",
                user.getUserName(), user.getLogin(), user.getEmail(), user.getBirthday());
        return jdbcTemplate.query("SELECT * FROM USERS WHERE LOGIN = ?", (rs, rowNum) -> new User(
                rs.getInt(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getDate(5).toLocalDate()), user.getLogin()).get(0);
    }

    @Override
    public User update(User user) {
        int ok = jdbcTemplate.update("UPDATE users SET USER_NAME = ?, LOGIN = ?, EMAIL = ?, BIRTHDAY = ? WHERE USER_ID = ?",
                user.getUserName(), user.getLogin(), user.getEmail(), user.getBirthday(), user.getId());
        if (ok == 0) {
            throw new NotFoundException();
        }
        return getById(user.getId());

    }

    @Override
    public User getById(Integer id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE USER_ID = ?", (rs1, rowNum1) -> new User(
                rs1.getInt(1),
                rs1.getString(2),
                rs1.getString(3),
                rs1.getString(4),
                rs1.getDate(5).toLocalDate()), id);
        if (users.isEmpty()) {
            throw new NotFoundException();
        }
        return users.get(0);
    }

    @Override
    public Collection<User> getAll() {
        return jdbcTemplate.query("SELECT * FROM USERS", (rs, rowNum) -> new User(
                rs.getInt(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getDate(5).toLocalDate()
        ));
    }
}