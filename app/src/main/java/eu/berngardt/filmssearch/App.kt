package eu.berngardt.filmssearch

import android.app.Application
import eu.berngardt.filmssearch.di.AppComponent
import eu.berngardt.filmssearch.di.DaggerAppComponent

class App : Application() {
    lateinit var dagger: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        // Создаем компонент
        dagger = DaggerAppComponent.create()
    }


    companion object {
        lateinit var instance: App
            private set
    }
}