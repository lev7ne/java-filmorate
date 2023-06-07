package ru.yandex.practicum.filmorate.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.MpaDao;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;
import java.util.List;

@Component
public class MpaDaoImpl implements MpaDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MpaDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }

    @Override
    public Mpa getById(Integer id) {
        List<Mpa> mpaList = jdbcTemplate.query("SELECT RATING_ID, RATING_NAME FROM RATINGS WHERE RATING_ID = ?", (rs, rowNum) -> new Mpa(
                rs.getInt("RATING_ID"),
                rs.getString("RATING_NAME")), id);
        if (mpaList.isEmpty()) {
            throw new NotFoundException();
        }
        return mpaList.get(0);
    }

    @Override
    public Collection<Mpa> getAll() {
        return jdbcTemplate.query("SELECT RATING_ID, RATING_NAME FROM RATINGS", (rs, rowNum) -> new Mpa(
                rs.getInt(1),
                rs.getString(2)));
    }
}
