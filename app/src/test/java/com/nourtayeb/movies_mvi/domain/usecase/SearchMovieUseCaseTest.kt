package com.nourtayeb.movies_mvi.domain.usecase

import com.nourtayeb.movies_mvi.domain.repository.MoviesRepository
import io.mockk.impl.annotations.MockK

class SearchMovieUseCaseTest {

    @MockK
    lateinit var repository: MoviesRepository


    lateinit var useCase:SearchMovieUseCase
//    @Before
//    fun init(){
//        MockKAnnotations.init(this)
//        useCase = SearchMovieUseCase(repository)
//    }
//
//    @Test
//    fun `empty searchMovies result returns failed result`(){
//        coEvery { repository.searchMovies(any(),any()) } returns listOf()
//        runBlockingTest {
//            val result = useCase.buildUseCase("",true)
//            assert(result is UseCaseResult.Failed)
//        }
//    }
//    @Test
//    fun `non-empty searchMovies result returns success result`(){
//        coEvery { repository.searchMovies(any(),any()) } returns dummyMovies
//        runBlockingTest {
//            val result = useCase.buildUseCase("",true)
//            assert(result is UseCaseResult.Success)
//        }
//    }
//
//    @Test
//    fun `build usecase paramters are the same sent to repository searchMovies `(){
//        val keySlot = slot<String>()
//        val fromRemoteSlot = slot<Boolean>()
//        val key ="mile"
//        val fromRemote = false
//        coEvery { repository.searchMovies(capture(keySlot),capture(fromRemoteSlot)) } returns dummyMovies
//        runBlockingTest {
//            useCase.buildUseCase(key,fromRemote)
//        }
//        Assert.assertEquals(key,keySlot.captured)
//        Assert.assertEquals(fromRemote,fromRemoteSlot.captured)
//    }
}