package eu.berngardt.filmssearch

import android.app.Application
import eu.berngardt.filmssearch.di.AppComponent
import eu.berngardt.filmssearch.di.modules.DatabaseModule
import eu.berngardt.filmssearch.di.modules.DomainModule
import eu.berngardt.remote_module.DaggerRemoteComponent

class App : Application() {
    lateinit var dagger: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this

        // Создаем компонент
        val remoteProvider = DaggerRemoteComponent.create()
        dagger = AppComponent.builder()
            .remoteProvider(remoteProvider)
            .databaseModule(DatabaseModule())
            .domainModule(DomainModule(this))
            .build()
    }

    companion object {
        lateinit var instance: App
            private set
    }
}
