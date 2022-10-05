package com.orels.stock_price.di

import com.orels.data.remote.RepositoryImpl
import com.orels.data.remote.StocksAPI
import com.orels.data.remote.interceptor.AuthInterceptor
import com.orels.domain.model.interactors.Repository
import com.orels.stock_price.di.annotation.BaseUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * @author Orel Zilberman
 * 05/10/2022
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideOkHttpClient(
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .retryOnConnectionFailure(true)
            .connectTimeout(30L, TimeUnit.SECONDS)
            .readTimeout(30L, TimeUnit.SECONDS)
            .build()

    @Provides
    @Singleton
    fun provideApi(
        @BaseUrl baseUrl: String,
        client: OkHttpClient
    ): StocksAPI {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(StocksAPI::class.java)
    }

    @Provides
    @BaseUrl
    fun providesBaseUrl(): String = "https://yh-finance.p.rapidapi.com"


    @Provides
    @Singleton
    fun provideCoinRepository(api: StocksAPI): Repository {
        return RepositoryImpl(api)
    }

}