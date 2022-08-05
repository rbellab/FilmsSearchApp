package eu.berngardt.filmssearch.view.fragments

import java.util.*
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import eu.berngardt.filmssearch.data.entity.Film
import eu.berngardt.filmssearch.view.MainActivity
import kotlinx.android.synthetic.main.fragment_home.*
import eu.berngardt.filmssearch.utils.AnimationHelper
import androidx.recyclerview.widget.LinearLayoutManager
import eu.berngardt.filmssearch.databinding.FragmentHomeBinding
import eu.berngardt.filmssearch.viewmodel.HomeFragmentViewModel
import eu.berngardt.filmssearch.view.rv_adapters.FilmListRecyclerAdapter
import eu.berngardt.filmssearch.view.rv_adapters.TopSpacingItemDecoration


class HomeFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(HomeFragmentViewModel::class.java)
    }
    private lateinit var filmsAdapter: FilmListRecyclerAdapter
    private lateinit var binding: FragmentHomeBinding
    private var filmsDataBase = listOf<Film>()
        // Используем backing field
        set (value) {
            // Если придет такое же значение то мы выходим из метода
            if (field == value) return
            // Если прило другое значение, то кладем его в переменную
            field = value
            // Обновляем RV адаптер
            filmsAdapter.addItems(field)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AnimationHelper.performFragmentCircularRevealAnimation(home_fragment_root, requireActivity(), 1)

        initSearchView()
        initPullToRefresh()
        initRecyckler()
        initObservers()
    }

    private fun initObservers() {
        // Кладем нашу БД в RV
        viewModel.filmsListLiveData.observe(viewLifecycleOwner, Observer<List<Film>> {
            filmsDataBase = it
            filmsAdapter.addItems(it)
        })

        viewModel.showProgressBar.observe(viewLifecycleOwner, Observer<Boolean> {
            binding.progressBar.isVisible = it
        })
    }

    private fun initPullToRefresh() {
        // Вешаем слушатель, чтобы вызвался pull to refresh
        binding.pullToRefresh.setOnRefreshListener {
            // Чистим адаптер(items нужно будет сделать паблик или создать для этого публичный метод)
            filmsAdapter.items.clear()
            // Делаем новый запрос фильмов на сервер
            viewModel.getFilms()
            // Убираем крутящиеся колечко
            binding.pullToRefresh.isRefreshing = false
        }
    }

    private fun initSearchView() {
        search_view.setOnClickListener {
            search_view.isIconified = false
        }

        // Подключаем слушателя изменений введенного текста в поиска
        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            // Этот метод отрабатывает при нажатии кнопки "поиск" на софт клавиатуре
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            // Этот метод отрабатывает на каждое изменения текста
            override fun onQueryTextChange(newText: String): Boolean {
                // Если ввод пуст то вставляем в адаптер всю БД
                if (newText.isEmpty()) {
                    filmsAdapter.addItems(filmsDataBase)
                    return true
                }

                // Фильтруем список на поискк подходящих сочетаний
                val result = filmsDataBase.filter {
                    //Чтобы все работало правильно, нужно и запроси и имя фильма приводить к нижнему регистру
                    it.title.lowercase(Locale.getDefault())
                        .contains(newText.lowercase(Locale.getDefault()))
                }

                // Добавляем в адаптер
                filmsAdapter.addItems(result)
                return true
            }
        })
    }

    private fun initRecyckler() {
        main_recycler.apply {
            filmsAdapter =
                FilmListRecyclerAdapter(object : FilmListRecyclerAdapter.OnItemClickListener {
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
    }

    companion object {
        private const val DECORATOR_PADDING = 8
    }

}
