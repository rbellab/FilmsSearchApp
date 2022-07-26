package eu.berngardt.filmssearch.di.modules

import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import android.content.Context
import eu.berngardt.filmssearch.data.MainRepository
import eu.berngardt.filmssearch.data.db.DatabaseHelper

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabaseHelper(context: Context) = DatabaseHelper(context)

    @Provides
    @Singleton
    fun provideRepository(databaseHelper: DatabaseHelper) = MainRepository(databaseHelper)
}