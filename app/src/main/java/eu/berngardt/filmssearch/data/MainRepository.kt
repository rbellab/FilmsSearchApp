package eu.berngardt.filmssearch.data

import io.reactivex.rxjava3.core.Observable
import eu.berngardt.filmssearch.data.dao.FilmDao
import eu.berngardt.filmssearch.data.entity.Film

import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow


class MainRepository(private val filmDao: FilmDao) {

    fun putToDb(films: List<Film>) {
        filmDao.insertAll(films)
    }

    fun getAllFromDB(): Observable<List<Film>> = filmDao.getCachedFilms()

}
