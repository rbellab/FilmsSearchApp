package eu.berngardt.filmssearch.di.modules

import eu.berngardt.filmssearch.BuildConfig
import eu.berngardt.filmssearch.data.ApiConstants
import eu.berngardt.filmssearch.data.TmdbApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class RemoteModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        // Настраиваем таймауты для медленного интернета
        .callTimeout(INTERNET_CALL_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(INTERNET_CALL_TIMEOUT, TimeUnit.SECONDS)
        // Добавляем логгер
        .addInterceptor(HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) {
                level = HttpLoggingInterceptor.Level.BASIC
            }
        })
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        // Указываем базовый URL из констант
        .baseUrl(ApiConstants.BASE_MOVIE_DATA_URL)
        // Добавляем конвертер
        .addConverterFactory(GsonConverterFactory.create())
        // Добавляем кастомный клиент
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideTmdbApi(retrofit: Retrofit): TmdbApi = retrofit.create(TmdbApi::class.java)

    companion object {
        // Значение таймаута для медленного интрнета
        private const val INTERNET_CALL_TIMEOUT = 30L
    }
}