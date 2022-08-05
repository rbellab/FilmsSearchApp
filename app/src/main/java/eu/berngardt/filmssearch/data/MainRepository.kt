package eu.berngardt.filmssearch.data

import androidx.lifecycle.LiveData
import java.util.concurrent.Executors
import eu.berngardt.filmssearch.data.dao.FilmDao
import eu.berngardt.filmssearch.data.entity.Film


class MainRepository(private val filmDao: FilmDao) {

    fun putToDb(films: List<Film>) {
        // Запросы в БД должны быть в отдельном потоке
        Executors.newSingleThreadExecutor().execute {
            filmDao.insertAll(films)
        }
    }

    fun getAllFromDB(): LiveData<List<Film>> = filmDao.getCachedFilms()

}
