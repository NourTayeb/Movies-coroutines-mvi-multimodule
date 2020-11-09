package com.nourtayeb.movies_mvi.domain.usecase


import com.nourtayeb.movies_mvi.data.network.UseCaseResult
import com.nourtayeb.movies_mvi.domain.entity.Movie
import com.nourtayeb.movies_mvi.domain.repository.MoviesRepository
import com.nourtayeb.movies_mvi.domain.repository.UserRepository

import javax.inject.Inject

class SearchMovieUseCase @Inject constructor(
    private val dataRepository: MoviesRepository,
    private val userRepository: UserRepository
) {
    suspend fun buildUseCase(key: String,fromRemote:Boolean): UseCaseResult<List<Movie>> {
        val repositoriesModel = dataRepository.searchMovies(key,fromRemote,userRepository.getLoggedInUser().id)
        return when(repositoriesModel.isNotEmpty()) {
            true -> {
                UseCaseResult.Success(repositoriesModel)
            }
            else -> {
                UseCaseResult.Failed()
            }
        }
    }

}