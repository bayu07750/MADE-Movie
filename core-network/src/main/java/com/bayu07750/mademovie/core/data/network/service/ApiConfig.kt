package com.bayu07750.mademovie.core.data.network.service

import com.bayu07750.mademovie.core.data.network.BuildConfig
import com.bayu07750.mademovie.core.data.network.BuildConfig.DEBUG
import com.skydoves.sandwich.adapters.ApiResponseCallAdapterFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiConfig {

    fun provideOkhttpClient(): OkHttpClient {
        val hostname = "api.themoviedb.org"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/NPIMWkzcNG/MyZsVExrC6tdy5LTZzeeKg2UlnGG55UY=")
            .add(hostname, "sha256/DxH4tt40L+eduF6szpY6TONlxhZhBd+pJ9wbHlQ2fuw=")
            .add(hostname, "sha256/++MBgDH5WGvL9Bcn5Be30cRcL0f5O+NyoXuWtQdX1aI=")
            .build()
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = if (DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
                }
            )
            .readTimeout(90, TimeUnit.SECONDS)
            .writeTimeout(90, TimeUnit.SECONDS)
            .callTimeout(20, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }

    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .client(client)
            .build()
    }

    fun provideTheMovieDBService(retrofit: Retrofit): TheMovieDBService {
        return retrofit.create(TheMovieDBService::class.java)
    }
}