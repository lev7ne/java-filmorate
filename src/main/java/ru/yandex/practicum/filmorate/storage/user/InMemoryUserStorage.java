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
}