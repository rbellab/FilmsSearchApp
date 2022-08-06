package eu.berngardt.filmssearch.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert
import androidx.lifecycle.LiveData
import androidx.room.OnConflictStrategy
import eu.berngardt.filmssearch.data.entity.Film

// Помечаем, что это не просто интерфейс а Dao-объект
@Dao
interface FilmDao {
    // Запрос на всю таблицу
    @Query("SELECT * FROM cached_films")
    fun getCachedFilms(): LiveData<List<Film>>

    // Кладем список в БД, в случае конфликта - перезаписываем
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Film>)
}
