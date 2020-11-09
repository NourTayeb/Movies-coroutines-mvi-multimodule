package com.nourtayeb.movies_mvi.domain.usecase

import com.nourtayeb.movies_mvi.domain.repository.MoviesRepository
import io.mockk.impl.annotations.MockK

class GetFavoriteUseCaseTest {

    @MockK
    lateinit var repository: MoviesRepository


    lateinit var useCase:GetFavoriteUseCase
//    @Before
//    fun init(){
//        MockKAnnotations.init(this)
//        useCase = GetFavoriteUseCase(repository)
//    }
//
//    @Test
//    fun `empty getFavorite result returns failed result`(){
//        coEvery { repository.getFavorite() } returns listOf()
//        runBlockingTest {
//            val result = useCase.buildUseCase()
//            assert(result is UseCaseResult.Failed)
//        }
//    }
//    @Test
//    fun `non-empty getFavorite result returns success result`(){
//        coEvery { repository.getFavorite() } returns dummyMovies
//        runBlockingTest {
//            val result = useCase.buildUseCase()
//            assert(result is UseCaseResult.Success)
//        }
//    }
}