package eu.berngardt.filmssearch.view

import android.os.Bundle
import eu.berngardt.filmssearch.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import eu.berngardt.filmssearch.view.fragments.*
import eu.berngardt.filmssearch.data.entity.Film
import eu.berngardt.filmssearch.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Инициализируем объект
        binding = ActivityMainBinding.inflate(layoutInflater)

        // Передаем его в метод
        setContentView(binding.root)

        initNavigation()

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
        bundle.putParcelable(PARCELABLE_KEY, film)

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
        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {

                R.id.home -> {
                    val fragment = checkFragmentExistence(HOME_TAG)
                    // В первом параметре, если фрагмент не найден и метод вернул null, то с помощью
                    // элвиса мы вызываем создание нвого фрагмента
                    changeFragment( fragment?: HomeFragment(), HOME_TAG)
                    true
                }

                R.id.favorites -> {
                    val fragment = checkFragmentExistence(FAVORITES_TAG)
                    changeFragment( fragment?: FavoritesFragment(), FAVORITES_TAG)
                    true
                }

                R.id.watch_later -> {
                    val fragment = checkFragmentExistence(WATCH_LATER_TAG)
                    changeFragment( fragment?: WatchLaterFragment(), WATCH_LATER_TAG)
                    true
                }

                R.id.selections -> {
                    val fragment = checkFragmentExistence(SELECTION_TAG)
                    changeFragment( fragment?: SelectionsFragment(), SELECTION_TAG)
                    true
                }

                R.id.settings -> {
                    val fragment = checkFragmentExistence(SETTINGS_TAG)
                    changeFragment( fragment?: SettingsFragment(), SETTINGS_TAG)
                    true
                }

                else -> false
            }
        }
    }

    // Ищем фрагмент по тэгу, если он есть то возвращаем его, если нет - то null
    private fun checkFragmentExistence(tag: String): Fragment? = supportFragmentManager.findFragmentByTag(tag)

    private fun changeFragment(fragment: Fragment, tag: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_placeholder, fragment, tag)
            .addToBackStack(null)
            .commit()
    }

    companion object {
        private const val PARCELABLE_KEY = "film"
        private const val HOME_TAG = "home"
        private const val FAVORITES_TAG = "favorites"
        private const val WATCH_LATER_TAG = "watch_later"
        private const val SELECTION_TAG = "selections"
        private const val SETTINGS_TAG = "settings"
    }
}
