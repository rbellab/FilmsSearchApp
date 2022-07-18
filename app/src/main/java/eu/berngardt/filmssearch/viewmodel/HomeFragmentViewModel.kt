package eu.berngardt.filmssearch.viewmodel

import javax.inject.Inject
import androidx.lifecycle.ViewModel
import eu.berngardt.filmssearch.App
import androidx.lifecycle.MutableLiveData
import eu.berngardt.filmssearch.domain.Film
import eu.berngardt.filmssearch.domain.Interactor


class HomeFragmentViewModel : ViewModel() {
    val filmsListLiveData:  MutableLiveData<List<Film>> = MutableLiveData()

    // Инициализируем интерактор
    @Inject
    lateinit var interactor: Interactor

    init {
        App.instance.dagger.inject(this)
        reloadData(1)
    }

    fun reloadData(page: Int) {
        interactor.getFilmsFromApi(page, object : ApiCallback {
            override fun onSuccess(films: List<Film>) {
                filmsListLiveData.postValue(films)
            }

            override fun onFailure() {
            }
        })
    }

    interface ApiCallback {
        fun onSuccess(films: List<Film>)
        fun onFailure()
    }
}