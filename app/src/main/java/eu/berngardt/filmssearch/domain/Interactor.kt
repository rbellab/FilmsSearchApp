package eu.berngardt.filmssearch.domain

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import eu.berngardt.filmssearch.data.*
import eu.berngardt.filmssearch.utils.Converter
import eu.berngardt.filmssearch.data.Entity.TmdbResults
import eu.berngardt.filmssearch.viewmodel.HomeFragmentViewModel
import eu.berngardt.filmssearch.data.preferenes.PreferenceProvider


class Interactor(private val repo: MainRepository, private val retrofitService: TmdbApi, private val preferences: PreferenceProvider) {

    fun getFilmsFromApi(page: Int, callback: HomeFragmentViewModel.ApiCallback) {
        // Метод getDefaultCategoryFromPreferences() будет нам получать при каждом запросе нужный нам список фильмов
        retrofitService.getFilms(getDefaultCategoryFromPreferences(), API.KEY, API.LANGUAGE, page).enqueue(object : Callback<TmdbResults> {

            override fun onResponse(call: Call<TmdbResults>, response: Response<TmdbResults>) {

                val list = Converter.convertApiListToDTOList(response.body()?.tmdbFilms)
                // Кладем фильмы в бд
                list.forEach {
                    repo.putToDb(film = it)
                }
                callback.onSuccess(list)
            }

            override fun onFailure(call: Call<TmdbResults>, t: Throwable) {
                // В случае провала вызываем другой метод коллбека
                callback.onFailure()
            }
        })
    }

    // Метод для сохранения настроек
    fun saveDefaultCategoryToPreferences(category: String) {
        preferences.saveDefaultCategory(category)
    }

    // Метод для получения настроек
    fun getDefaultCategoryFromPreferences() = preferences.geDefaultCategory()

    // Метод для получения списка фильмов из БД
    fun getFilmsFromDB(): List<Film> = repo.getAllFromDB()
}