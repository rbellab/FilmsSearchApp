package eu.berngardt.filmssearch.di.modules

import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import android.content.Context
import eu.berngardt.filmssearch.data.TmdbApi
import eu.berngardt.filmssearch.domain.Interactor
import eu.berngardt.filmssearch.data.MainRepository
import eu.berngardt.filmssearch.data.preferenes.PreferenceProvider

@Module
// Передаем контекст для SharedPreferences через конструктор
class DomainModule(val context: Context) {
    // Нам нужно контекст как-то провайдить, поэтому создаем такой метод
    @Provides
    fun provideContext() = context

    @Singleton
    @Provides
    // Создаем экземпляр SharedPreferences
    fun providePreferences(context: Context) = PreferenceProvider(context)

    @Singleton
    @Provides
    fun provideInteractor(repository: MainRepository, tmdbApi: TmdbApi, preferenceProvider: PreferenceProvider)
        = Interactor(repo = repository, retrofitService = tmdbApi, preferences = preferenceProvider)
}
