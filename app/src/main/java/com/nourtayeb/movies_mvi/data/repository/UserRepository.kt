package com.nourtayeb.movies_mvi.data.repository

import com.nourtayeb.movies_mvi.domain.entity.User

interface UserRepository {
    suspend fun getLoggedInUser(): User
    suspend fun addLoggedInUser(user:User):Boolean
}