package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.LocalDate;

@lombok.Data
public class Film {

//    целочисленный идентификатор — id;
//    название — name;
//    описание — description;
//    дата релиза — releaseDate;
//    продолжительность фильма — duration.

    private int id;
    @NotBlank
    @NotNull
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Integer duration;
}
