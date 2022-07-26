package eu.berngardt.filmssearch.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        // Создаем таблицу для фильмов
        db?.execSQL(
            StringBuilder()
                .append("CREATE TABLE $TABLE_NAME (")
                .append("$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,")
                .append("$COLUMN_TITLE TEXT UNIQUE,")
                .append("$COLUMN_POSTER TEXT,")
                .append("$COLUMN_DESCRIPTION TEXT,")
                .append("$COLUMN_RATING REAL);")
                .toString()
        )
    }

    // Миграций не преполагается -> метод пустой
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Nothing to do yet
    }

    companion object {
        // Название БД
        private const val DATABASE_NAME = "films.db"

        // Версия БД
        private const val DATABASE_VERSION = 1

        // Константы для работы с таблицей, они понабатся в CRUD операциях
        // и, возможно, в составлении запросов
        const val TABLE_NAME = "films_table"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_POSTER = "poster_path"
        const val COLUMN_DESCRIPTION = "overview"
        const val COLUMN_RATING = "vote_average"
    }
}