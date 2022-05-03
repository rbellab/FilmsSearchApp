package eu.berngardt.filmssearch.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import eu.berngardt.filmssearch.storage.Film
import kotlinx.android.synthetic.main.film_item.view.*

// В конструктор класс передается layout, который мы создали(film_item.xml)
class FilmViewHolder(itemView: View)
    : RecyclerView.ViewHolder(itemView)
{
    // Привязываем view из layout к переменным
    private val title = itemView.title
    private val poster = itemView.poster
    private val description = itemView.description

    // В этом методе кладем данные из film в наши view
    fun bind(film: Film) {
        film?.let{
            copyFilmData(film)
        }
    }

    private fun copyFilmData(film: Film) {
        // Устанавливаем заголовок
        title.text = film.title

        // Устанавливаем картинку
        poster.setImageResource(film.poster)

        // Устанавливаем описание
        description.text = film.description
    }
}
