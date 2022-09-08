package eu.berngardt.filmssearch.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.Observable
import eu.berngardt.filmssearch.data.entity.Film

// Помечаем, что это не просто интерфейс а Dao-объект
@Dao
interface FilmDao {
    // Запрос на всю таблицу
    @Query("SELECT * FROM cached_films")
    fun getCachedFilms(): Observable<List<Film>>

    // Кладем список в БД, в случае конфликта - перезаписываем
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Film>)
}
