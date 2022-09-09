package eu.berngardt.filmssearch.view.fragments

import java.util.*
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.core.view.isVisible
import java.util.concurrent.TimeUnit
import androidx.fragment.app.Fragment
import io.reactivex.rxjava3.core.Observable
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import eu.berngardt.filmssearch.utils.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import eu.berngardt.filmssearch.data.entity.Film
import eu.berngardt.filmssearch.view.MainActivity
import io.reactivex.rxjava3.schedulers.Schedulers
import eu.berngardt.filmssearch.utils.AutoDisposable
import eu.berngardt.filmssearch.utils.AnimationHelper
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import androidx.recyclerview.widget.LinearLayoutManager
import eu.berngardt.filmssearch.databinding.FragmentHomeBinding
import eu.berngardt.filmssearch.viewmodel.HomeFragmentViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import eu.berngardt.filmssearch.view.rv_adapters.FilmListRecyclerAdapter
import eu.berngardt.filmssearch.view.rv_adapters.TopSpacingItemDecoration


class HomeFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(HomeFragmentViewModel::class.java)
    }
    private val autoDisposable = AutoDisposable()

    private lateinit var filmsAdapter: FilmListRecyclerAdapter
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        autoDisposable.bindTo(lifecycle)
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

        AnimationHelper.performFragmentCircularRevealAnimation(binding.homeFragmentRoot, requireActivity(), ANIMATION_POSITION)

        initSearchView()
        initPullToRefresh()
        // Находим наш ресайклервью
        initRecyckler()
        // Кладём БД в ресайклервью
	
        viewModel.filmsListData
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { list -> filmsAdapter.addItems(list) }
            .addTo(autoDisposable)

        viewModel.showProgressBar
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { binding.progressBar.isVisible = it }
            .addTo(autoDisposable)
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
        binding.searchView.setOnClickListener {
            binding.searchView.isIconified = false
        }

        Observable.create(ObservableOnSubscribe<String> { subscriber ->
            // Вешаем слушатель на клавиатуру
            binding.searchView.setOnQueryTextListener(object :
                // Вызывается на ввод символов
                SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String): Boolean {
                    filmsAdapter.items.clear()
                    subscriber.onNext(newText)
                    return false
                }
                // Вызывается по нажатию кнопки "Поиск"
                override fun onQueryTextSubmit(query: String): Boolean {
                    subscriber.onNext(query)
                    return false
                }
            })
        })
            .subscribeOn(Schedulers.io())
            .map {
                it.toLowerCase(Locale.getDefault()).trim()
            }
            .debounce(DEBOUNCE_VALUE, TimeUnit.MILLISECONDS)
            .filter {
                // Если в поиске пустое поле, возвращаем список фильмов по умолчанию
                viewModel.getFilms()
                it.isNotBlank()
            }
            .flatMap {
                viewModel.getSearchResult(it)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onError = {
                    Toast.makeText(requireContext(), ERROR_TEXT_MESSAGE, Toast.LENGTH_SHORT).show()
                },
                onNext = {
                    filmsAdapter.addItems(it)
                }
            )
            .addTo(autoDisposable)
    }

    private fun initRecyckler() {
        binding.mainRecycler.apply {
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
        private const val ANIMATION_POSITION = 1
        private const val DEBOUNCE_VALUE = 800L
        private const val ERROR_TEXT_MESSAGE = "Что-то пошло не так"
    }

}
