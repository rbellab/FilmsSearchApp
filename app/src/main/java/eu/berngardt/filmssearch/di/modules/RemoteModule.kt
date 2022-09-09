package eu.berngardt.filmssearch.di.modules

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import okhttp3.OkHttpClient
import javax.inject.Singleton
import java.util.concurrent.TimeUnit
import eu.berngardt.filmssearch.BuildConfig
import eu.berngardt.filmssearch.data.TmdbApi
import okhttp3.logging.HttpLoggingInterceptor
import eu.berngardt.filmssearch.data.ApiConstants
import retrofit2.converter.gson.GsonConverterFactory
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory


@Module
class RemoteModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        // Настраиваем таймауты для медленного интернета
        .callTimeout(INTERNET_ACCESS_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(INTERNET_ACCESS_TIMEOUT, TimeUnit.SECONDS)

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
        .baseUrl(ApiConstants.BASE_URL)
        // Добавляем конвертер
        .addConverterFactory(GsonConverterFactory.create())
        // Добавляем поддержку RxJava
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        //Добавляем кастомный клиент
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideTmdbApi(retrofit: Retrofit): TmdbApi = retrofit.create(TmdbApi::class.java)

    companion object {
        private const val INTERNET_ACCESS_TIMEOUT = 30L
    }
}
