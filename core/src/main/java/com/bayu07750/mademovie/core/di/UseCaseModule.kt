package com.bayu07750.mademovie.core.di

import com.bayu07750.mademovie.core.domain.usecase.*
import org.koin.dsl.module

val UseCaseModule = module {
    factory { GetTrendingMoviesUseCase(get()) }
    factory { GetTrendingMoviesPagingUseCase(get()) }
    factory { GetPopularMoviesUseCase(get()) }
    factory { GetPopularMoviesPagingUseCase(get()) }
    factory { GetNowPlayingMoviesUseCase(get()) }
    factory { GetNowPlayingMoviesPagingUseCase(get()) }
    factory { GetMovieDetailUseCase(get()) }
    factory { GetMovieCastsUseCase(get()) }
    factory { GetMovieDetailAndCastUseCase(get(), get(), get()) }
    factory { AddBookmarkedMovieUseCase(get()) }
    factory { DeleteBookmarkedMovieUseCase(get()) }
    factory { GetAllBookmarkedMovieUseCase(get()) }
    factory { GetBookmarkedMovieByIdUseCase(get()) }
    factory { BookmarkMovieUseCase(get(), get()) }
    factory { GetAllMovieGenreUseCase(get()) }
    factory { GetMovieDiscoveryUseCase(get()) }
    factory { GetSearchMovieUseCase(get()) }
}