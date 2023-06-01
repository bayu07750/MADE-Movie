package com.bayu07750.mademovie.core.initializer

import android.content.Context
import androidx.startup.Initializer
import com.bayu07750.mademovie.core.BuildConfig.DEBUG
import timber.log.Timber

class TimberInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        if (DEBUG) {
            Timber.plant(Timber.DebugTree())
            Timber.d("Timber has been initialized")
        }
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}