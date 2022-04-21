package eu.berngardt.filmssearch

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import eu.berngardt.filmssearch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    /**
     * Обработчик onCreate */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNavigation()
        runMainFragment()
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

    private fun runMainFragment() {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_placeholder, HomeFragment())
            .addToBackStack(null)
            .commit()
    }


    /**
     * Метод для создания и отображения "тостов" */
    private fun createAndShowToast(toastText: Int) {
        Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show()
    }
}