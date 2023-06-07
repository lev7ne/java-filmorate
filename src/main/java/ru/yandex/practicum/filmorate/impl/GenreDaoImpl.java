package ru.yandex.practicum.filmorate.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.GenreDao;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

@Component
public class GenreDaoImpl implements GenreDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GenreDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Genre getById(Integer id) {
        Genre genre = null;
        try {
            genre = jdbcTemplate.queryForObject("SELECT * FROM GENRES WHERE GENRE_ID = ?", (rs, rowNum) -> new Genre(
                    rs.getInt(1),
                    rs.getString(2)), id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException();
        }

        return genre;
    }

    @Override
    public Collection<Genre> getAll() {
        return jdbcTemplate.query("SELECT GENRE_ID, GENRE_NAME FROM GENRES", (rs, rowNum) -> new Genre(
                rs.getInt(1),
                rs.getString(2)));
    }
}
