package com.example.movieku.di

import android.content.Context
import com.example.movieku.data.MoviekuRepository
import com.example.movieku.data.source.local.LocalDataSource
import com.example.movieku.data.source.local.room.MoviekuDatabase
import com.example.movieku.data.source.remote.RemoteDataSource
import com.example.movieku.utils.AppExecutors

object Injection {

    fun provideRepository(context: Context): MoviekuRepository {

        val moviekuDatabase = MoviekuDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = LocalDataSource.getInstance(moviekuDatabase.moviekuDao())
        val appExecutors = AppExecutors()

        return MoviekuRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}