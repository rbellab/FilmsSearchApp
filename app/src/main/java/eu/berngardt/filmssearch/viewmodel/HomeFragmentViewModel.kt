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
        val films = interactor.getFilmsDB()
        filmsListLiveData.postValue(films)
    }
}