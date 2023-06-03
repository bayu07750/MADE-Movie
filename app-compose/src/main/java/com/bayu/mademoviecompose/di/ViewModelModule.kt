package com.bayu.mademoviecompose.di

import com.bayu.mademoviecompose.presentation.bookmark.BookmarkViewModel
import com.bayu.mademoviecompose.presentation.category.CategoryViewModel
import com.bayu.mademoviecompose.presentation.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val ViewModelModule = module {
    viewModelOf(::CategoryViewModel)
    viewModelOf(::BookmarkViewModel)
    viewModelOf(::HomeViewModel)
}