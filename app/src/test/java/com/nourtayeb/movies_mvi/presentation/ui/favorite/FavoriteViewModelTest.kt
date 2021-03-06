package com.nourtayeb.movies_mvi.presentation.ui.favorite

import androidx.lifecycle.Observer
import com.nourtayeb.ads.domain.usecase.ShowImageAdsUseCase
import com.nourtayeb.movies_mvi.common.base.ViewModelBaseTest
import com.nourtayeb.movies_mvi.common.dummyMovies
import com.nourtayeb.movies_mvi.domain.UseCaseResult
import com.nourtayeb.movies_mvi.domain.usecase.AddToFavoriteUseCase
import com.nourtayeb.movies_mvi.domain.usecase.GetFavoriteUseCase
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class FavoriteViewModelTest : ViewModelBaseTest() {

    @RelaxedMockK
    lateinit var getFavoriteUseCase: GetFavoriteUseCase

    @RelaxedMockK
    lateinit var addToFavoriteUseCase: AddToFavoriteUseCase

    @RelaxedMockK
    lateinit var showImageAdsUseCase: ShowImageAdsUseCase


    lateinit var viewModel: FavoriteViewModel

    @Before
    fun init() {
        viewModel = FavoriteViewModel(
            testDispatcher,
            getFavoriteUseCase,
            addToFavoriteUseCase,
            showImageAdsUseCase
        )
    }

    @Test
    fun `success getFavorite returns Loading then FavoriteLoaded`() {

        coEvery { getFavoriteUseCase.buildUseCase() } returns UseCaseResult.Success(
            dummyMovies
        )
        viewModel.performAction(FavoriteMoviesUiAction.LoadFavorite)
        val list = mutableListOf<FavoriteMoviesUiState>()
        viewModel.state.observe(lifecycleOwner, Observer {
            list.add(it)
            if (list.size == 2) {
                Assert.assertEquals(
                    list,
                    listOf(
                        FavoriteMoviesUiState.Loading,
                        FavoriteMoviesUiState.FavoriteLoaded(dummyMovies)
                    )
                )
            }
        })
        Assert.assertEquals(list.size, 2)
    }

    @Test
    fun `success getFavorite doesnt return Failed`() {

        coEvery { getFavoriteUseCase.buildUseCase() } returns UseCaseResult.Success(
            dummyMovies
        )
        viewModel.performAction(FavoriteMoviesUiAction.LoadFavorite)
        viewModel.state.observe(lifecycleOwner, Observer {
            Assert.assertFalse(it is FavoriteMoviesUiState.Failed)
        })
    }

    @Test
    fun `failed getFavorite returns Loading then Failed LoadFavorite`() {
        val failure = ""
        coEvery { getFavoriteUseCase.buildUseCase() } returns UseCaseResult.Failed()
        viewModel.performAction(FavoriteMoviesUiAction.LoadFavorite)
        val list = mutableListOf<FavoriteMoviesUiState>()
        viewModel.state.observe(lifecycleOwner, Observer {
            list.add(it)
            if (list.size == 2) {
                Assert.assertEquals(
                    list,
                    listOf(
                        FavoriteMoviesUiState.Loading,
                        FavoriteMoviesUiState.Failed(failure)
                    )
                )
            }
        })
        Assert.assertEquals(list.size, 2)
    }

    @Test
    fun `failed getFavorite doesnt return FavoriteLoaded`() {

        coEvery { getFavoriteUseCase.buildUseCase() } returns UseCaseResult.Failed()
        viewModel.performAction(FavoriteMoviesUiAction.LoadFavorite)
        viewModel.state.observe(lifecycleOwner, Observer {
            Assert.assertFalse(it is FavoriteMoviesUiState.FavoriteLoaded)
        })
    }


    @Test
    fun `success AddToFav returns Loading then AddedToFavorite`() {
        val isFav = true
        val id = 3
        coEvery { addToFavoriteUseCase.buildUseCase(isFav, id) } returns UseCaseResult.Success(true)
        viewModel.performAction(FavoriteMoviesUiAction.AddToFav(isFav, id))
        val list = mutableListOf<FavoriteMoviesUiState>()
        viewModel.state.observe(lifecycleOwner, Observer {
            list.add(it)
            if (list.size == 2) {
                Assert.assertEquals(
                    list,
                    listOf(
                        FavoriteMoviesUiState.Loading,
                        FavoriteMoviesUiState.AddedToFavorite(id, isFav)
                    )
                )
            }
        })
        Assert.assertEquals(list.size, 2)
    }


    @Test
    fun `failed AddToFav returns Loading then Failed`() {
        val isFav = true
        val failure = ""
        val id = 3
        coEvery { addToFavoriteUseCase.buildUseCase(isFav, id) } returns UseCaseResult.Failed(
            failure
        )
        viewModel.performAction(FavoriteMoviesUiAction.AddToFav(isFav, id))
        val list = mutableListOf<FavoriteMoviesUiState>()
        viewModel.state.observe(lifecycleOwner, Observer {
            list.add(it)
            if (list.size == 2) {
                Assert.assertEquals(
                    list,
                    listOf(
                        FavoriteMoviesUiState.Loading,
                        FavoriteMoviesUiState.Failed(failure)
                    )
                )
            }
        })
        Assert.assertEquals(list.size, 2)
    }

    @Test
    fun `Success AddToFav doesnt return Failed`() {
        val isFav = true
        val id = 3
        coEvery { addToFavoriteUseCase.buildUseCase(isFav, id) } returns UseCaseResult.Success(true)
        viewModel.performAction(FavoriteMoviesUiAction.AddToFav(isFav, id))
        viewModel.state.observe(lifecycleOwner, Observer {
            Assert.assertFalse(it is FavoriteMoviesUiState.Failed)
        })
    }

    @Test
    fun `Failed AddToFav doesnt return AddedToFavorite`() {
        val isFav = true
        val failure = ""
        val id = 3
        coEvery { addToFavoriteUseCase.buildUseCase(isFav, id) } returns UseCaseResult.Failed(
            failure
        )
        viewModel.performAction(FavoriteMoviesUiAction.AddToFav(isFav, id))
        viewModel.state.observe(lifecycleOwner, Observer {
            Assert.assertFalse(it is FavoriteMoviesUiState.AddedToFavorite)
        })
    }


//    @Test
//    fun `ShowAdds return AdLoaded every three seconds`() {
//        coEvery { showAdsUseCase.buildUseCase() } returns
//        viewModel.performAction(FavoriteMoviesUiAction.AddToFav(isFav, id))
//        viewModel.state.observe(lifecycleOwner, Observer {
//            Assert.assertFalse(it is FavoriteMoviesUiState.AddedToFavorite)
//        })
//    }
}