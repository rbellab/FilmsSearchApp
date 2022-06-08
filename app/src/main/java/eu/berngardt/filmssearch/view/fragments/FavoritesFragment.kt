package view.fragments

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import eu.berngardt.filmssearch.domain.Film
import androidx.recyclerview.widget.LinearLayoutManager
import view.rv_adapters.FilmListRecyclerAdapter
import view.rv_decorations.TopSpacingItemDecoration
import eu.berngardt.filmssearch.databinding.FragmentFavoritesBinding
import eu.berngardt.filmssearch.utils.AnimationHelper
import eu.berngardt.filmssearch.view.MainActivity
import kotlinx.android.synthetic.main.fragment_favorites.*


class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private lateinit var filmsAdapter: FilmListRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FrameLayout? {
        _binding = FragmentFavoritesBinding.inflate(layoutInflater)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AnimationHelper.performFragmentCircularRevealAnimation(fragment_favorites, requireActivity(), 2)

        // Получаем список при транзакции фрагмента
        val favoritesList: List<Film> = emptyList()
        _binding?.let {
            _binding!!.favoritesRecycler.apply {
                filmsAdapter = FilmListRecyclerAdapter(object : FilmListRecyclerAdapter.OnItemClickListener{
                    override fun click(film: Film) {
                        (requireActivity() as MainActivity).launchDetailsFragment(film)
                    }
                })

                // Присваиваем адаптер
                adapter = filmsAdapter

                // Присвои layoutmanager
                layoutManager = LinearLayoutManager(requireContext())

                // Применяем декоратор для отступов
                val decorator = TopSpacingItemDecoration(8)
                addItemDecoration(decorator)
            }
        }

        // Кладем нашу БД в RV
        filmsAdapter.addItems(favoritesList)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}