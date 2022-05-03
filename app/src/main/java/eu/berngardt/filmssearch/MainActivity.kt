package eu.berngardt.filmssearch

import android.os.Bundle
import android.widget.Toast
import eu.berngardt.filmssearch.storage.Film
import androidx.appcompat.app.AppCompatActivity
import eu.berngardt.filmssearch.ui.fragments.HomeFragment
import eu.berngardt.filmssearch.ui.fragments.DetailsFragment
import eu.berngardt.filmssearch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding!!.root)

        initNavigation()
        launchMainFragment()
    }

    private fun initNavigation() {
        initTopAppBarOnMenuItemClickListener()
        initMenuBtnOnMenuItemClickListener()
        initBottomNavigationBar()
    }

    private fun initMenuBtnOnMenuItemClickListener() {
        _binding?.topAppBar?.setNavigationOnClickListener {
            createAndShowToast(R.string.nav_menu_btn_title)
        }
    }

    private fun initTopAppBarOnMenuItemClickListener() {
        _binding.let {
            it!!.topAppBar.let {
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
    }

    private fun initBottomNavigationBar() {
        _binding.let {
            it!!.bottomNavigation.let {
                it.setOnNavigationItemSelectedListener {
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
                        } else -> false
                    }
                }
            }
        }
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
