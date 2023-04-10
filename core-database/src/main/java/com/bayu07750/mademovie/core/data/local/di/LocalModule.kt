package com.bayu07750.mademovie.core.data.local.di

import com.bayu07750.mademovie.core.data.local.room.AppDatabase
import com.bayu07750.mademovie.core.data.local.room.MovieLocalDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val LocalModule = module {
    single { AppDatabase.getInstance(androidContext()) }
    single { get<AppDatabase>().movieDao }
    single { MovieLocalDataSource(get()) }
}