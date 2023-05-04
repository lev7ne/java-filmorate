package ru.yandex.practicum.filmorate.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.time.LocalDate;

@lombok.Data
@lombok.Builder
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class Film {
    private int id;
    @NotBlank
    @NotNull
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Integer duration;
}
