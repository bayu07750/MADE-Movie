package com.bayu07750.mademovie.core.data.local.room.callback

import android.content.Context
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.bayu07750.mademovie.core.data.local.room.work.AddGenresWorker

class GenresCallback(
    private val context: Context
) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        AddGenresWorker.setupWork(context)
    }
}