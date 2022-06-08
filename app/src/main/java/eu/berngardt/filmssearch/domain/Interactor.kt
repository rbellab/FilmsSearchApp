package eu.berngardt.filmssearch.domain

import eu.berngardt.filmssearch.data.MainRepository

class Interactor(val repo: MainRepository) {
    fun getFilmsDB(): List<Film>? = repo.let {
        return it.filmsDataBase
    }
}