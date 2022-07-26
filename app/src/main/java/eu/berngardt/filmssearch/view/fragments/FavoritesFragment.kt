package eu.berngardt.filmssearch.view.fragments

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import eu.berngardt.filmssearch.domain.Film
import eu.berngardt.filmssearch.view.MainActivity
import eu.berngardt.filmssearch.utils.AnimationHelper
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_favorites.*
import eu.berngardt.filmssearch.view.rv_adapters.FilmListRecyclerAdapter
import eu.berngardt.filmssearch.view.rv_adapters.TopSpacingItemDecoration
import eu.berngardt.filmssearch.databinding.FragmentFavoritesBinding


class FavoritesFragment : Fragment() {

    private lateinit var filmsAdapter: FilmListRecyclerAdapter
    private lateinit var binding: FragmentFavoritesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Получаем список при транзакции фрагмента
        val favoritesList: List<Film> = emptyList()

        AnimationHelper.performFragmentCircularRevealAnimation(favorites_fragment_root, requireActivity(), ANIMATION_POSITION)

        binding.favoritesRecycler.apply {
            filmsAdapter = FilmListRecyclerAdapter(object : FilmListRecyclerAdapter.OnItemClickListener {
                override fun click(film: Film) {
                    (requireActivity() as MainActivity).launchDetailsFragment(film)
                }
            })

            // Присваиваем адаптер
            adapter = filmsAdapter

            // Присвои layoutmanager
            layoutManager = LinearLayoutManager(requireContext())

            // Применяем декоратор для отступов
            val decorator = TopSpacingItemDecoration(DECORATOR_PADDING)
            addItemDecoration(decorator)
        }

        // Кладем нашу БД в RV
        filmsAdapter.addItems(favoritesList)
    }

    companion object {
        private const val DECORATOR_PADDING = 8
        private const val ANIMATION_POSITION = 2
    }
}