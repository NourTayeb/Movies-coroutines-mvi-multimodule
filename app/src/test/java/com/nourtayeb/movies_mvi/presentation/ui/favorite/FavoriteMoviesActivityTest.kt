package com.nourtayeb.movies_mvi.presentation.ui.favorite
//
//import android.content.Context
//import android.os.Build
//import androidx.lifecycle.MutableLiveData
//import com.nourtayeb.movies_mvi.R
//import com.nourtayeb.movies_mvi.common.base.ActivityBaseTest
//import io.mockk.every
//import io.mockk.impl.annotations.RelaxedMockK
//import io.mockk.spyk
//import io.mockk.verify
//import org.junit.Before
//import org.junit.Test
//import org.junit.runner.RunWith
//import org.robolectric.Robolectric
//import org.robolectric.RobolectricTestRunner
//import androidx.test.core.app.ApplicationProvider
//import com.nourtayeb.movies_mvi.common.dummyMovies
//import com.nourtayeb.movies_mvi.presentation.common.adapter.MoviesAdapter
//import org.robolectric.annotation.Config
//
//@RunWith(RobolectricTestRunner::class)
//@Config(sdk = [Build.VERSION_CODES.P])
//class FavoriteMoviesActivityTest : ActivityBaseTest() {
//
//
//    lateinit var activity: FavoriteActivity
//
//    @RelaxedMockK
//    lateinit var viewModel: FavoriteViewModel
//
//    lateinit var context: Context
//
//
//    @RelaxedMockK
//    lateinit var adapter: MoviesAdapter
//
//    @Before
//    fun init() {
//        activity = spyk(Robolectric.setupActivity(FavoriteActivity::class.java))
//        every { activity.viewModel } returns viewModel
//        context = ApplicationProvider.getApplicationContext<Context>()
//        activity.moviesAdapter =adapter
//    }
//
////    @Test
////    fun `FavoriteLoadedUiState calls adapter-addData and hideProgressIndicator`() {
////        every {
////            viewModel.performAction(
////                FavoriteMoviesUiAction.LoadFavorite
////            )
////        } returns MutableLiveData(
////            FavoriteMoviesUiState.FavoriteLoaded(dummyMovies)
////        )
////        viewModel.performAction(FavoriteMoviesUiAction.LoadFavorite)
////            .observe(lifecycleOwner, activity.getStateObserver())
////        verify { adapter.addData(dummyMovies) }
////        verify { activity.hideProgressIndicator() }
////    }
////
////    @Test
////    fun `LoadingUiState calls showProgressIndicator`() {
////        every {
////            viewModel.performAction(
////                FavoriteMoviesUiAction.LoadFavorite
////            )
////        } returns MutableLiveData(
////            FavoriteMoviesUiState.Loading
////        )
////        viewModel.performAction(FavoriteMoviesUiAction.LoadFavorite)
////            .observe(lifecycleOwner, activity.getStateObserver())
////        verify { activity.showProgressIndicator() }
////    }
////
////    @Test
////    fun `FailedUiState LoadData calls showNoResultText and hideProgressIndicator`() {
////        every { activity.showNoResultText() } returns Unit
////        every {
////            viewModel.performAction(
////                FavoriteMoviesUiAction.LoadFavorite
////            )
////        } returns MutableLiveData(
////            FavoriteMoviesUiState.Failed()
////        )
////        viewModel.performAction(FavoriteMoviesUiAction.LoadFavorite)
////            .observe(lifecycleOwner, activity.getStateObserver())
////        verify { activity.showNoResultText() }
////        verify { activity.hideProgressIndicator() }
////    }
////
////    @Test
////    fun `FailedUiState Add calls showToast(error_add_to_fav) and hideProgressIndicator`() {
////        val isFav = true
////        val id = 2
////        every { activity.getString(R.string.error_add_to_fav) } returns context.getString(R.string.error_add_to_fav)
////        every { activity.showNoResultText() } returns Unit
////        every {
////            viewModel.performAction(
////                FavoriteMoviesUiAction.AddToFav(
////                    isFav,
////                    id
////                )
////            )
////        } returns MutableLiveData(
////            FavoriteMoviesUiState.Failed()
////        )
////        viewModel.performAction(FavoriteMoviesUiAction.AddToFav(isFav, id))
////            .observe(lifecycleOwner, activity.getStateObserver())
////        verify { activity.showNoResultText() }
////        verify { activity.hideProgressIndicator() }
////    }
////
////    @Test
////    fun `AddedToFavoriteUiState calls showToast(favorite_state_changed) and hideProgressIndicator and broadcast`() {
////        val isFav = true
////        val id = 2
////        val message = context.getString(R.string.favorite_state_changed)
////        every { activity.getString(R.string.favorite_state_changed) } returns context.getString(R.string.favorite_state_changed)
////        every { activity.showToast(message) } returns Unit
////        val addedTofavriteUiState= FavoriteMoviesUiState.AddedToFavorite(id,isFav)
////        every {
////            viewModel.performAction(
////                FavoriteMoviesUiAction.AddToFav(
////                    isFav,
////                    id
////                )
////            )
////        } returns MutableLiveData(
////            addedTofavriteUiState
////        )
////        viewModel.performAction(FavoriteMoviesUiAction.AddToFav(isFav, id))
////            .observe(lifecycleOwner, activity.getStateObserver())
////        verify { activity.showToast(message) }
////        verify { activity.hideProgressIndicator() }
////        verify { activity.broadcast(addedTofavriteUiState) }
////    }
////
////    @Test
////    fun `ExceptionUiState AddToFav calls showToast(some_error_occurred) and hideProgressIndicator`() {
////        val isFav = true
////        val id = 2
////        val message = context.getString(R.string.some_error_occurred)
////        every { activity.getString(R.string.some_error_occurred) } returns context.getString(R.string.some_error_occurred)
////        every { activity.showToast(message) } returns Unit
////        every {
////            viewModel.performAction(
////                FavoriteMoviesUiAction.AddToFav(
////                    isFav,
////                    id
////                )
////            )
////        } returns MutableLiveData(
////            FavoriteMoviesUiState.Exception()
////        )
////        viewModel.performAction(FavoriteMoviesUiAction.AddToFav(isFav, id))
////            .observe(lifecycleOwner, activity.getStateObserver())
////        verify { activity.showToast(message) }
////        verify { activity.hideProgressIndicator() }
////    }
////
////    @Test
////    fun `ExceptionUiState LoadFavorite calls showToast(some_error_occurred) and hideProgressIndicator`() {
////        val message = context.getString(R.string.some_error_occurred)
////        every { activity.getString(R.string.some_error_occurred) } returns context.getString(R.string.some_error_occurred)
////        every { activity.showToast(message) } returns Unit
////        every {
////            viewModel.performAction(
////                FavoriteMoviesUiAction.LoadFavorite
////            )
////        } returns MutableLiveData(
////            FavoriteMoviesUiState.Exception()
////        )
////        viewModel.performAction(FavoriteMoviesUiAction.LoadFavorite)
////            .observe(lifecycleOwner, activity.getStateObserver())
////        verify { activity.showToast(message) }
////        verify { activity.hideProgressIndicator() }
////    }
////
////    @Test
////    fun `loadFavorite calls viewmodel performAction LoadFavorite`(){
////        activity.loadFavorite()
////        val action = FavoriteMoviesUiAction.LoadFavorite
////        verify { viewModel.performAction(action) }
////    }
////    @Test
////    fun `addToFavoriteAction calls viewmodel performAction AddToFavAction`(){
////        val isFav = true
////        val id = 3
////        activity.addToFavorite(isFav,id)
////        val action = FavoriteMoviesUiAction.AddToFav(isFav,id)
////        verify { viewModel.performAction(action) }
////    }
//}