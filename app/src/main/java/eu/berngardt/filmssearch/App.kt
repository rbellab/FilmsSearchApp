package eu.berngardt.filmssearch

import android.app.Application
import eu.berngardt.filmssearch.di.AppComponent
import eu.berngardt.filmssearch.di.DaggerAppComponent
import eu.berngardt.filmssearch.di.modules.DomainModule
import eu.berngardt.filmssearch.di.modules.RemoteModule
import eu.berngardt.filmssearch.di.modules.DatabaseModule

class App : Application() {
    lateinit var dagger: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        // Создаем компонент
        dagger = DaggerAppComponent.builder()
            .remoteModule(RemoteModule())
            .databaseModule(DatabaseModule())
            .domainModule(DomainModule(this))
            .build()
    }

    companion object {
        lateinit var instance: App
            private set
    }
}