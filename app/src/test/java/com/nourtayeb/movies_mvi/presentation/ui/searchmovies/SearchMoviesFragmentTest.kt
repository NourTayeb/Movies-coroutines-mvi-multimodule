package com.nourtayeb.movies_mvi.presentation.ui.searchmovies

import android.content.Context
import android.os.Build
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import com.nourtayeb.movies_mvi.R
import com.nourtayeb.movies_mvi.common.base.ActivityBaseTest
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.spyk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import androidx.test.core.app.ApplicationProvider
import com.google.android.material.progressindicator.ProgressIndicator
import com.nourtayeb.movies_mvi.common.dummyMovies
import com.nourtayeb.movies_mvi.presentation.common.adapter.MoviesAdapter
import com.nourtayeb.movies_mvi.presentation.common.messages.FavoriteStatusChanged
import kotlinx.android.synthetic.main.fragment_search_movies.*
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class SearchMoviesFragmentTest : ActivityBaseTest() {

    lateinit var fragment: SearchMoviesFragment

    @RelaxedMockK
    lateinit var viewModel: SearchMovieViewModel

    lateinit var context: Context

    @RelaxedMockK
    lateinit var adapter: MoviesAdapter

    @Before
    fun init() {
        fragment = spyk(SearchMoviesFragment())
        every { fragment.viewModel } returns viewModel
        context = ApplicationProvider.getApplicationContext<Context>()
        // every { activity.moviesAdapter } returns adapter
        fragment.moviesAdapter = adapter
        every { fragment.viewLifecycleOwner } returns lifecycleOwner
        every { fragment.noResult } returns TextView(context)
        every { fragment.progressIndicator } returns ProgressIndicator(context)
    }

    @Test
    fun `SearchedUiState calls adapter-addData and hideProgressIndicator`() {
        val key = "mile"
        val fromRemote = true
        every {
            viewModel.state
        } returns MutableLiveData(
            SearchMoviesUiState.Searched(dummyMovies)
        )
        viewModel.performAction(SearchMoviesUiAction.Search(key, fromRemote))
        viewModel.state.observe(lifecycleOwner, fragment.getStateObserver())
        verify { adapter.addData(dummyMovies) }
        verify { fragment.hideProgressIndicator() }
    }

    @Test
    fun `LoadingUiState calls showProgressIndicator`() {
        val key = "mile"
        val fromRemote = true
        every {
            viewModel.state
        } returns MutableLiveData(
            SearchMoviesUiState.Loading
        )
        viewModel.performAction(SearchMoviesUiAction.Search(key, fromRemote))
        viewModel.state    .observe(lifecycleOwner, fragment.getStateObserver())
        verify { fragment.showProgressIndicator() }
    }

    @Test
    fun `FailedUiState Search calls adapter-clearData and showNoResultText and hideProgressIndicato`() {
        val key = "mile"
        val fromRemote = true
        val error = "Search"
        every { fragment.showNoResultText() } returns Unit
        every {
            viewModel.state
        } returns MutableLiveData(
            SearchMoviesUiState.Failed(error)
        )
        viewModel.performAction(SearchMoviesUiAction.Search(key, fromRemote))
        viewModel.state.observe(lifecycleOwner, fragment.getStateObserver())
        verify { fragment.showNoResultText() }
        verify { adapter.clearData() }
        verify { fragment.hideProgressIndicator() }
    }

    @Test
    fun `FailedUiState Add calls adapter-clearData and showToast(error_add_to_fav) and hideProgressIndicator`() {
        val isFav = true
        val id = 2
        val error = "Add"
        val message = context.getString(R.string.error_add_to_fav)
        every { fragment.getString(R.string.error_add_to_fav) } returns context.getString(R.string.error_add_to_fav)
        every { fragment.showToast(message) } returns Unit
        every {
            viewModel.state
        } returns MutableLiveData(
            SearchMoviesUiState.Failed(error)
        )
        viewModel.performAction(SearchMoviesUiAction.AddToFav(isFav, id))
        viewModel.state.observe(lifecycleOwner, fragment.getStateObserver())
        verify { fragment.showToast(message) }
        verify { fragment.hideProgressIndicator() }
    }

    @Test
    fun `AddedToFavoriteUiState calls showToast(favorite_state_changed) and hideProgressIndicator`() {
        val isFav = true
        val id = 2
        val message = context.getString(R.string.favorite_state_changed)
        every { fragment.getString(R.string.favorite_state_changed) } returns context.getString(R.string.favorite_state_changed)
        every { fragment.showToast(message) } returns Unit
        every {
            viewModel.state
        } returns MutableLiveData(
            SearchMoviesUiState.AddedToFavorite
        )
        viewModel.performAction(SearchMoviesUiAction.AddToFav(isFav, id))
        viewModel.state.observe(lifecycleOwner, fragment.getStateObserver())
        verify { fragment.showToast(message) }
        verify { fragment.hideProgressIndicator() }
    }

    @Test
    fun `ExceptionUiState AddToFav calls showToast(some_error_occurred) and hideProgressIndicator`() {
        val isFav = true
        val id = 2
        val message = context.getString(R.string.some_error_occurred)
        every { fragment.getString(R.string.some_error_occurred) } returns context.getString(R.string.some_error_occurred)
        every { fragment.showToast(message) } returns Unit
        every {
            viewModel.state
        } returns MutableLiveData(
            SearchMoviesUiState.Exception()
        )
        viewModel.performAction(SearchMoviesUiAction.AddToFav(isFav, id))
        viewModel.state.observe(lifecycleOwner, fragment.getStateObserver())
        verify { fragment.showToast(message) }
        verify { fragment.hideProgressIndicator() }
    }

    @Test
    fun `ExceptionUiState Search calls showToast(some_error_occurred) and hideProgressIndicator`() {
        val fromRemote = true
        val key = "mile"
        val message = context.getString(R.string.some_error_occurred)
        every { fragment.getString(R.string.some_error_occurred) } returns context.getString(R.string.some_error_occurred)
        every { fragment.showToast(message) } returns Unit
        every {
            viewModel.state
        } returns MutableLiveData(
            SearchMoviesUiState.Exception()
        )
        viewModel.performAction(SearchMoviesUiAction.Search(key, fromRemote))
        viewModel.state.observe(lifecycleOwner, fragment.getStateObserver())
        verify { fragment.showToast(message) }
        verify { fragment.hideProgressIndicator() }
    }

    @Test
    fun `searchAction calls viewmodel performAction SearchAction`(){
        val fromRemote = true
        val key = "mile"
        fragment.searchAction(key,fromRemote)
        val action = SearchMoviesUiAction.Search(key,fromRemote)
        verify { viewModel.performAction(action) }
    }
    @Test
    fun `addToFavoriteAction calls viewmodel performAction AddToFavAction`(){
        val isFav = true
        val id = 3
        fragment.addToFavoriteAction(isFav,id)
        val action = SearchMoviesUiAction.AddToFav(isFav,id)
        verify { viewModel.performAction(action) }
    }
    @Test
    fun `onMessageReceived calls adapter changeItemIsFav`(){
        val isFav = true
        val id = 3
        val favoriteStateChanged = FavoriteStatusChanged(isFav,id)
        every { adapter.changeItemIsFav(isFav,id) } returns Unit
        fragment.onMessageReceived(favoriteStateChanged)
        verify { adapter.changeItemIsFav(isFav,id) }
    }
}