package eu.berngardt.filmssearch.viewmodel

import androidx.lifecycle.ViewModel
import eu.berngardt.filmssearch.App
import androidx.lifecycle.MutableLiveData
import eu.berngardt.filmssearch.domain.Film
import eu.berngardt.filmssearch.domain.Interactor

class HomeFragmentViewModel : ViewModel() {
    val filmsListLiveData:  MutableLiveData<List<Film>> = MutableLiveData()

    // Инициализируем интерактор
    private var interactor: Interactor = App.instance.interactor

    init {
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