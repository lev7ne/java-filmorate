package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Counter;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.Validator;

import java.util.*;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {
    private final Counter counter = new Counter();
    private final Map<Integer, User> users = new HashMap<>();
    private final Validator validator;

    @Autowired
    public InMemoryUserStorage(Validator validator) {
        this.validator = validator;
    }

    @Override
    public User createUser(User user) {
        validator.validateUser(user);
        int id = counter.getId();
        user.setId(id);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        getUserById(user.getId());
        validator.validateUser(user);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public Collection<User> getUsers() {
        return users.values();
    }

    @Override
    public User getUserById(Integer id) {
        User anyUser = users.get(id);
        if (anyUser == null) {
            log.warn("Пользователь с id {} не найден.", id);
            throw new NotFoundException();
        }
        return anyUser;
    }

    @Override
    public User addFriend(Integer id, Integer friendId) {
        User anyUser1 = getUserById(id);
        User anyUser2 = getUserById(friendId);
        anyUser1.getFriends().add(anyUser2.getId());
        anyUser2.getFriends().add(anyUser1.getId());
        users.put(id, anyUser1);
        users.put(friendId, anyUser2);
        return anyUser1;
    }

    @Override
    public User deleteFriend(Integer id, Integer friendId) {
        User anyUser1 = getUserById(id);
        User anyUser2 = getUserById(friendId);
        anyUser1.getFriends().remove(anyUser2.getId());
        anyUser2.getFriends().remove(anyUser1.getId());
        users.put(id, anyUser1);
        users.put(friendId, anyUser2);
        return anyUser1;
    }

    @Override
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
