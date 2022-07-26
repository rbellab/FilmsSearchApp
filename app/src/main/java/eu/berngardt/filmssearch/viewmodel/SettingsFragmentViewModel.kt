package eu.berngardt.filmssearch.viewmodel

import javax.inject.Inject
import eu.berngardt.filmssearch.App
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import eu.berngardt.filmssearch.domain.Interactor


class SettingsFragmentViewModel : ViewModel() {

    // Инжектим интерактор
    @Inject
    lateinit var interactor: Interactor
    val categoryPropertyLifeData: MutableLiveData<String> = MutableLiveData()

    init {
        App.instance.dagger.inject(this)
        // Получаем категорию при иницализации, чтобы у нас сразу подтягивалась категория
        getCategoryProperty()
    }

    private fun getCategoryProperty() {
        // Кладем каттегорию в LiveData
        categoryPropertyLifeData.value = interactor.getDefaultCategoryFromPreferences()
    }

    fun putCategoryProperty(category: String) {
        // Сохраняем в настройки
        interactor.saveDefaultCategoryToPreferences(category)
        // И сразу забираем,чтобы сохранить состояние в модели
        getCategoryProperty()
    }
}