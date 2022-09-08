package eu.berngardt.filmssearch.data


import kotlinx.coroutines.flow.Flow
import eu.berngardt.filmssearch.data.dao.FilmDao
import eu.berngardt.filmssearch.data.entity.Film


class MainRepository(private val filmDao: FilmDao) {

    fun putToDb(films: List<Film>) {
        filmDao.insertAll(films)
    }

    fun getAllFromDB(): Flow<List<Film>> = filmDao.getCachedFilms()

}
