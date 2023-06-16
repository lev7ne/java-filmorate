package ru.yandex.practicum.filmorate.controller;

public class UserControllerTest {
//    Validator validator = new Validator();
//    UserStorage inMemoryUserStorage = new InMemoryUserStorage();
//    JdbcTemplate jdbcTemplate;
//    UserStorage userDbStorage = new UserDbStorage(jdbcTemplate);
//    UserService userService = new UserService(inMemoryUserStorage, validator, userDbStorage);
//    UserController userController = new UserController(userService);
//
//    @DisplayName("Проверка валидации пользователя")
//    @Test
//    void shouldThrowValidateException() {
//        User user = User.builder()
//                .email("ros.kh.23@yandex.ru")
//                .login("Lev7ne")
//                .userName("Rostislav")
//                .birthday(LocalDate.of(2993, 11, 02))
//                .build();
//        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> userController.createUser(user));
//        assertEquals("Попытка добавить пользователя с датой рождения в будущем: " + user.getBirthday(), exception.getMessage());
//    }
//
//    @DisplayName("Проверка подмены имени логином")
//    @Test
//    void shouldChangeLoginWithoutName() {
//        User user = User.builder()
//                .email("ros.kh.23@yandex.ru")
//                .login("Lev7ne")
//                .birthday(LocalDate.of(1993, 11, 02))
//                .build();
//        userController.createUser(user);
//        assertTrue(userController.getUsers().contains(user));
//        User anyUser = userController.getUsers()
//                .stream()
//                .filter(user1 -> Objects.equals(user1.getEmail(), "ros.kh.23@yandex.ru"))
//                .findFirst()
//                .orElse(null);
//        assertEquals(anyUser.getUserName(), anyUser.getLogin());
//    }
}
