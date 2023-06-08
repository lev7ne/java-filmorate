package ru.yandex.practicum.filmorate.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.MpaDao;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.mapper.Mapper;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;
import java.util.List;

@Component
public class MpaDaoImpl implements MpaDao {
    private final JdbcTemplate jdbcTemplate;

    private final String selectRatingQuery = "SELECT RATING_ID, RATING_NAME " +
            "                                 FROM RATINGS";

    @Autowired
    public MpaDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }

    @Override
    public Mpa getById(Integer id) {
        List<Mpa> mpas = jdbcTemplate.query(selectRatingQuery + " WHERE RATING_ID = ?", Mapper::makeMpa, id);

        if (mpas.isEmpty()) {
            throw new NotFoundException();
        }

        return mpas.get(0);
    }

    @Override
    public Collection<Mpa> getAll() {
        return jdbcTemplate.query(selectRatingQuery, Mapper::makeMpa);
    }
}
