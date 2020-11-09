package com.nourtayeb.movies_mvi.presentation.ui.searchmovies

import androidx.lifecycle.Observer
import com.nourtayeb.movies_mvi.common.base.ViewModelBaseTest
import com.nourtayeb.movies_mvi.common.dummyMovies
import com.nourtayeb.movies_mvi.data.network.UseCaseResult
import com.nourtayeb.movies_mvi.domain.usecase.AddToFavoriteUseCase
import com.nourtayeb.movies_mvi.domain.usecase.SearchMovieUseCase
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class SearchMovieViewModelTest : ViewModelBaseTest() {


    lateinit var viewModel: SearchMovieViewModel

    @RelaxedMockK
    lateinit var searchMovieUseCase: SearchMovieUseCase

    @RelaxedMockK
    lateinit var addToFavoriteUseCase: AddToFavoriteUseCase


    @Before
    fun init() {
        viewModel = SearchMovieViewModel(testDispatcher, searchMovieUseCase, addToFavoriteUseCase)
    }

    @Test
    fun `success search returns Loading then Searched`() {
        val key = "mile"
        val fromRemote = false
        coEvery { searchMovieUseCase.buildUseCase(key, fromRemote) } returns UseCaseResult.Success(
            dummyMovies
        )
        val liveData = viewModel.performAction(SearchMoviesUiAction.Search(key, fromRemote))
        val list = mutableListOf<SearchMoviesUiState>()
        liveData.observe(lifecycleOwner, Observer {
            list.add(it)
            if (list.size == 2) {
                assertEquals(
                    list,
                    listOf(
                        SearchMoviesUiState.Loading,
                        SearchMoviesUiState.Searched(dummyMovies)
                    )
                )
            }
        })
        Assert.assertEquals(list.size,2)
    }

    @Test
    fun `success search doesnt return Failed`() {
        val key = "mile"
        val fromRemote = false
        coEvery { searchMovieUseCase.buildUseCase(key, fromRemote) } returns UseCaseResult.Success(
            dummyMovies
        )
        val liveData = viewModel.performAction(SearchMoviesUiAction.Search(key, fromRemote))
        liveData.observe(lifecycleOwner, Observer {
            assertFalse(it is SearchMoviesUiState.Failed)
        })
    }

    @Test
    fun `failed search returns Loading then Failed Search`() {
        val key = "mile"
        val failure = "Search"
        val fromRemote = false
        coEvery { searchMovieUseCase.buildUseCase(key, fromRemote) } returns UseCaseResult.Failed()
        val liveData = viewModel.performAction(SearchMoviesUiAction.Search(key, fromRemote))
        val list = mutableListOf<SearchMoviesUiState>()
        liveData.observe(lifecycleOwner, Observer {
            list.add(it)
            if (list.size == 2) {
                assertEquals(
                    list,
                    listOf(
                        SearchMoviesUiState.Loading,
                        SearchMoviesUiState.Failed(failure)
                    )
                )
            }
        })
        Assert.assertEquals(list.size,2)
    }

    @Test
    fun `failed search doesnt return Searched`() {
        val key = "mile"
        val fromRemote = false
        coEvery { searchMovieUseCase.buildUseCase(key, fromRemote) } returns UseCaseResult.Failed()
        val liveData = viewModel.performAction(SearchMoviesUiAction.Search(key, fromRemote))
        liveData.observe(lifecycleOwner, Observer {
            assertFalse(it is SearchMoviesUiState.Searched)
        })
    }


    @Test
    fun `success AddToFav returns Loading then AddedToFavorite`() {
        val isFav = true
        val id = 3
        coEvery { addToFavoriteUseCase.buildUseCase(isFav, id) } returns UseCaseResult.Success(true)
        val liveData = viewModel.performAction(SearchMoviesUiAction.AddToFav(isFav, id))
        val list = mutableListOf<SearchMoviesUiState>()
        liveData.observe(lifecycleOwner, Observer {
            list.add(it)
            if (list.size == 2) {
                assertEquals(
                    list,
                    listOf(
                        SearchMoviesUiState.Loading,
                        SearchMoviesUiState.AddedToFavorite
                    )
                )
            }
        })
        Assert.assertEquals(list.size,2)
    }


    @Test
    fun `failed AddToFav returns Loading then Failed`() {
        val isFav = true
        val failure = "Fav"
        val id = 3
        coEvery { addToFavoriteUseCase.buildUseCase(isFav, id) } returns UseCaseResult.Failed(
            failure
        )
        val liveData = viewModel.performAction(SearchMoviesUiAction.AddToFav(isFav, id))
        val list = mutableListOf<SearchMoviesUiState>()
        liveData.observe(lifecycleOwner, Observer {
            list.add(it)
            if (list.size == 2) {
                assertEquals(
                    list,
                    listOf(
                        SearchMoviesUiState.Loading,
                        SearchMoviesUiState.Failed(failure)
                    )
                )
            }
        })
        Assert.assertEquals(list.size,2)
    }

    @Test
    fun `Success AddToFav doesnt return Failed`() {
        val isFav = true
        val id = 3
        coEvery { addToFavoriteUseCase.buildUseCase(isFav, id) } returns UseCaseResult.Success(true)
        val liveData = viewModel.performAction(SearchMoviesUiAction.AddToFav(isFav, id))
        liveData.observe(lifecycleOwner, Observer {
            assertFalse(it is SearchMoviesUiState.Failed)
        })
    }

    @Test
    fun `Failed AddToFav doesnt return AddedToFavorite`() {
        val isFav = true
        val failure = "Fav"
        val id = 3
        coEvery { addToFavoriteUseCase.buildUseCase(isFav, id) } returns UseCaseResult.Failed(
            failure
        )
        val liveData = viewModel.performAction(SearchMoviesUiAction.AddToFav(isFav, id))
        liveData.observe(lifecycleOwner, Observer {
            assertFalse(it is SearchMoviesUiState.AddedToFavorite)
        })
    }


}