package com.nourtayeb.movies_mvi.presentation.ui.favorite
//
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
import kotlinx.android.synthetic.main.fragment_favorite.*
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class FavoriteMoviesFragmentTest : ActivityBaseTest() {


    lateinit var fragment: FavoriteFragment

    @RelaxedMockK
    lateinit var viewModel: FavoriteViewModel

    lateinit var context: Context


    @RelaxedMockK
    lateinit var adapter: MoviesAdapter

    @Before
    fun init() {
        fragment = spyk(FavoriteFragment())
        every { fragment.viewModel } returns viewModel
        context = ApplicationProvider.getApplicationContext<Context>()
        fragment.moviesAdapter =adapter
        every { fragment.noResult } returns TextView(context)
        every { fragment.progressIndicator } returns ProgressIndicator(context)
        every { fragment.viewLifecycleOwner } returns lifecycleOwner
    }

    @Test
    fun `FavoriteLoadedUiState calls adapter-addData and hideProgressIndicator`() {
        every {
            viewModel.state
        } returns MutableLiveData(
            FavoriteMoviesUiState.FavoriteLoaded(dummyMovies)
        )
        viewModel.performAction(FavoriteMoviesUiAction.LoadFavorite)
        viewModel.state.observe(lifecycleOwner, fragment.getStateObserver())
        verify { adapter.addData(dummyMovies) }
        verify { fragment.hideProgressIndicator() }
    }

    @Test
    fun `LoadingUiState calls showProgressIndicator`() {
        every {
            viewModel.state
        } returns MutableLiveData(
            FavoriteMoviesUiState.Loading
        )
        viewModel.performAction(FavoriteMoviesUiAction.LoadFavorite)
        viewModel.state.observe(lifecycleOwner, fragment.getStateObserver())
        verify { fragment.showProgressIndicator() }
    }

    @Test
    fun `FailedUiState LoadData calls showNoResultText and hideProgressIndicator`() {
        every { fragment.showNoResultText() } returns Unit
        every {
            viewModel.state
        } returns MutableLiveData(
            FavoriteMoviesUiState.Failed()
        )
        viewModel.performAction(FavoriteMoviesUiAction.LoadFavorite)
        viewModel.state.observe(lifecycleOwner, fragment.getStateObserver())
        verify { fragment.showNoResultText() }
        verify { fragment.hideProgressIndicator() }
    }

    @Test
    fun `FailedUiState Add calls showToast(error_add_to_fav) and hideProgressIndicator`() {
        val isFav = true
        val id = 2
        every { fragment.getString(R.string.error_add_to_fav) } returns context.getString(R.string.error_add_to_fav)
        every { fragment.showNoResultText() } returns Unit
        every {viewModel.state
        } returns MutableLiveData(
            FavoriteMoviesUiState.Failed()
        )
        viewModel.performAction(FavoriteMoviesUiAction.AddToFav(isFav, id))
        viewModel.state.observe(lifecycleOwner, fragment.getStateObserver())
        verify { fragment.showNoResultText() }
        verify { fragment.hideProgressIndicator() }
    }

    @Test
    fun `AddedToFavoriteUiState calls showToast(favorite_state_changed) and hideProgressIndicator and broadcast`() {
        val isFav = true
        val id = 2
        val message = context.getString(R.string.favorite_state_changed)
        every { fragment.getString(R.string.favorite_state_changed) } returns context.getString(R.string.favorite_state_changed)
        every { fragment.showToast(message) } returns Unit
        val addedTofavriteUiState= FavoriteMoviesUiState.AddedToFavorite(id,isFav)
        every {
            viewModel.state
        } returns MutableLiveData(
            addedTofavriteUiState
        )
        viewModel.performAction(FavoriteMoviesUiAction.AddToFav(isFav, id))
        viewModel.state.observe(lifecycleOwner, fragment.getStateObserver())
        verify { fragment.showToast(message) }
        verify { fragment.hideProgressIndicator() }
        verify { fragment.broadcast(addedTofavriteUiState) }
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
            FavoriteMoviesUiState.Exception()
        )
        viewModel.performAction(FavoriteMoviesUiAction.AddToFav(isFav, id))
        viewModel.state.observe(lifecycleOwner, fragment.getStateObserver())
        verify { fragment.showToast(message) }
        verify { fragment.hideProgressIndicator() }
    }

    @Test
    fun `ExceptionUiState LoadFavorite calls showToast(some_error_occurred) and hideProgressIndicator`() {
        val message = context.getString(R.string.some_error_occurred)
        every { fragment.getString(R.string.some_error_occurred) } returns context.getString(R.string.some_error_occurred)
        every { fragment.showToast(message) } returns Unit
        every {
            viewModel.state
        } returns MutableLiveData(
            FavoriteMoviesUiState.Exception()
        )
        viewModel.performAction(FavoriteMoviesUiAction.LoadFavorite)
        viewModel.state.observe(lifecycleOwner, fragment.getStateObserver())
        verify { fragment.showToast(message) }
        verify { fragment.hideProgressIndicator() }
    }

    @Test
    fun `loadFavorite calls viewmodel performAction LoadFavorite`(){
        fragment.loadFavorite()
        val action = FavoriteMoviesUiAction.LoadFavorite
        verify { viewModel.performAction(action) }
    }
    @Test
    fun `addToFavoriteAction calls viewmodel performAction AddToFavAction`(){
        val isFav = true
        val id = 3
        fragment.addToFavorite(isFav,id)
        val action = FavoriteMoviesUiAction.AddToFav(isFav,id)
        verify { viewModel.performAction(action) }
    }
}