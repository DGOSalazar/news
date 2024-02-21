package com.raven.home.di

import com.raven.home.data.HomeRepository
import com.raven.home.data.remote.service.HomeService
import com.raven.home.domain.HomeDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class HomeModule {
    @Binds
    abstract fun bindHiringSource(repository: HomeRepository): HomeDataSource
}


