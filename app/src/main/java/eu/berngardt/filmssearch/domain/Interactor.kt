package eu.berngardt.filmssearch.domain

import eu.berngardt.filmssearch.data.*
import eu.berngardt.filmssearch.data.Entity.TmdbResults
import eu.berngardt.filmssearch.utils.Converter
import eu.berngardt.filmssearch.viewmodel.HomeFragmentViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Interactor(private val repo: MainRepository, private val retrofitService: TmdbApi) {
    // В конструктор мы будм передавать коллбэк из вьюмоделе, чтобы реагировать на то, когда фильмы будут получены
    // и страницу, котороую нужно загрузить (это для пагинации)
    fun getFilmsFromApi(page: Int, callback: HomeFragmentViewModel.ApiCallback) {
        retrofitService.getFilms(API.KEY, FILM_DATA_API_LANGUAGE, page).enqueue(object : Callback<TmdbResults> {
            override fun onResponse(call: Call<TmdbResults>, response: Response<TmdbResults>) {
                // При успехе мы вызываем метод передаем onSuccess и в этот коллбэк список фильмов
                callback.onSuccess(Converter.convertApiListToDTOList(response.body()?.tmdbFilms))
            }

            override fun onFailure(call: Call<TmdbResults>, t: Throwable) {
                // В случае провала вызываем другой метод коллбека
                callback.onFailure()
            }
        })
    }

    companion object {
        private const val FILM_DATA_API_LANGUAGE = "ru-RU"
    }
}