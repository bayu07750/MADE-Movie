package com.bayu07750.mademovie.core.data.local.mmkv

import com.tencent.mmkv.MMKV
import org.koin.dsl.module

val MMKVModule = module {
    single {
        Session(
            MMKV.mmkvWithID(
                BuildConfig.LIBRARY_PACKAGE_NAME,
                MMKV.SINGLE_PROCESS_MODE,
                BuildConfig.API_KEY
            )
        )
    }
}