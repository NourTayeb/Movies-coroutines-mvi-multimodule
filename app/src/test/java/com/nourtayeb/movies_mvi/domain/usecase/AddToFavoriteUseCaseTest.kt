package com.nourtayeb.movies_mvi.domain.usecase

import com.nourtayeb.movies_mvi.common.dummyUser
import com.nourtayeb.movies_mvi.domain.UseCaseResult
import com.nourtayeb.movies_mvi.data.repository.MoviesRepository
import com.nourtayeb.movies_mvi.data.repository.UserRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Assert
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

class AddToFavoriteUseCaseTest {

    @RelaxedMockK
    lateinit var movieRepository: MoviesRepository

    @RelaxedMockK
    lateinit var userRepository: UserRepository


    lateinit var useCase: AddToFavoriteUseCase

    @Before
    fun init() {
        MockKAnnotations.init(this)
        useCase = AddToFavoriteUseCase(movieRepository, userRepository)
    }

    @Test
    fun `true movieRepo toggleFavorite returns success result`() {
        val isFav = true
        val id = 0
        coEvery { userRepository.getLoggedInUser() } returns dummyUser
        coEvery { movieRepository.toggleFavorite(isFav,id,dummyUser.id) } returns true
        runBlockingTest {
            val result = useCase.buildUseCase(isFav, id)
            Assert.assertTrue(result is UseCaseResult.Success)
        }

    }
    @Test
    fun `false movieRepo toggleFavorite returns Failed result`() {
        val isFav = true
        val id = 0
        coEvery { userRepository.getLoggedInUser() } returns dummyUser
        coEvery { movieRepository.toggleFavorite(isFav,id,dummyUser.id) } returns false
        runBlockingTest {
            val result = useCase.buildUseCase(isFav, id)
            Assert.assertTrue(result is UseCaseResult.Failed)
        }

    }
}