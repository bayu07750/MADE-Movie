package com.bayu.mademoviecompose

import android.app.Application
import com.bayu.mademoviecompose.BuildConfig.DEBUG
import com.bayu.mademoviecompose.di.AppModule
import com.bayu07750.mademovie.core.di.CoreModule
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