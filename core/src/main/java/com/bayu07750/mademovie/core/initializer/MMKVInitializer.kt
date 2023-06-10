package com.bayu07750.mademovie.core.initializer

import android.content.Context
import androidx.startup.Initializer
import com.tencent.mmkv.MMKV

class MMKVInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        MMKV.initialize(context)
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}