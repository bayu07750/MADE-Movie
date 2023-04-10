package com.bayu07750.mademovie.core.data.local.room.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.bayu07750.mademovie.core.data.local.room.entity.mapper.GenreEntityMapper
import com.bayu07750.mademovie.core.data.model.response.ListGenreResponse
import com.bayu07750.mademovie.core.data.local.room.AppDatabase
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddGenresWorker(
    context: Context,
    params: WorkerParameters,
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            val movieDao = AppDatabase.getInstance(applicationContext).movieDao
            val inputStream = applicationContext.assets.open("movie_genres.json")
            val json = inputStream.bufferedReader().use { it.readText() }
            val genres = Gson().fromJson(json, ListGenreResponse::class.java)
            genres ?: run {
                return@withContext Result.failure()
            }
            val entities = genres.genres.map { GenreEntityMapper.asEntity(it) }
            movieDao.insertGenres(entities)
            Result.success()
        }
    }

    companion object {
        fun setupWork(context: Context) {
            val workRequest = OneTimeWorkRequestBuilder<AddGenresWorker>()
                .build()
            WorkManager.getInstance(context).enqueue(workRequest)
        }
    }
}