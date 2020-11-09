package com.nourtayeb.movies_mvi.data.network

//sealed class UseCaseResult {
//    data class Success<out T>(val data: T? = null): UseCaseResult()
//    data class Failed(val status: String? = null): UseCaseResult()
//}
sealed class UseCaseResult<out T> {
    data class Success<out T>(val data: T? = null): UseCaseResult<T>()
    data class Failed(val status: String? = null): UseCaseResult<Nothing>()
}