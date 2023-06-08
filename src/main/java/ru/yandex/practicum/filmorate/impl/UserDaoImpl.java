package ru.yandex.practicum.filmorate.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.mapper.Mapper;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;

@Component
public class UserDaoImpl implements UserDao {
    private final JdbcTemplate jdbcTemplate;
    private final String insertUserQuery = "INSERT INTO USERS (USER_NAME, LOGIN, EMAIL, BIRTHDAY) VALUES (?, ?, ?, ?)";
    private final String updateUserQuery = "UPDATE users SET USER_NAME = ?, LOGIN = ?, EMAIL = ?, BIRTHDAY = ? " +
            "                               WHERE USER_ID = ?";
    private final String selectUserQuery = "SELECT * " +
            "                               FROM USERS";

    @Autowired
    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User create(User user) {
        jdbcTemplate.update(insertUserQuery, user.getUserName(), user.getLogin(), user.getEmail(), user.getBirthday());
        return jdbcTemplate.query(selectUserQuery + " WHERE LOGIN = ?", Mapper::makeUser, user.getLogin()).get(0);
    }

    @Override
    public User update(User user) {
        int response = jdbcTemplate.update(updateUserQuery, user.getUserName(), user.getLogin(), user.getEmail(), user.getBirthday(), user.getId());

        if (response == 0) {
            throw new NotFoundException();
        }

        return getById(user.getId());

    }

    @Override
    public User getById(Integer id) {
        List<User> users = jdbcTemplate.query(selectUserQuery + " WHERE USER_ID = ?", Mapper::makeUser, id);

        if (users.isEmpty()) {
            throw new NotFoundException();
        }

        return users.get(0);
    }

    @Override
    public Collection<User> getAll() {
        return jdbcTemplate.query(selectUserQuery, Mapper::makeUser);
    }
}