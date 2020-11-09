package com.nourtayeb.movies_mvi.domain.usecase


import com.nourtayeb.movies_mvi.data.network.UseCaseResult
import com.nourtayeb.movies_mvi.domain.entity.User
import com.nourtayeb.movies_mvi.domain.repository.UserRepository

import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun buildUseCase(user:User): UseCaseResult<Boolean> {
        val result = userRepository.addLoggedInUser(user)
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