package com.bayu07750.mademovie.core.di

import com.bayu07750.mademovie.core.data.repository.MovieRepositoryImp
import com.bayu07750.mademovie.core.domain.repository.MovieRepository
import org.koin.dsl.module

val RepositoryModule = module {
    single<MovieRepository> { MovieRepositoryImp(get(), get()) }
}