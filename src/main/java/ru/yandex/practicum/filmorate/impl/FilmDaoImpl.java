package ru.yandex.practicum.filmorate.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.FilmDao;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmGenre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class FilmDaoImpl implements FilmDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FilmDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }

    @Override
    public Film create(Film film) {
        jdbcTemplate.update("INSERT INTO FILMS (FILM_NAME, DESCRIPTION, RELEASE_DATE, DURATION, RATING_ID) VALUES (?, ?, ?, ?, ?)",
                film.getFilmName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getMpa().getId());

        Film anyFilm = jdbcTemplate.query("SELECT * FROM FILMS WHERE FILM_NAME = ?", (rs, rowNum) -> new Film(
                rs.getInt(1),
                rs.getString(2),
                rs.getString(3),
                rs.getDate(4).toLocalDate(),
                rs.getShort(5),
                new Mpa(rs.getInt(6), rs.getString(6)),
                null), film.getFilmName()).get(0);

        for (FilmGenre genre : film.getGenres()) {
            jdbcTemplate.update("INSERT INTO FILM_GENRES (FILM_ID, GENRE_ID) VALUES (?, ?)",
                    anyFilm.getId(), genre.getId());
        }

        List<FilmGenre> genresList = jdbcTemplate.query("SELECT DISTINCT FILM_GENRES.GENRE_ID, G.GENRE_NAME" +
                "                                            FROM FILM_GENRES" +
                "                                            LEFT JOIN GENRES G on FILM_GENRES.GENRE_ID = G.GENRE_ID" +
                "                                            WHERE FILM_ID = ?" +
                "                                            ORDER BY FILM_GENRES.GENRE_ID", (rs, rowNum) -> new FilmGenre(
                rs.getInt(1),
                rs.getString(2)
        ), anyFilm.getId());

        anyFilm.setGenres(genresList);

        return anyFilm;
    }

    @Override
    public Film update(Film film) {
        int flag1 = jdbcTemplate.update("UPDATE FILMS SET FILM_NAME = ?, DESCRIPTION = ?, RELEASE_DATE = ?, DURATION = ?, RATING_ID = ? WHERE FILM_ID = ?;",
                film.getFilmName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getMpa().getId(), film.getId());
        if (flag1 == 0) {
            throw new NotFoundException();
        }

        jdbcTemplate.update("DELETE FROM FILM_GENRES WHERE FILM_ID = ?", film.getId());

        for (FilmGenre genre : film.getGenres()) {

            jdbcTemplate.update("INSERT INTO FILM_GENRES (FILM_ID, GENRE_ID) VALUES (?, ?)",
                    film.getId(), genre.getId());
        }

        return getById(film.getId());
    }

    @Override
    public Film getById(Integer id) {
        Film film = null;
        try {
            film = jdbcTemplate.queryForObject("SELECT FILM_ID, FILM_NAME, DESCRIPTION, RELEASE_DATE, DURATION, FILMS.RATING_ID, R.RATING_NAME" +
                    "                               FROM FILMS" +
                    "                               LEFT JOIN RATINGS R on FILMS.RATING_ID = R.RATING_ID" +
                    "                               WHERE FILM_ID = ?", (rs, rowNum) -> new Film(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getDate(4).toLocalDate(),
                    rs.getShort(5),
                    new Mpa(rs.getInt(6), rs.getString(7)),
                    null), id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException();
        }

        List<FilmGenre> genresList = jdbcTemplate.query("SELECT DISTINCT FILM_GENRES.GENRE_ID, G.GENRE_NAME" +
                "                                            FROM FILM_GENRES" +
                "                                            LEFT JOIN GENRES G on FILM_GENRES.GENRE_ID = G.GENRE_ID" +
                "                                            WHERE FILM_ID = ?" +
                "                                            ORDER BY FILM_GENRES.GENRE_ID", (rs, rowNum) -> new FilmGenre(
                rs.getInt(1),
                rs.getString(2)
        ), id);

        if (film == null) {
            throw new NotFoundException();
        }
        film.setGenres(genresList);

        return film;

    }

    @Override
    public Collection<Film> getAll() {
        List<Film> films = jdbcTemplate.query("SELECT FILMS.FILM_ID, FILM_NAME, DESCRIPTION, RELEASE_DATE, DURATION, FILMS.RATING_ID, R.RATING_NAME" +
                "                                  FROM FILMS" +
                "                                  LEFT JOIN RATINGS R on FILMS.RATING_ID = R.RATING_ID", (rs, rowNum) -> new Film(
                rs.getInt(1),
                rs.getString(2),
                rs.getString(3),
                rs.getDate(4).toLocalDate(),
                rs.getShort(5),
                new Mpa(rs.getInt(6), rs.getString(7)),
                new ArrayList<>()));

        List<FilmGenre> genres = jdbcTemplate.query("SELECT FILM_GENRES.FILM_ID, G.GENRE_NAME, FILM_GENRES.GENRE_ID" +
                "                                        FROM FILM_GENRES" +
                "                                        LEFT JOIN GENRES G on FILM_GENRES.GENRE_ID = G.GENRE_ID", (rs, rowNum) -> new FilmGenre(
                rs.getInt(3),
                rs.getString(2),
                rs.getInt(1)));

        for (Film film : films) {
            for (FilmGenre filmGenre : genres) {
                if (film.getId() == filmGenre.getFilmId()) {
                    film.getGenres().add(filmGenre);
                }
            }
        }

        return films;
    }

    @Override
    public Collection<Film> getPopular(int count) {
        List<Film> films = jdbcTemplate.query("SELECT FILMS.FILM_ID, FILM_NAME, DESCRIPTION, RELEASE_DATE, DURATION, FILMS.RATING_ID, R.RATING_NAME" +
                "                                  FROM FILMS" +
                "                                  LEFT JOIN RATINGS R on R.RATING_ID = FILMS.RATING_ID" +
                "                                  WHERE FILMS.FILM_ID IN (SELECT FILM_ID" +
                "                                                          FROM LIKES" +
                "                                                          GROUP BY FILM_ID" +
                "                                                          ORDER BY COUNT(USER_ID) DESC" +
                "                                                          LIMIT ?);", (rs, rowNum) -> new Film(
                rs.getInt(1),
                rs.getString(2),
                rs.getString(3),
                rs.getDate(4).toLocalDate(),
                rs.getShort(5),
                new Mpa(rs.getInt(6), rs.getString(7)),
                new ArrayList<>()), count);

        if (films.isEmpty()) {
            return getAll();
        }

        List<FilmGenre> genres = jdbcTemplate.query("SELECT FILM_GENRES.FILM_ID, G.GENRE_NAME, FILM_GENRES.GENRE_ID" +
                "                                        FROM FILM_GENRES" +
                "                                        LEFT JOIN GENRES G on FILM_GENRES.GENRE_ID = G.GENRE_ID", (rs, rowNum) -> new FilmGenre(
                rs.getInt(3),
                rs.getString(2),
                rs.getInt(1)));

        for (Film film : films) {
            for (FilmGenre filmGenre : genres) {
                if (film.getId() == filmGenre.getFilmId()) {
                    film.getGenres().add(filmGenre);
                }
            }
        }

        return films;
    }
}
