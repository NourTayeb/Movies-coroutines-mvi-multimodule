package com.nourtayeb.movies_mvi.common.di

import com.nourtayeb.movies_mvi.data.repository.MoviesRepositoryImp
import com.nourtayeb.movies_mvi.data.repository.UserRepositoryImp
import com.nourtayeb.movies_mvi.domain.usecase.AddToFavoriteUseCase
import com.nourtayeb.movies_mvi.domain.usecase.GetFavoriteUseCase
import com.nourtayeb.movies_mvi.domain.usecase.LoginUseCase
import com.nourtayeb.movies_mvi.domain.usecase.SearchMovieUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object UseCaseModule {

    @Provides
    fun providesSearchMovieUseCase(moviesRepository: MoviesRepositoryImp,userRepository:UserRepositoryImp): SearchMovieUseCase {
        return SearchMovieUseCase(moviesRepository,userRepository)
    }
    @Provides
    fun providesAddToFavoriteUseCase(moviesRepository: MoviesRepositoryImp,userRepository:UserRepositoryImp): AddToFavoriteUseCase {
        return AddToFavoriteUseCase(moviesRepository,userRepository)
    }
    @Provides
    fun providesGetFavoriteUseCase(moviesRepository: MoviesRepositoryImp,userRepository:UserRepositoryImp): GetFavoriteUseCase {
        return GetFavoriteUseCase(moviesRepository,userRepository)
    }

    @Provides
    fun providesLoginUseCase(userRepository:UserRepositoryImp): LoginUseCase {
        return LoginUseCase(userRepository)
    }


}