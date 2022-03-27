package eu.berngardt.filmssearch

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    /**
     * Обработчик onCreate */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initNavigation()
    }

    /**
     * Метод для создания навигации  */
    private fun initNavigation() {
        // Ищем наш тулбар
        val topAppBar = findViewById<MaterialToolbar>(R.id.topAppBar)
        // "Вешаем" на него ClickListener
        topAppBar.setOnMenuItemClickListener {
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
        topAppBar.setNavigationOnClickListener {
            createAndShowToast(R.string.nav_menu_btn_title)
        }

        // Ищем нашу "панель навигации" ...
        val bottom_navigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        // ... и "вешаем" на него "слушателя"
        bottom_navigation.setOnNavigationItemSelectedListener {

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