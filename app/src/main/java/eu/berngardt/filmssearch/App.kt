package eu.berngardt.filmssearch

import android.app.Application
import eu.berngardt.filmssearch.domain.Interactor
import eu.berngardt.filmssearch.data.MainRepository


class App : Application() {
    lateinit var repo: MainRepository
    lateinit var interactor: Interactor

    override fun onCreate() {
        super.onCreate()
        // Инициализируем экземпляр App, через который будем получать доступ к остальным переменным
        instance = this

        // Инициализируем репозиторий
        repo = MainRepository()

        // Инициализируем интерактор
        interactor = Interactor(repo)
    }

    companion object {
        // Здесь статически хранится ссылка на экземпляр App
        lateinit var instance: App
            // Приватный сеттер, чтобы нельзя было в эту переменную присвоить что-либо другое
            private set
    }
}