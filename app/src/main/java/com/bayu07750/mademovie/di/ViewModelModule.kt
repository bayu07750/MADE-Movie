package com.bayu07750.mademovie.di

import com.bayu07750.mademovie.presentation.category.CategoryViewModel
import com.bayu07750.mademovie.presentation.detail.DetailViewModel
import com.bayu07750.mademovie.presentation.discovery.DiscoveryViewModel
import com.bayu07750.mademovie.presentation.home.HomeViewModel
import com.bayu07750.mademovie.presentation.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val ViewModelModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::DetailViewModel)
    viewModelOf(::CategoryViewModel)
    viewModelOf(::DiscoveryViewModel)
    viewModelOf(::SearchViewModel)
}