package eu.berngardt.filmssearch.storage

import eu.berngardt.filmssearch.R

object FilmsStorage {
    private var _filmsList: List<Film>? = null

    init {
        createFilmsList()
    }

    private fun createFilmsList() {
        _filmsList = listOf(
            Film("Я краснею"      , R.drawable.films_poster_1, "Description of the film 1"),
            Film("Coda"           , R.drawable.films_poster_2, "Description of the film 2"),
            Film("Don't look up"  , R.drawable.films_poster_3, "Description of the film 3"),
            Film("Severance"      , R.drawable.films_poster_4, "Description of the film 4"),
            Film("Nightmare allee", R.drawable.films_poster_5, "Description of the film 5"),
            Film("Мстители"       , R.drawable.films_poster_6, "Description of the film 6"),
            Film("Interstellar"   , R.drawable.films_poster_7, "Description of the film 7"),
            Film("Король ричард"  , R.drawable.films_poster_8, "Description of the film 8")
        )
    }

    fun getFilmsList(): List<Film>? {
        return _filmsList
    }
}