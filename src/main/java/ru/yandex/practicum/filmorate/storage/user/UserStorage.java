package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {
    User createUser(User user);

    User updateUser(User user);

    User getUserById(Integer id);

    Collection<User> getUsers();

    User addFriend(Integer id, Integer friendId);

    User deleteFriend(Integer id, Integer friendId);

    Collection<User> getAllFriends(Integer id);

    Collection<User> getCommonFriends(Integer id, Integer otherId);
}
