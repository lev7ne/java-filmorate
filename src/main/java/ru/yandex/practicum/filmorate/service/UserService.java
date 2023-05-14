package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Counter;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.Validator;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
public class UserService {
    UserStorage userStorage;
    Validator validate = new Validator();
    Counter counter = new Counter();

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User createUser(User user) {
        validate.validateUser(user);
        int id = counter.getId();
        user.setId(id);
        return userStorage.saveUser(user);
    }

    public User updateUser(User user) {
        if (userStorage.getUserById(user.getId()) == null) {
            log.warn("Попытка обновить пользователя с несуществующим id.");
            throw new NotFoundException();
        }
        validate.validateUser(user);
        return userStorage.saveUser(user);
    }

    public User getUserById(Integer id) {
        if (userStorage.getUserById(id) == null) {
            throw new NotFoundException();
        }
        return userStorage.getUserById(id);
    }

    public Collection<User> getUsers() {
        return userStorage.getUsers();
    }

    public User addFriend(Integer id, Integer friendId) {
        User anyUser1 = userStorage.getUserById(id);
        User anyUser2 = userStorage.getUserById(friendId);
        if (anyUser1 == null || anyUser2 == null) {
            log.warn("Один из пользователей не существует.");
            throw new NotFoundException();
        }
        anyUser1.getFriends().add(anyUser2.getId());
        anyUser2.getFriends().add(anyUser1.getId());
        userStorage.saveUser(anyUser1);
        userStorage.saveUser(anyUser2);
        return anyUser1;
    }

    public User deleteFriend(Integer id, Integer friendId) {
        User anyUser1 = userStorage.getUserById(id);
        User anyUser2 = userStorage.getUserById(friendId);
        if (anyUser1 == null || anyUser2 == null) {
            log.warn("Один из пользователей не существует.");
            throw new ValidationException();
        }
        anyUser1.getFriends().remove(anyUser2.getId());
        anyUser2.getFriends().remove(anyUser1.getId());
        userStorage.saveUser(anyUser1);
        userStorage.saveUser(anyUser2);
        return anyUser1;
    }

    public Collection<User> getAllFriends(Integer id) {
        User anyUser = userStorage.getUserById(id);
        List<User> userFriendsList = new ArrayList<>();
        if (anyUser == null) {
            log.warn("Пользователя не существует.");
            throw new ValidationException();
        }
        for (Integer elem : anyUser.getFriends()) {
            userFriendsList.add(userStorage.getUserById(elem));
        }
        return userFriendsList;
    }

    public Collection<User> getCommonFriends(Integer id, Integer otherId) {
        User anyUser1 = userStorage.getUserById(id);
        User anyUser2 = userStorage.getUserById(otherId);
        List<User> userCommonFriendsList = new ArrayList<>();
        if (anyUser1 == null || anyUser2 == null) {
            log.warn("Один из пользователей не существует.");
            throw new ValidationException();
        }
        for (Integer elem : anyUser1.getFriends()) {
            if (anyUser2.getFriends().contains(elem)) {
                userCommonFriendsList.add(userStorage.getUserById(elem));
            }
        }
        return userCommonFriendsList;
    }
}
