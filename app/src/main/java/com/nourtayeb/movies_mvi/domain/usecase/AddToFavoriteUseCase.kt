package com.nourtayeb.movies_mvi.domain.usecase
import com.nourtayeb.movies_mvi.domain.UseCaseResult
import com.nourtayeb.movies_mvi.data.repository.MoviesRepository
import com.nourtayeb.movies_mvi.data.repository.UserRepository

import javax.inject.Inject
class AddToFavoriteUseCase @Inject constructor(
    private val dataRepository: MoviesRepository, private val userRepository: UserRepository
) {
    suspend fun buildUseCase(isFav:Boolean, id:Int): UseCaseResult<Boolean> {
        val result = dataRepository.toggleFavorite(isFav,id,userRepository.getLoggedInUser().id)
        return when(result) {
            true -> {
                UseCaseResult.Success(result)
            }
            else -> {
                UseCaseResult.Failed()
            }
        }
    }
}