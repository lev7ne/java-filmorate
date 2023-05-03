package ru.yandex.practicum.filmorate.model;

public class Counter {
    private int id = 0;

    public int getId() {
        return ++id;
    }
}
