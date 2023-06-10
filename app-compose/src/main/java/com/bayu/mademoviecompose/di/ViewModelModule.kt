package com.bayu.mademoviecompose.di

import com.bayu.mademoviecompose.presentation.bookmark.BookmarkViewModel
import com.bayu.mademoviecompose.presentation.category.CategoryViewModel
import com.bayu.mademoviecompose.presentation.detail.DetailViewModel
import com.bayu.mademoviecompose.presentation.home.HomeViewModel
import com.bayu.mademoviecompose.presentation.search.SearchViewModel
import com.bayu.mademoviecompose.presentation.discovery.DiscoveryViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val ViewModelModule = module {
    viewModelOf(::CategoryViewModel)
    viewModelOf(::BookmarkViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::DetailViewModel)
    viewModelOf(::SearchViewModel)
    viewModelOf(::DiscoveryViewModel)
}