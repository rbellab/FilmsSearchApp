package eu.berngardt.filmssearch.viewmodel

import android.widget.Toast
import javax.inject.Inject
import androidx.lifecycle.LiveData
import eu.berngardt.filmssearch.App
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import eu.berngardt.filmssearch.data.entity.Film
import eu.berngardt.filmssearch.domain.Interactor


class HomeFragmentViewModel : ViewModel() {
    val showProgressBar: MutableLiveData<Boolean> = MutableLiveData()

    // Инициализируем интерактор
    @Inject
    lateinit var interactor: Interactor
    val filmsListLiveData: LiveData<List<Film>>

    init {
        App.instance.dagger.inject(this)
        filmsListLiveData = interactor.getFilmsFromDB()
        getFilms()
    }

    fun getFilms() {
        showProgressBar.postValue(true)
        interactor.getFilmsFromApi(DEFAULT_PAGE_NUMMER, object : ApiCallback {

            override fun onSuccess() {
                showProgressBar.postValue(false)
            }

            override fun onFailure() {
                showProgressBar.postValue(false)
                Toast.makeText(App.instance.baseContext, DATA_ACCESS_ERROR_MSG, Toast.LENGTH_LONG).show()
            }

        })
    }

    interface ApiCallback {
        fun onSuccess()
        fun onFailure()
    }

    companion object {
        private const val DEFAULT_PAGE_NUMMER = 1
        private const val DATA_ACCESS_ERROR_MSG = "ОШИБКА: Не удалось получить данных от сервера -> берём из локальной БД!"
    }
}
