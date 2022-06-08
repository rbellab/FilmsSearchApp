package eu.berngardt.filmssearch.data

import eu.berngardt.filmssearch.R
import eu.berngardt.filmssearch.domain.Film

object FilmsStorage {
    private var _filmsList: List<Film>? = null

    init {
        createFilmsList()
    }

    private fun createFilmsList() {
        _filmsList = listOf(
            Film("Я краснею",
                R.drawable.films_poster_1,
                "Description of the film 1", 2.0f),

            Film("Coda",
                R.drawable.films_poster_2,
                "Description of the film 2", 3.1f),

            Film("Don't look up",
                R.drawable.films_poster_3,
                "Description of the film 3", 4.2f),

            Film("Severance",
                R.drawable.films_poster_4,
                "Description of the film 4", 5.3f),

            Film("Nightmare allee",
                R.drawable.films_poster_5,
                "Description of the film 5", 6.4f),

            Film("Мстители",
                R.drawable.films_poster_6,
                "Description of the film 6", 7.5f),

            Film("Interstellar",
                R.drawable.films_poster_7,
                "Description of the film 7", 8.6f),

            Film("Король ричард",
                R.drawable.films_poster_8,
                "Description of the film 8", 9.5f)
        )
    }

    fun getFilmsList(): List<Film>? {
        return _filmsList
    }
}