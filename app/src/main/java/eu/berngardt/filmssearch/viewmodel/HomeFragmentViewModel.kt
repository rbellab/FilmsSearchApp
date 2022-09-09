package eu.berngardt.filmssearch.viewmodel

import javax.inject.Inject
import eu.berngardt.filmssearch.App
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Observable
import eu.berngardt.filmssearch.data.entity.Film
import eu.berngardt.filmssearch.domain.Interactor
import io.reactivex.rxjava3.subjects.BehaviorSubject


class HomeFragmentViewModel : ViewModel() {
    //Инициализируем интерактор
    @Inject
    lateinit var interactor: Interactor
    val filmsListData: Observable<List<Film>>
    val showProgressBar: BehaviorSubject<Boolean>

    init {
        App.instance.dagger.inject(this)
        showProgressBar = interactor.progressBarState
        filmsListData = interactor.getFilmsFromDB()
        getFilms()
    }

    fun getFilms() {
        interactor.getFilmsFromApi(DEFAULT_PAGE_NUMMER)
    }

    companion object {
        private const val DEFAULT_PAGE_NUMMER = 1
    }
}
