package eu.berngardt.filmssearch.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import eu.berngardt.filmssearch.domain.Film
import eu.berngardt.filmssearch.R
import eu.berngardt.filmssearch.data.ApiConstants
import eu.berngardt.filmssearch.databinding.FragmentDetailsBinding
import com.bumptech.glide.Glide

class DetailsFragment : Fragment() {
    private lateinit var film: Film

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFilmsDetails()

        binding.detailsFabFavorites.setOnClickListener {
            if (!film.isInFavorites) {
                binding.detailsFabFavorites.setImageResource(R.drawable.ic_baseline_favorite_24)
                film.isInFavorites = true
            } else {
                binding.detailsFabFavorites.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                film.isInFavorites = false
            }
        }

        binding.detailsFabShare.setOnClickListener {
            // Создаем интент
            val intent = Intent()
            // Укзываем action с которым он запускается
            intent.action = Intent.ACTION_SEND
            // Кладем данные о нашем фильме
            intent.putExtra(
                Intent.EXTRA_TEXT,
                "Привет! Зацени киношку: ${film.title} \n\n ${film.description}"
            )
            // УКазываем MIME тип, чтобы система знала, какое приложения предложить
            intent.type = "text/plain"
            // Запускаем наше активити
            startActivity(Intent.createChooser(intent, "Поделиться с:"))
        }
    }

    private fun setFilmsDetails() {
        // Получаем наш фильм из переданного бандла
        film = arguments?.get("film") as Film

        // Устанавливаем заголовок
        binding.detailsToolbar.title = film.title
        // Устанавливаем картинку
        Glide.with(this)
            .load(ApiConstants.BASE_MOVIE_IMAGE_URL + "w780" + film.poster)
            .centerCrop()
            .into(binding.detailsPoster)
        // Устанавливаем описание
        binding.detailsDescription.text = film.description

        binding.detailsFabFavorites.setImageResource(
            if (film.isInFavorites) R.drawable.ic_baseline_favorite_24
            else R.drawable.ic_baseline_favorite_border_24
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}