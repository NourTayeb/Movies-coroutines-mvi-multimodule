package com.nourtayeb.movies_mvi.domain.usecase


import com.nourtayeb.movies_mvi.data.network.UseCaseResult
import com.nourtayeb.movies_mvi.domain.entity.Movie
import com.nourtayeb.movies_mvi.domain.repository.MoviesRepository
import com.nourtayeb.movies_mvi.domain.repository.UserRepository

import javax.inject.Inject

class GetFavoriteUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val userRepository: UserRepository
) {
    suspend fun buildUseCase(): UseCaseResult<List<Movie>> {
        val repositoriesModel = moviesRepository.getFavorite(userRepository.getLoggedInUser().id)
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