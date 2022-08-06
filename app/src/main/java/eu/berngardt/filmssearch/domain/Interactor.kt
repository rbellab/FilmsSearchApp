package eu.berngardt.filmssearch.domain

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.lifecycle.LiveData
import eu.berngardt.filmssearch.data.*
import eu.berngardt.filmssearch.utils.Converter
import eu.berngardt.filmssearch.data.entity.Film
import eu.berngardt.filmssearch.data.entity.TmdbResults
import eu.berngardt.filmssearch.data.preferenes.PreferenceProvider
import eu.berngardt.filmssearch.viewmodel.HomeFragmentViewModel


class Interactor(private val repo: MainRepository, private val retrofitService: TmdbApi, private val preferences: PreferenceProvider) {
    fun getFilmsFromApi(page: Int, callback: HomeFragmentViewModel.ApiCallback) {
        // Метод getDefaultCategoryFromPreferences() будет нам получать при каждом запросе нужный нам список фильмов
        retrofitService.getFilms(getDefaultCategoryFromPreferences(), API.KEY, API.LANGUAGE, page).enqueue(object : Callback<TmdbResults> {

            override fun onResponse(call: Call<TmdbResults>, response: Response<TmdbResults>) {
                // При успехе мы вызываем метод передаем onSuccess и в этот коллбэк список фильмов
                val list = Converter.convertApiListToDTOList(response.body()?.tmdbFilms)
                // Кладем фильмы в бд
                repo.putToDb(list)
                callback.onSuccess()
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

    fun getFilmsFromDB(): LiveData<List<Film>> = repo.getAllFromDB()
}
