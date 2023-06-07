package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FriendshipDao;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.Validator;

import java.util.Collection;

@Slf4j
@Service
public class UserService {
    private final UserDao userDao;
    private final FriendshipDao friendshipDao;
    private final Validator validator;

    @Autowired
    public UserService(UserDao userDao, FriendshipDao friendshipDao, Validator validator) {
        this.userDao = userDao;
        this.validator = validator;
        this.friendshipDao = friendshipDao;
    }

    public User createUser(User user) {
        validator.validateUser(user);
        return userDao.create(user);
    }

    public User updateUser(User user) {
        validator.validateUser(user);
        return userDao.update(user);
    }

    public User getUserById(Integer id) { // работает с замечанием !!!
        return userDao.getById(id);
    }

    public Collection<User> getUsers() { // работает
        return userDao.getAll();
    }

    public void addFriend(Integer id, Integer friendId) {
        getUserById(friendId);
        friendshipDao.addFriend(id, friendId);
    }

    public void deleteFriend(Integer id, Integer friendId) { // работает
        friendshipDao.deleteFriend(id, friendId);
    }

    public Collection<User> getFriendship(Integer id) {
        return friendshipDao.getFriendship(id);
    }

    public Collection<User> getCommonFriendship(Integer id, Integer otherId) {
        return friendshipDao.getCommonFriendship(id, otherId);
    }
}
