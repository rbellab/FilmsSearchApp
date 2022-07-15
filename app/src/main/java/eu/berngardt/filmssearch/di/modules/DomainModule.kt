package eu.berngardt.filmssearch.di.modules

import eu.berngardt.filmssearch.data.MainRepository
import eu.berngardt.filmssearch.data.TmdbApi
import eu.berngardt.filmssearch.domain.Interactor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {
    @Singleton
    @Provides
    fun provideInteractor(repository: MainRepository, tmdbApi: TmdbApi) = Interactor(repo = repository, retrofitService = tmdbApi)
}