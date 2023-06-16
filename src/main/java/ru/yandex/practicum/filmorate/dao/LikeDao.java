package ru.yandex.practicum.filmorate.dao;

import org.springframework.data.relational.core.sql.Like;

public interface LikeDao extends Addable<Like> {

    void add(Integer filmId, Integer userId);

    void delete(Integer filmId, Integer userId);
}