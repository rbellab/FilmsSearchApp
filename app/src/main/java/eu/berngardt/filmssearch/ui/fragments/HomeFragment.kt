package eu.berngardt.filmssearch.ui.fragments

import java.util.*
import android.view.View
import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import eu.berngardt.filmssearch.MainActivity
import eu.berngardt.filmssearch.storage.Film
import eu.berngardt.filmssearch.storage.FilmsStorage
import androidx.recyclerview.widget.LinearLayoutManager
import eu.berngardt.filmssearch.ui.FilmListRecyclerAdapter
import eu.berngardt.filmssearch.ui.TopSpacingItemDecoration
import eu.berngardt.filmssearch.databinding.FragmentHomeBinding
import eu.berngardt.filmssearch.ui.AnimationHelper
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*


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

        AnimationHelper.performFragmentCircularRevealAnimation(home_fragment_root, requireActivity(), 1)

        makeSearchFieldClickable()
        addTextListenerToSearchFie()
        addQueryTextListenerToSearchFie()
        applyMainRecyclerAdapter()
    }

    private fun makeSearchFieldClickable() {
        _binding?.let {
            // Делаем всё посковое поле "кликабельным"
            it.searchView.setOnClickListener {
                it.search_view.isIconified = false
            }
        }
    }

    private fun addTextListenerToSearchFie() {
        _binding?.let {
            // Подключаем слушателя изменений введенного текста в поиска
            it.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                // Этот метод отрабатывает при нажатии кнопки "поиск" на софт клавиатуре
                override fun onQueryTextSubmit(query: String?): Boolean {
                    TODO("Not yet implemented")
                }

                // Этот метод отрабатывает на каждое изменения текста
                override fun onQueryTextChange(newText: String?): Boolean {
                    TODO("Not yet implemented")
                }

            })
        }
    }

    private fun addQueryTextListenerToSearchFie() {
        _binding?.let {
            it.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{

                // Этот метод отрабатывает при нажатии кнопки "поиск" на софт клавиатуре
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                // Этот метод отрабатывает на каждое изменения текста
                override fun onQueryTextChange(newText: String): Boolean {
                    // Если ввод пуст то вставляем в адаптер всю БД
                    if (newText.isEmpty()) {
                        filmsAdapter.addItems(filmsDataBase!!)
                        return true
                    }

                    // Фильтруем список на поискк подходящих сочетаний
                    val result = filmsDataBase!!.filter {
                        // Чтобы все работало правильно, нужно и запрос, и имя фильма приводить к нижнему регистру
                        it.title.toLowerCase(Locale.getDefault()).contains(newText.toLowerCase(Locale.getDefault()))
                    }

                    // Добавляем в адаптер
                    filmsAdapter.addItems(result)
                    return true
                }
            })
        }
    }

    private fun applyMainRecyclerAdapter() {
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
