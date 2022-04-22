package eu.berngardt.filmssearch

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import eu.berngardt.filmssearch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNavigation()
        launchMainFragment()
    }

    private fun launchMainFragment() {
        // Зупускаем фрагмент при старте
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_placeholder, HomeFragment())
            .addToBackStack(null)
            .commit()
    }

    fun launchDetailsFragment(film: Film) {
        // Создаем "посылку"
        val bundle = Bundle()
        // Кладем наш фильм в "посылку"
        bundle.putParcelable("film", film)
        // Кладем фрагмент с деталями в перменную
        val fragment = DetailsFragment()
        // Прикрепляем нашу "посылку" к фрагменту
        fragment.arguments = bundle

        // Запускаем фрагмент
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_placeholder, fragment)
            .addToBackStack(null)
            .commit()
    }

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

    /**
     * Метод для создания и отображения "тостов" */
    private fun createAndShowToast(toastText: Int) {
        Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show()
    }
}
