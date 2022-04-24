package eu.berngardt.filmssearch.ui.fragments

import android.view.View
import android.os.Bundle
import android.content.Intent
import android.view.ViewGroup
import eu.berngardt.filmssearch.R
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import eu.berngardt.filmssearch.storage.Film
import eu.berngardt.filmssearch.databinding.FragmentDetailsBinding


class DetailsFragment : Fragment() {

    companion object {
        private const val ARG_KEY_FILM = "film"
    }

    private var _binding: FragmentDetailsBinding? = null
    private lateinit var film: Film

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(layoutInflater)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFilmsDetails()
        initButtons()
    }

    private fun setFilmsDetails() {
        // Получаем наш фильм
        film = arguments?.get(ARG_KEY_FILM) as Film
        film.let {
            copyFilmData(it)
        }
    }

    private fun initButtons() {
        _binding?.let {
            val fav = it.detailsFabFavorites
            it.detailsFabFavorites.setOnClickListener {
                if (!film.isInFavorites) {
                    fav.setImageResource(R.drawable.ic_baseline_favorite_24)
                    film.isInFavorites = true
                } else {
                    fav.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                    film.isInFavorites = false
                }
            }
            it.detailsFabShare.setOnClickListener {
                // Создаем интент
                val intent = Intent()
                // Укзываем action с которым он запускается
                intent.action = Intent.ACTION_SEND
                // Кладем данные о нашем фильме
                intent.putExtra(
                    Intent.EXTRA_TEXT,
                    "Привет! Зацени киношку: ${film.title} \n\n ${film.description}"
                )
                // Указываем MIME тип, чтобы система знала, какое приложения предложить
                intent.type = "text/plain"
                // Запускаем наше активити
                startActivity(Intent.createChooser(intent, "Share To:"))
            }
        }
    }

    private fun copyFilmData(film: Film) {
        _binding.let {
            // Устанавливаем заголовок
            it!!.detailsToolbar.title = film.title

            // Устанавливаем картинку
            it!!.detailsPoster.setImageResource(film.poster)

            // Устанавливаем описание
            it!!.detailsDescription.text = film.description

            // Устанавливаем находится ли в Избранном
            it!!.detailsFabFavorites.setImageResource(
                if (film.isInFavorites) R.drawable.ic_baseline_favorite_24
                else R.drawable.ic_baseline_favorite_border_24
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}