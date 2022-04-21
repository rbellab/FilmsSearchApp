package eu.berngardt.filmssearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import eu.berngardt.filmssearch.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var filmsAdapter: FilmListRecyclerAdapter
    private val filmsDataBase = listOf(
        Film("Я краснею"      , R.drawable.films_poster_1, "Description of the film 1"),
        Film("Coda"           , R.drawable.films_poster_2, "Description of the film 2"),
        Film("Don't look up"  , R.drawable.films_poster_3, "Description of the film 3"),
        Film("Severance"      , R.drawable.films_poster_4, "Description of the film 4"),
        Film("Nightmare allee", R.drawable.films_poster_5, "Description of the film 5"),
        Film("Мстители"       , R.drawable.films_poster_6, "Description of the film 6"),
        Film("Interstellar"   , R.drawable.films_poster_7, "Description of the film 7"),
        Film("Король ричард"  , R.drawable.films_poster_8, "Description of the film 8"),
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        binding = FragmentHomeBinding.inflate(layoutInflater)
        initRecyclerView()
        return binding.root
    }

    private fun initRecyclerView() {
        binding.mainRecycler.apply {

            // Инициализируем наш адаптер в конструктор передаем анонимно инициализированный интерфейс,
            // оставим его пока пустым, он нам понадобится во второй части задания
            filmsAdapter = FilmListRecyclerAdapter(object : FilmListRecyclerAdapter.OnItemClickListener{
                override fun click(film: Film) {
                }
            })

            //Присваиваем адаптер
            adapter = filmsAdapter

            //Присвои layoutmanager
            layoutManager = LinearLayoutManager(requireContext())

            //Применяем декоратор для отступов
            val decorator = TopSpacingItemDecoration(8)
            addItemDecoration(decorator)
        }

        // Кладем нашу БД в RV
        filmsAdapter.addItems(filmsDataBase)
    }


}