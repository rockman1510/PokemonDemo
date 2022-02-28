package com.pokemondemo.di.module

import android.content.Context
import com.pokemondemo.BuildConfig
import com.pokemondemo.api.ApiService
import com.pokemondemo.constant.Constants
import com.pokemondemo.utils.ConnectionUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideBaseUrl(): String = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        val client = OkHttpClient.Builder().readTimeout(Constants.READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(Constants.WRITE_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(Constants.CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .cache(Cache(context.cacheDir, Constants.CACHE_SIZE))
            .addInterceptor(Interceptor { chain: Interceptor.Chain ->
                var request = chain.request()
                request = if (ConnectionUtils.checkNetworkEnabled(context)) {
                    request.newBuilder()
                        .header(Constants.CACHE_CONTROL, Constants.TIME_CACHE_ONLINE).build()
                } else {
                    request.newBuilder()
                        .header(Constants.CACHE_CONTROL, Constants.TIME_CACHE_OFFLINE).build()
                }
                chain.proceed(request.newBuilder().build())
            })
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            client.addInterceptor(logging)
        }
        return client.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit {
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl).client(okHttpClient).build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
}