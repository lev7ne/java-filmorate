# java-filmorate

Template repository for Filmorate project.
![filmorate-db_scheme.jpg](filmorate-db_scheme.jpg)
***
_Получение списка всех фильмов:_
`SELECT * FROM film;`
***
_Вывести ТОП-10 популярных фильмов:_
`SELECT film_id, COUNT(like_id) AS likes
FROM like
GROUP BY film_id
ORDER BY likes DESC
LIMIT 10;`
***
_Вывод списка всех пользователей:_
`SELECT * FROM user;`
***
_Пример запроса для вывода друзей пользователя с id=n:_
`SELECT friend_id
FROM friendship
WHERE user_id = n;`