package eu.berngardt.filmssearch.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import eu.berngardt.filmssearch.data.dao.FilmDao
import eu.berngardt.filmssearch.data.entity.Film

@Database(entities = [Film::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun filmDao(): FilmDao
}
