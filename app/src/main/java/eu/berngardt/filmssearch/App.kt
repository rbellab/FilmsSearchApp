package eu.berngardt.filmssearch

import android.app.Application
import eu.berngardt.filmssearch.data.ApiConstants
import eu.berngardt.filmssearch.data.TmdbApi
import eu.berngardt.filmssearch.domain.Interactor

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class App : Application() {

    lateinit var interactor: Interactor
    lateinit var retrofitService: TmdbApi

    override fun onCreate() {
        super.onCreate()
        // Инициализируем экземпляр App, через который будем получать доступ к остальным переменным
        instance = this

        // Создаем кастомный клиент
        val okHttpClient = OkHttpClient.Builder()
            // Настриваем таймауты для медленного интрнета
            .callTimeout(INTERNET_CALL_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(INTERNET_CALL_TIMEOUT, TimeUnit.SECONDS)
            // Добавляем логгер
            .addInterceptor(HttpLoggingInterceptor().apply {
                if (BuildConfig.DEBUG) {
                    level = HttpLoggingInterceptor.Level.BASIC
                }
            })
            .build()
        // Создаем ретрофит
        val retrofit = Retrofit.Builder()
            // Указываем базовый URL из констант
            .baseUrl(ApiConstants.BASE_URL)
            // Добавляем конвертер
            .addConverterFactory(GsonConverterFactory.create())
            // Добавляем кастомный клиент
            .client(okHttpClient)
            .build()
        // Создаем сам сервис с методами для запросов
        retrofitService = retrofit.create(TmdbApi::class.java)
        // Инициализируем интерактор
        interactor = Interactor(retrofitService)
    }

    companion object {
        // Значение таймаута для медленного интрнета
        const val INTERNET_CALL_TIMEOUT = 30L

        // Здесь статически хранится ссылка на экземпляр App
        lateinit var instance: App
            // Приватный сеттер, чтобы нельзя было в эту переменную присвоить что-либо другое
            private set
    }
}