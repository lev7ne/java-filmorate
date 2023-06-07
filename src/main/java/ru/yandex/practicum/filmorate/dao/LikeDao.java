package ru.yandex.practicum.filmorate.dao;

public interface LikeDao {

    void addLike(Integer filmId, Integer userId);

    void deleteLike(Integer filmId, Integer userId);

}
