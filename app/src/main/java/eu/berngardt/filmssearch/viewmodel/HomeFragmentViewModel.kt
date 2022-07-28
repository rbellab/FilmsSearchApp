package eu.berngardt.filmssearch.viewmodel

import javax.inject.Inject
import eu.berngardt.filmssearch.App
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import eu.berngardt.filmssearch.domain.Film
import eu.berngardt.filmssearch.domain.Interactor


class HomeFragmentViewModel : ViewModel() {
    val filmsListLiveData: MutableLiveData<List<Film>> = MutableLiveData()

    // Инициализируем интерактор
    @Inject
    lateinit var interactor: Interactor

    init {
        App.instance.dagger.inject(this)
        getFilms()
    }

    fun getFilms() {
        interactor.getFilmsFromApi(
            DEFAULT_PAGE_NUMMER,
            object : ApiCallback {
                override fun onSuccess(films: List<Film>) {
                    filmsListLiveData.postValue(films)
                }

            override fun onFailure() {
                filmsListLiveData.postValue(interactor.getFilmsFromDB())
            }
        })
    }

    interface ApiCallback {
        fun onSuccess(films: List<Film>)
        fun onFailure()
    }

    companion object {
        private const val DEFAULT_PAGE_NUMMER = 1
    }
}