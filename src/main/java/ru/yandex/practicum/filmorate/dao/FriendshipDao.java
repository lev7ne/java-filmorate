package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Friendship;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface FriendshipDao extends Addable<Friendship> {
    List<User> getFriendship(Integer id);

    List<User> getCommonFriendship(Integer id, Integer otherId);
}