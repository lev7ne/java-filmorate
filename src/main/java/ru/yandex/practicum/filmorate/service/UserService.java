package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
        User anyUser1 = getUserById(id);
        User anyUser2 = getUserById(friendId);
        anyUser1.getFriends().add(anyUser2.getId());
        anyUser2.getFriends().add(anyUser1.getId());
        userStorage.updateUser(anyUser1);
        userStorage.updateUser(anyUser2);
        return anyUser1;
    }

    public User deleteFriend(Integer id, Integer friendId) {
        User anyUser1 = getUserById(id);
        User anyUser2 = getUserById(friendId);
        anyUser1.getFriends().remove(anyUser2.getId());
        anyUser2.getFriends().remove(anyUser1.getId());
        userStorage.updateUser(anyUser1);
        userStorage.updateUser(anyUser2);
        return anyUser1;
    }

    public Collection<User> getAllFriends(Integer id) {
        User anyUser = getUserById(id);
        List<User> userFriendsList = new ArrayList<>();
        for (Integer elem : anyUser.getFriends()) {
            userFriendsList.add(getUserById(elem));
        }
        return userFriendsList;
    }

    public Collection<User> getCommonFriends(Integer id, Integer otherId) {
        User anyUser1 = getUserById(id);
        User anyUser2 = getUserById(otherId);
        List<User> userCommonFriendsList = new ArrayList<>();
        for (Integer elem : anyUser1.getFriends()) {
            if (anyUser2.getFriends().contains(elem)) {
                userCommonFriendsList.add(getUserById(elem));
            }
        }
        return userCommonFriendsList;
    }
}
