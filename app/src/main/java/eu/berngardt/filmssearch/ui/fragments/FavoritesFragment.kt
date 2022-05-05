package eu.berngardt.filmssearch.ui.fragments

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import eu.berngardt.filmssearch.MainActivity
import eu.berngardt.filmssearch.storage.Film
import androidx.recyclerview.widget.LinearLayoutManager
import eu.berngardt.filmssearch.ui.FilmListRecyclerAdapter
import eu.berngardt.filmssearch.ui.TopSpacingItemDecoration
import eu.berngardt.filmssearch.databinding.FragmentFavoritesBinding


class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private lateinit var filmsAdapter: FilmListRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoritesBinding.inflate(layoutInflater)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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