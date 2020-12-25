package com.nourtayeb.ads.data

sealed class UseCaseResult<out T> {
    data class Success<out T>(val data: T? = null): UseCaseResult<T>()
    data class Failed(val status: String? = null): UseCaseResult<Nothing>()
}