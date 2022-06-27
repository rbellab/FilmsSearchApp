package eu.berngardt.filmssearch.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import eu.berngardt.filmssearch.R
import eu.berngardt.filmssearch.databinding.ActivityMainBinding
import eu.berngardt.filmssearch.domain.Film
import eu.berngardt.filmssearch.view.fragments.*


class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(R.style.Theme_FilmsSearch)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavigation()
        launchMainFragment()
    }

    private fun initNavigation() {
        initTopAppBarOnMenuItemClickListener()
        initMenuBtnOnMenuItemClickListener()
        initBottomNavigationBar()
    }

    private fun initMenuBtnOnMenuItemClickListener() {
        binding.topAppBar?.setNavigationOnClickListener {
            createAndShowToast(R.string.nav_menu_btn_title)
        }
    }

    private fun initTopAppBarOnMenuItemClickListener() {
        binding.topAppBar.let {
            it.setOnMenuItemClickListener {
                // Проверяем на что именно кликнул пользователь
                when (it.itemId) {
                    // Если это кнопка "Настройки", то реагируем
                    R.id.settings -> {
                        createAndShowToast(R.string.settings_menu_btn_title)
                        true
                    } else -> false
                }
            }
        }
    }

    private fun initBottomNavigationBar() {
        binding.bottomNavigation.let {
            it.setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    // Кнопка "Домой"
                    R.id.home -> {
                        val tag = "home"
                        val fragment = checkFragmentExistence(tag)
                        // В первом параметре, если фрагмент не найден и метод вернул null, то с помощью
                        // элвиса мы вызываем создание нового фрагмента
                        changeFragment( fragment?: HomeFragment(), tag)
                        true
                    }

                    // Кнопка "Избранное"
                    R.id.favorites -> {
                        val tag = "favorites"
                        val fragment = checkFragmentExistence(tag)
                        changeFragment( fragment?: FavoritesFragment(), tag)
                        true
                    }

                    // Кнопка "Посмотреть позже"
                    R.id.watch_later -> {
                        val tag = "watch_later"
                        val fragment = checkFragmentExistence(tag)
                        changeFragment( fragment?: WatchLaterFragment(), tag)
                        true
                    }

                    // Кнопка "Подборки"
                    R.id.collections -> {
                        val tag = "selections"
                        val fragment = checkFragmentExistence(tag)
                        changeFragment( fragment?: CollectionsFragment(), tag)
                        true
                    } else -> false
                }
            }
        }
    }

    private fun checkAndChangeFragment(tag: String) {
        val fragment = checkFragmentExistence(tag)
        // В первом параметре, если фрагмент не найден и метод вернул null,
        // то с помощью элвиса мы вызываем создание нового фрагмента
        changeFragment( fragment?: HomeFragment(), tag)
    }

    // Ищем фрагмент по тегу, если он есть то возвращаем его, если нет, то null
    private fun checkFragmentExistence(tag: String): Fragment? = supportFragmentManager.findFragmentByTag(tag)

    private fun changeFragment(fragment: Fragment, tag: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_placeholder, fragment, tag)
            .addToBackStack(null)
            .commit()
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    /**
     * Метод для создания и отображения "тостов" */
    private fun createAndShowToast(toastText: Int) {
        Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show()
    }

}
