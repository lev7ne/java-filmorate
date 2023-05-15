package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;

@Slf4j
@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User createUser(User user) {
        return userStorage.createUser(user);
    }

    public User updateUser(User user) {
        return userStorage.updateUser(user);
    }

    public User getUserById(Integer id) {
        return userStorage.getUserById(id);
    }

    public Collection<User> getUsers() {
        return userStorage.getUsers();
    }

    public User addFriend(Integer id, Integer friendId) {
        return userStorage.addFriend(id, friendId);
    }

    public User deleteFriend(Integer id, Integer friendId) {
        return userStorage.deleteFriend(id, friendId);
    }

    public Collection<User> getAllFriends(Integer id) {
        return userStorage.getAllFriends(id);
    }

    public Collection<User> getCommonFriends(Integer id, Integer otherId) {
        return userStorage.getCommonFriends(id, otherId);
    }
}
