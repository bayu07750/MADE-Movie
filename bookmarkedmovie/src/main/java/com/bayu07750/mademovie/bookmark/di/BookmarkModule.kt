package com.bayu07750.mademovie.bookmark.di

import com.bayu07750.mademovie.bookmark.presentation.BookmarkViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val BookmarkModule = module {
    viewModelOf(::BookmarkViewModel)
}