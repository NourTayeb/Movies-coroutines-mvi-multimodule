package com.nourtayeb.movies_mvi.common.di

import com.nourtayeb.movies_mvi.data.local.db.MoviesDao
import com.nourtayeb.movies_mvi.data.local.db.UserDao
import com.nourtayeb.movies_mvi.data.mapper.MovieMapper
import com.nourtayeb.movies_mvi.data.mapper.UserMapper
import com.nourtayeb.movies_mvi.data.network.ApiService
import com.nourtayeb.movies_mvi.data.repository.MoviesRepositoryImp
import com.nourtayeb.movies_mvi.data.repository.UserRepositoryImp
import com.nourtayeb.movies_mvi.domain.repository.MoviesRepository
import com.nourtayeb.movies_mvi.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
object DataRepositoryModule {

    @Provides
    fun provideDataRepository(apiService: ApiService,mapper: MovieMapper,dao: MoviesDao,userDao :UserDao): MoviesRepository {
        return MoviesRepositoryImp(apiService,mapper,dao,userDao)
    }
    @Provides
    fun provideUserRepository(userDao: UserDao,userMapper: UserMapper): UserRepository {
        return UserRepositoryImp(userDao,userMapper)
    }
}