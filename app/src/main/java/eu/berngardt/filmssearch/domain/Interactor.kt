package eu.berngardt.filmssearch.domain

import eu.berngardt.filmssearch.data.API
import eu.berngardt.remote_module.TmdbApi
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import eu.berngardt.filmssearch.utils.Converter
import eu.berngardt.filmssearch.data.entity.Film
import io.reactivex.rxjava3.schedulers.Schedulers
import eu.berngardt.filmssearch.data.MainRepository
import io.reactivex.rxjava3.subjects.BehaviorSubject
import eu.berngardt.filmssearch.data.preferenes.PreferenceProvider


class Interactor(private val repo: MainRepository, private val retrofitService: TmdbApi, private val preferences: PreferenceProvider) {
    var progressBarState: BehaviorSubject<Boolean> = BehaviorSubject.create()

    fun getFilmsFromApi(page: Int) {
        // Показываем ProgressBar
        progressBarState.onNext(true)
	
        // Метод getDefaultCategoryFromPreferences() будет нам получать при каждом запросе нужный нам список фильмов
        retrofitService.getFilms(getDefaultCategoryFromPreferences(), API.KEY, API.LANGUAGE, page)
            .subscribeOn(Schedulers.io())
            .map {
                Converter.convertApiListToDTOList(it.tmdbFilms)
            }
            .subscribeBy(
                onError = {
                    progressBarState.onNext(false)
                },
                onNext = {
                    progressBarState.onNext(false)
                    repo.putToDb(it)
                }
            )
    }

    fun getSearchResultFromApi(search: String): Observable<List<Film>> = retrofitService.getFilmFromSearch(
        API.KEY,
        API.LANGUAGE,
        search,
        DEFAULT_PAGE_NUMBER).map {
            Converter.convertApiListToDTOList(it.tmdbFilms)
        }

    // Метод для сохранения настроек
    fun saveDefaultCategoryToPreferences(category: String) {
        preferences.saveDefaultCategory(category)
    }

    // Метод для получения настроек
    fun getDefaultCategoryFromPreferences() = preferences.geDefaultCategory()

    fun getFilmsFromDB(): Observable<List<Film>> = repo.getAllFromDB()

    companion object {
        private const val DEFAULT_PAGE_NUMBER = 1
    }
}
