package com.bayu07750.mademovie

import android.app.Application
import com.bayu07750.mademovie.BuildConfig.DEBUG
import com.bayu07750.mademovie.core.di.CoreModule
import com.bayu07750.mademovie.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidLogger(if (DEBUG) Level.DEBUG else Level.NONE)
            androidContext(this@App)
            modules(buildList {
                addAll(CoreModule)
                addAll(AppModule)
            })
        }
    }
}