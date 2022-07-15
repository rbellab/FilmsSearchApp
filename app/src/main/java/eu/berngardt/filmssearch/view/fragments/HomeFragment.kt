package eu.berngardt.filmssearch.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import eu.berngardt.filmssearch.databinding.FragmentHomeBinding
import eu.berngardt.filmssearch.domain.Film
import eu.berngardt.filmssearch.utils.AnimationHelper
import eu.berngardt.filmssearch.view.MainActivity
import eu.berngardt.filmssearch.viewmodel.HomeFragmentViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import view.rv_adapters.FilmListRecyclerAdapter
import view.rv_decorations.TopSpacingItemDecoration
import java.util.*


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var page: Int = 1

    private lateinit var filmsAdapter: FilmListRecyclerAdapter

    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(HomeFragmentViewModel::class.java)
    }

    private var filmsDataBase = listOf<Film>()
        // Используем backing field
        set (value) {
            // Если придет такое же значение, то мы выходим из метода
            if (field == value) return
            // Если пришло другое значение, то кладем его в переменную
            field = value
            // Обновляем RV адаптер
            filmsAdapter.addItems(field)
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): ConstraintLayout? {
        _binding = FragmentHomeBinding.inflate(layoutInflater)

        binding.mainRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            // Попытка реализации пагинации. Судя по-всему, не очень удачная. :)
            // Работает, но жутко криво. :)

            // Насколько всего прокрутили
            private var totalScrolledDy : Int = 0

            override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(rv, dx, dy)

                // Максимально допустимое значение для текущей страницы
                var maxDy = rv.getChildAt(0).measuredHeight * (rv.adapter!!.itemCount - 3)

                // Увеличиваем значение прокрученных "пикселей"
                totalScrolledDy += dy

                // Если привысии "лимит", то...
                if (totalScrolledDy >= maxDy) {
                    // ... увеличиваем номер странички
                    page++;
                    // "скручиваем пробег"
                    totalScrolledDy = 0
                    // Получаем данные с нужной страничкой
                    viewModel.reloadData(page)
                    // Перемещаемся на начало новой странички
                    rv.scrollToPosition(0)
                }
            }
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AnimationHelper.performFragmentCircularRevealAnimation(home_fragment_root, requireActivity(), 1)

        makeSearchFieldClickable()
        addTextListenerToSearchFie()
        addQueryTextListenerToSearchFie()
        applyMainRecyclerAdapter()

        viewModel.filmsListLiveData.observe(viewLifecycleOwner, Observer<List<Film>> {
            filmsDataBase = it
        })
    }

    private fun makeSearchFieldClickable() {
        // Делаем всё посковое поле "кликабельным"
        binding.searchView.setOnClickListener {
            it.search_view.isIconified = false
        }
    }

    private fun addTextListenerToSearchFie() {
        // Подключаем слушателя изменений введенного текста в поиска
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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

    private fun addQueryTextListenerToSearchFie() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{

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

    private fun applyMainRecyclerAdapter() {
        binding.mainRecycler.apply {
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
        filmsDataBase?.let { filmsAdapter.addItems(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}

