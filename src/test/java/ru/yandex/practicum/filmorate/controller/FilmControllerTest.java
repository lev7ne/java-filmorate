package ru.yandex.practicum.filmorate.controller;

public class FilmControllerTest {
//    Validator validator = new Validator();
//    JdbcTemplate jdbcTemplate = new JdbcTemplate();
//    UserStorage userStorage = new UserDbStorage(jdbcTemplate);
//    FilmStorage filmStorage = new FilmDbStorage();
//    FilmService filmService = new FilmService(filmStorage, userStorage, validator);
//    FilmController filmController = new FilmController(filmService);
//
//    @DisplayName("Проверка валидации фильма")
//    @Test
//    void shouldThrowValidateException() {
//        Film film = Film.builder()
//                .filmName("Титаник")
//                .description("Описание фильма")
//                .releaseDate(LocalDate.of(993, 12, 4))
//                .duration((short) 120)
//                .build();
//        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> filmController.createFilm(film));
//        assertEquals("Дата релиза фильма " + film.getReleaseDate() + " раньше даты рождения кинематографа.", exception.getMessage());
//    }
//
//    @DisplayName("Добавления фильма в Collection")
//    @Test
//    void shouldSaveFilm() {
//        Film film = Film.builder()
//                .filmName("Титаник")
//                .description("Описание фильма")
//                .releaseDate(LocalDate.of(1993, 12, 4))
//                .duration((short) 180)
//                .build();
//        filmController.createFilm(film);
//        assertEquals(filmController.getFilms().size(), 1);
//        assertTrue(filmController.getFilms().contains(film));
//    }
}
