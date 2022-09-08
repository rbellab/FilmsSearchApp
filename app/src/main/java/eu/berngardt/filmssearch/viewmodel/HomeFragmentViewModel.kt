package eu.berngardt.filmssearch.viewmodel

import javax.inject.Inject
import kotlinx.coroutines.launch
import eu.berngardt.filmssearch.App
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import eu.berngardt.filmssearch.data.entity.Film
import eu.berngardt.filmssearch.domain.Interactor


class HomeFragmentViewModel : ViewModel() {
    //Инициализируем интерактор
    @Inject
    lateinit var interactor: Interactor
    val filmsListData: Flow<List<Film>>
    val showProgressBar: Channel<Boolean>

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
