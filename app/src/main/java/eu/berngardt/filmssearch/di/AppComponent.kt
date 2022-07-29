package eu.berngardt.filmssearch.di

import dagger.Component
import javax.inject.Singleton
import eu.berngardt.filmssearch.di.modules.DomainModule
import eu.berngardt.filmssearch.di.modules.RemoteModule
import eu.berngardt.filmssearch.di.modules.DatabaseModule
import eu.berngardt.filmssearch.viewmodel.HomeFragmentViewModel
import eu.berngardt.filmssearch.viewmodel.SettingsFragmentViewModel

@Singleton
@Component(

    // Внедряем все модули, нужные для этого компонента
    modules = [
        RemoteModule::class,
        DatabaseModule::class,
        DomainModule::class
    ]
)

interface AppComponent {
    // Метод для того, чтобы появилась возможность внедрять зависимости в HomeFragmentViewModel
    fun inject(homeFragmentViewModel: HomeFragmentViewModel)

    // Метод для того, чтобы появилась возможность внедрять зависимости в SettingsFragmentViewModel
    fun inject(settingsFragmentViewModel: SettingsFragmentViewModel)
}
