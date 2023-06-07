package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class FilmGenre {
    @JsonIgnore
    private int filmId;
    private int id;
    private String name;

    public FilmGenre(int id, String name, int filmId) {
        this.filmId = filmId;
        this.id = id;
        this.name = name;
    }

    public FilmGenre(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
