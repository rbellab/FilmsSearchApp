package eu.berngardt.filmssearch

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import eu.berngardt.filmssearch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
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

    /**
     * Обработчик onCreate */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavigation()
        initRecyclerView()
    }

    /**
     * Метод для создания навигации  */
    private fun initNavigation() {
        // "Вешаем" на тулбар ClickListener
        binding.topAppBar.setOnMenuItemClickListener {
            // Проверяем на что именно кликнул пользователь
            when (it.itemId) {
                // Если это кнопка "Настройки", то реагируем
                R.id.settings -> {
                    createAndShowToast(R.string.settings_menu_btn_title)
                    true
                }
                else -> false
            }
        }

        // "Вешаем" ClickListener на кнопку "Меню"
        binding.topAppBar.setNavigationOnClickListener {
            createAndShowToast(R.string.nav_menu_btn_title)
        }

        // "Ввешаем" на панель навигации "слушателя"
        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            // Проверяем на что именно кликнул пользователь
            when (it.itemId) {
                // Кнопка "Избранное"
                R.id.favorites -> {
                    createAndShowToast(R.string.fav_nav_btn_title)
                    true
                }

                // Кнопка "Посмотреть позже"
                R.id.watch_later -> {
                    createAndShowToast(R.string.watch_later_nav_btn_title)
                    true
                }

                // Кнопка "Подборки"
                R.id.collections -> {
                    createAndShowToast(R.string.collections_nav_btn_title)
                    true
                }
                else -> false
            }
        }
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
            layoutManager = LinearLayoutManager(this@MainActivity)

            //Применяем декоратор для отступов
            val decorator = TopSpacingItemDecoration(8)
            addItemDecoration(decorator)
        }

        // Кладем нашу БД в RV
        filmsAdapter.addItems(filmsDataBase)
    }

    /**
     * Метод для создания и отображения "тостов" */
    private fun createAndShowToast(toastText: Int) {
        Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show()
    }
}