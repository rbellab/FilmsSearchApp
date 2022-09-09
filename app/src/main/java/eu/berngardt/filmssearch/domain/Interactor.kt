package eu.berngardt.filmssearch.domain

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.Dispatchers
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.CoroutineScope
import eu.berngardt.filmssearch.data.API
import kotlinx.coroutines.channels.Channel
import io.reactivex.rxjava3.core.Observable
import eu.berngardt.filmssearch.data.TmdbApi
import io.reactivex.rxjava3.core.Completable
import eu.berngardt.filmssearch.utils.Converter
import eu.berngardt.filmssearch.data.entity.Film
import io.reactivex.rxjava3.schedulers.Schedulers
import eu.berngardt.filmssearch.data.MainRepository
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.BehaviorSubject
import eu.berngardt.filmssearch.data.entity.TmdbResults
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import eu.berngardt.filmssearch.data.preferenes.PreferenceProvider


class Interactor(private val repo: MainRepository, private val retrofitService: TmdbApi, private val preferences: PreferenceProvider) {
    var progressBarState: BehaviorSubject<Boolean> = BehaviorSubject.create()

    fun getFilmsFromApi(page: Int) {
        // Показываем ProgressBar
        progressBarState.onNext(true)
	
        // Метод getDefaultCategoryFromPreferences() будет нам получать при каждом запросе нужный нам список фильмов
        retrofitService.getFilms(getDefaultCategoryFromPreferences(), API.KEY, "ru-RU", page).enqueue(object : Callback<TmdbResults> {
            override fun onResponse(call: Call<TmdbResults>, response: Response<TmdbResults>) {
                val list = Converter.convertApiListToDTOList(response.body()?.tmdbFilms)
                //Кладем фильмы в бд
                //В случае успешного ответа кладем фильмы в БД и выключаем ProgressBar
                Completable.fromSingle<List<Film>> {
                    repo.putToDb(list)
                }
                .subscribeOn(Schedulers.io())
                .subscribe()
                progressBarState.onNext(false)
            }

            override fun onFailure(call: Call<TmdbResults>, t: Throwable) {
                //В случае провала выключаем ProgressBar
                progressBarState.onNext(false)
            }
        })
    }

    // Метод для сохранения настроек
    fun saveDefaultCategoryToPreferences(category: String) {
        preferences.saveDefaultCategory(category)
    }

    // Метод для получения настроек
    fun getDefaultCategoryFromPreferences() = preferences.geDefaultCategory()

    fun getFilmsFromDB(): Observable<List<Film>> = repo.getAllFromDB()
}
