package eu.berngardt.filmssearch.di.modules

import dagger.Module
import dagger.Provides
import androidx.room.Room
import javax.inject.Singleton
import android.content.Context
import eu.berngardt.filmssearch.data.MainRepository
import eu.berngardt.filmssearch.data.dao.FilmDao
import eu.berngardt.filmssearch.data.db.AppDatabase

@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideFilmDao(context: Context) =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DB_NAME
        ).build().filmDao()

    @Provides
    @Singleton
    fun provideRepository(filmDao: FilmDao) = MainRepository(filmDao)

    companion object {
        private const val DB_NAME = "film_db"
    }
}
