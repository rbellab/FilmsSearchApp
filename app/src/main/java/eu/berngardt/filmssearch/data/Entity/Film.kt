package eu.berngardt.filmssearch.data.entity

import androidx.room.Index
import androidx.room.Entity
import android.os.Parcelable
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "cached_films", indices = [Index(value = ["title"], unique = true)])
data class Film(
  @PrimaryKey(autoGenerate = true) val id: Int = 0,
  @ColumnInfo(name = "title") val title: String,
  @ColumnInfo(name = "poster_path") val poster: String?,
  @ColumnInfo(name = "overview") val description: String,
  @ColumnInfo(name = "vote_average") var rating: Double = 0.0,
  var isInFavorites: Boolean = false
) : Parcelable