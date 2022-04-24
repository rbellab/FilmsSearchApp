package eu.berngardt.filmssearch.ui.fragments

import android.view.View
import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import eu.berngardt.filmssearch.MainActivity
import eu.berngardt.filmssearch.storage.Film
import eu.berngardt.filmssearch.storage.FilmsStorage
import androidx.recyclerview.widget.LinearLayoutManager
import eu.berngardt.filmssearch.ui.FilmListRecyclerAdapter
import eu.berngardt.filmssearch.ui.TopSpacingItemDecoration
import eu.berngardt.filmssearch.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var filmsAdapter: FilmListRecyclerAdapter
    private val filmsDataBase = FilmsStorage.getFilmsList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Находим наш RV
        _binding?.let {
            it.mainRecycler.apply {
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
        filmsDataBase?.let { filmsAdapter.addItems(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
