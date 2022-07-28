package eu.berngardt.filmssearch.data

import android.database.Cursor
import android.content.ContentValues
import eu.berngardt.filmssearch.domain.Film
import eu.berngardt.filmssearch.data.db.DatabaseHelper

class MainRepository(databaseHelper: DatabaseHelper) {

    // Инициализируем объект для взаимодействия с БД
    private val sqlDb = databaseHelper.readableDatabase
    // Создаем курсор для обработки запросов из БД
    private lateinit var cursor: Cursor

    fun putToDb(film: Film) {
        // Создаем объект, который будет хранить пары ключ значения, для того,
        // чтобы класть нужные данные в нужные столбцы
        val cv = ContentValues()
        cv.apply {
            put(DatabaseHelper.COLUMN_TITLE, film.title)
            put(DatabaseHelper.COLUMN_POSTER, film.poster)
            put(DatabaseHelper.COLUMN_DESCRIPTION, film.description)
            put(DatabaseHelper.COLUMN_RATING, film.rating)
        }
        // Клдаем фильм в бд
        sqlDb.insert(DatabaseHelper.TABLE_NAME, null, cv)
    }

    fun getAllFromDB(): List<Film> {
        // Создаем курсор на основание запроса "Получить все из таблицы"
        cursor = sqlDb.rawQuery("SELECT * FROM ${DatabaseHelper.TABLE_NAME}", null)

        // Здесь будет храниться результат (данные из БД)
        val result = mutableListOf<Film>()

        // Проверяем есть ли хоть одна строка в ответе на запрос
        if (cursor.moveToFirst()) {
            // Итерируемся по таблице, пока есть записи и создаем на основании объект Film
            do {
                val title = cursor.getString(1)
                val poster = cursor.getString(2)
                val description = cursor.getString(3)
                val rating = cursor.getDouble(4)

                result.add(Film(title, poster, description, rating))
            } while (cursor.moveToNext())
        }
        //Возвращаем список фильмов
        return result
    }
}