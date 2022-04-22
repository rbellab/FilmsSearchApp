package eu.berngardt.filmssearch

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var filmsAdapter: FilmListRecyclerAdapter
    private val filmsDataBase = createFilmsDataBase()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Находим наш RV
        main_recycler.apply {
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

        // Кладем нашу БД в RV
        filmsAdapter.addItems(filmsDataBase)
    }

    private fun createFilmsDataBase(): List<Film> {
        return listOf(
            Film("Я краснею"      , R.drawable.films_poster_1, "Description of the film 1"),
            Film("Coda"           , R.drawable.films_poster_2, "Description of the film 2"),
            Film("Don't look up"  , R.drawable.films_poster_3, "Description of the film 3"),
            Film("Severance"      , R.drawable.films_poster_4, "Description of the film 4"),
            Film("Nightmare allee", R.drawable.films_poster_5, "Description of the film 5"),
            Film("Мстители"       , R.drawable.films_poster_6, "Description of the film 6"),
            Film("Interstellar"   , R.drawable.films_poster_7, "Description of the film 7"),
            Film("Король ричард"  , R.drawable.films_poster_8, "Description of the film 8")
        )
    }

}
