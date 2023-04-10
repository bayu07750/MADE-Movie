package com.bayu07750.mademovie.core.data.network.di

import com.bayu07750.mademovie.core.data.network.service.ApiConfig
import com.bayu07750.mademovie.core.data.network.service.TheMovieDBClient
import org.koin.dsl.module

val NetworkModule = module {
    single { ApiConfig.provideOkhttpClient() }
    single { ApiConfig.provideRetrofit(get()) }
    single { ApiConfig.provideTheMovieDBService(get()) }
    single { TheMovieDBClient(get(), get()) }
}