package com.bayu.mademoviecompose.di

import com.bayu.mademoviecompose.presentation.category.CategoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val ViewModelModule = module {
    viewModelOf(::CategoryViewModel)
}