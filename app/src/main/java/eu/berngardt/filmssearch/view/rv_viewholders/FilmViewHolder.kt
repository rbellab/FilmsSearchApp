package view.rv_viewholders

import android.view.View
import com.bumptech.glide.Glide
import eu.berngardt.filmssearch.domain.Film
import androidx.recyclerview.widget.RecyclerView
import eu.berngardt.filmssearch.data.ApiConstants
import kotlinx.android.synthetic.main.film_item.view.*

// В конструктор класс передается layout, который мы создали(film_item.xml)
class FilmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {
        private const val RATING_CALC_COEFF = 10
    }

    // Привязываем view из layout к переменным
    private val title = itemView.title
    private val poster = itemView.poster
    private val description = itemView.description
    // Вот здесь мы находим в верстке наш прогресс бар для рейтинга
    private val ratingDonut = itemView.rating_donut

    // В этом методе кладем данные из film в наши view
    fun bind(film: Film) {
        film.let{
            copyFilmData(film)
        }
    }

    private fun copyFilmData(film: Film) {
        // Устанавливаем заголовок
        title.text = film.title
        // Устанавливаем постер
        // Указываем контейнер, в которм будет "жить" наша картинка
        Glide.with(itemView)
            // Загружаем сам ресурс
            .load(getFilmPosterUrl(film.poster))
            // Центруем изображение
            .centerCrop()
            // Указываем ImageView, куда будем загружать изображение
            .into(poster)
        // Устанавливаем описание
        description.text = film.description
        // Устанавливаем рэйтинг
        ratingDonut.setProgress((film.rating * RATING_CALC_COEFF).toInt())
    }

    // Метод для полученя URL постера фильма
    private fun getFilmPosterUrl(posterUrl: String) : String {
        return StringBuilder()
            .append(ApiConstants.BASE_MOVIE_IMAGE_URL)
            .append(ApiConstants.MOVIE_POSTER_IMAGE_SIZE)
            .append(posterUrl)
            .toString()
    }
}
