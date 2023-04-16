package com.bayu07750.mademovie.core.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bayu07750.mademovie.core.data.local.BuildConfig
import com.bayu07750.mademovie.core.data.local.room.callback.GenresCallback
import com.bayu07750.mademovie.core.data.local.room.dao.MovieDao
import com.bayu07750.mademovie.core.data.local.room.entity.GenreEntity
import com.bayu07750.mademovie.core.data.local.room.entity.MovieEntity
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

@Database(
    entities = [MovieEntity::class, GenreEntity::class],
    version = 1,
    exportSchema = true,
)
abstract class AppDatabase : RoomDatabase() {

    abstract val movieDao: MovieDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: createDb(context).also {
                    instance = it
                }
            }
        }

        private fun createDb(context: Context): AppDatabase {
            val dbName = BuildConfig.LIBRARY_PACKAGE_NAME + ".AppDatabase"
            val passpharse = SQLiteDatabase.getBytes(dbName.toCharArray())
            val supportFactory = SupportFactory(passpharse)
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                dbName,
            )
                .addCallback(GenresCallback(context))
                .fallbackToDestructiveMigration()
                .openHelperFactory(supportFactory)
                .build()
        }
    }
}