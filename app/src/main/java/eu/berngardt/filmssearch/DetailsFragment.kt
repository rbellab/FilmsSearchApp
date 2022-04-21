package eu.berngardt.filmssearch

import android.view.View
import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import eu.berngardt.filmssearch.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFilmsDetails()
    }

    private fun setFilmsDetails() {
        // Получаем наш фильм
        val film = arguments?.get("film") as Film
        if (film != null) copyFilmData(film)
    }

    private fun copyFilmData(film: Film) {
        // Устанавливаем заголовок
        binding.detailsToolbar.title = film.title

        // Устанавливаем картинку
        binding.detailsPoster.setImageResource(film.poster)

        // Устанавливаем описание
        binding.detailsDescription.text = film.description
    }
}
