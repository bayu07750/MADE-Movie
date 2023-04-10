package com.bayu07750.mademovie.core.di

import com.bayu07750.mademovie.core.data.local.di.LocalModule
import com.bayu07750.mademovie.core.data.local.mmkv.MMKVModule
import com.bayu07750.mademovie.core.data.network.di.NetworkModule

val CoreModule = listOf(
    LocalModule,
    MMKVModule,
    NetworkModule,
    RepositoryModule,
    UseCaseModule,
)