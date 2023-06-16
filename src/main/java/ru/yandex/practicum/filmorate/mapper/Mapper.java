package ru.yandex.practicum.filmorate.mapper;

import ru.yandex.practicum.filmorate.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Mapper {

    public static User makeUser(ResultSet rs, int rowNum) throws SQLException {
        return new User(
                rs.getInt(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getDate(5).toLocalDate()
        );
    }

    public static Mpa makeMpa(ResultSet rs, int rowNum) throws SQLException {
        return new Mpa(
                rs.getInt(1),
                rs.getString(2)
        );
    }

    public static FilmGenre makeFilmGenre(ResultSet rs, int rowNum) throws SQLException {
        return new FilmGenre(
                rs.getInt(3),
                rs.getString(2),
                rs.getInt(1)
        );
    }

    public static Genre makeGenre(ResultSet rs, int rowNum) throws SQLException {
        return new Genre(
                rs.getInt(1),
                rs.getString(2)
        );
    }

    public static Film makeFilm(ResultSet rs, int rowNum) throws SQLException {
        return new Film(
                rs.getInt(1),
                rs.getString(2),
                rs.getString(3),
                rs.getDate(4).toLocalDate(),
                rs.getShort(5),
                new Mpa(rs.getInt(6), rs.getString(7)),
                new ArrayList<>()
        );
    }

    public static Friendship makeFriendship(ResultSet rs, int rowNum) throws SQLException {
        return new Friendship(
                rs.getInt(1),
                rs.getInt(2),
                rs.getBoolean(3)
        );
    }

}