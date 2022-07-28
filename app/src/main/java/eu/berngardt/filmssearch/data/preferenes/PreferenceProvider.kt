package eu.berngardt.filmssearch.data.preferenes

import android.content.Context
import androidx.core.content.edit
import android.content.SharedPreferences

class PreferenceProvider(context: Context) {

    // Нам нужен контекст приложения
    private val appContext = context.applicationContext

    // Создаем экземпляр SharedPreferences
    private val preference: SharedPreferences = appContext.getSharedPreferences(SETTINGS_NAME, Context.MODE_PRIVATE)

    init {
        // Логика для первого запуска приложения, чтобы положить наши настройки.
        // Сюда потом можно добавить и другие настройки
        if (preference.getBoolean(KEY_FIRST_LAUNCH, false)) {
            preference.edit { putString(KEY_DEFAULT_CATEGORY, DEFAULT_CATEGORY) }
            preference.edit { putBoolean(KEY_FIRST_LAUNCH, false) }
        }
    }

    // Category prefs
    // Сохраняем категорию
    fun saveDefaultCategory(category: String) {
        preference.edit { putString(KEY_DEFAULT_CATEGORY, category) }
    }

    // Забираем категорию
    fun geDefaultCategory(): String {
        return preference.getString(KEY_DEFAULT_CATEGORY, DEFAULT_CATEGORY) ?: DEFAULT_CATEGORY
    }

    // Ключи для настроек, по ним мы их будем получать
    companion object {
        private const val KEY_FIRST_LAUNCH = "first_launch"
        private const val KEY_DEFAULT_CATEGORY = "default_category"
        private const val DEFAULT_CATEGORY = "popular"
        private const val SETTINGS_NAME = "settings"
    }
}