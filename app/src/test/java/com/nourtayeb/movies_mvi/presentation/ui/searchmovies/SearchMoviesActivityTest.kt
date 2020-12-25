package com.nourtayeb.movies_mvi.presentation.ui.searchmovies
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
//import com.nourtayeb.movies_mvi.presentation.common.messages.FavoriteStatusChanged
//import org.robolectric.annotation.Config
//
//@RunWith(RobolectricTestRunner::class)
//@Config(sdk = [Build.VERSION_CODES.P])
//class SearchMoviesActivityTest : ActivityBaseTest() {
//
//    lateinit var activity: SearchMoviesActivity
//
//    @RelaxedMockK
//    lateinit var viewModel: SearchMovieViewModel
//
//    lateinit var context: Context
//
//    @RelaxedMockK
//    lateinit var adapter: MoviesAdapter
//
//    @Before
//    fun init() {
//        activity = spyk(Robolectric.setupActivity(SearchMoviesActivity::class.java))
//        every { activity.viewModel } returns viewModel
//        context = ApplicationProvider.getApplicationContext<Context>()
//        // every { activity.moviesAdapter } returns adapter
//        activity.moviesAdapter = adapter
//    }
//
////    @Test
////    fun `SearchedUiState calls adapter-addData and hideProgressIndicator`() {
////        val key = "mile"
////        val fromRemote = true
////        every {
////            viewModel.performAction(
////                SearchMoviesUiAction.Search(
////                    key,
////                    fromRemote
////                )
////            )
////        } returns MutableLiveData(
////            SearchMoviesUiState.Searched(dummyMovies)
////        )
////        viewModel.performAction(SearchMoviesUiAction.Search(key, fromRemote))
////            .observe(lifecycleOwner, activity.getStateObserver())
////        verify { adapter.addData(dummyMovies) }
////        verify { activity.hideProgressIndicator() }
////    }
////
////    @Test
////    fun `LoadingUiState calls showProgressIndicator`() {
////        val key = "mile"
////        val fromRemote = true
////        every {
////            viewModel.performAction(
////                SearchMoviesUiAction.Search(
////                    key,
////                    fromRemote
////                )
////            )
////        } returns MutableLiveData(
////            SearchMoviesUiState.Loading
////        )
////        viewModel.performAction(SearchMoviesUiAction.Search(key, fromRemote))
////            .observe(lifecycleOwner, activity.getStateObserver())
////        verify { activity.showProgressIndicator() }
////    }
////
////    @Test
////    fun `FailedUiState Search calls adapter-clearData and showNoResultText and hideProgressIndicato`() {
////        val key = "mile"
////        val fromRemote = true
////        val error = "Search"
////        every { activity.showNoResultText() } returns Unit
////        every {
////            viewModel.performAction(
////                SearchMoviesUiAction.Search(
////                    key,
////                    fromRemote
////                )
////            )
////        } returns MutableLiveData(
////            SearchMoviesUiState.Failed(error)
////        )
////        viewModel.performAction(SearchMoviesUiAction.Search(key, fromRemote))
////            .observe(lifecycleOwner, activity.getStateObserver())
////        verify { activity.showNoResultText() }
////        verify { adapter.clearData() }
////        verify { activity.hideProgressIndicator() }
////    }
////
////    @Test
////    fun `FailedUiState Add calls adapter-clearData and showToast(error_add_to_fav) and hideProgressIndicator`() {
////        val isFav = true
////        val id = 2
////        val error = "Add"
////        val message = context.getString(R.string.error_add_to_fav)
////        every { activity.getString(R.string.error_add_to_fav) } returns context.getString(R.string.error_add_to_fav)
////        every { activity.showToast(message) } returns Unit
////        every {
////            viewModel.performAction(
////                SearchMoviesUiAction.AddToFav(
////                    isFav,
////                    id
////                )
////            )
////        } returns MutableLiveData(
////            SearchMoviesUiState.Failed(error)
////        )
////        viewModel.performAction(SearchMoviesUiAction.AddToFav(isFav, id))
////            .observe(lifecycleOwner, activity.getStateObserver())
////        verify { activity.showToast(message) }
////        verify { activity.hideProgressIndicator() }
////    }
////
////    @Test
////    fun `AddedToFavoriteUiState calls showToast(favorite_state_changed) and hideProgressIndicator`() {
////        val isFav = true
////        val id = 2
////        val message = context.getString(R.string.favorite_state_changed)
////        every { activity.getString(R.string.favorite_state_changed) } returns context.getString(R.string.favorite_state_changed)
////        every { activity.showToast(message) } returns Unit
////        every {
////            viewModel.performAction(
////                SearchMoviesUiAction.AddToFav(
////                    isFav,
////                    id
////                )
////            )
////        } returns MutableLiveData(
////            SearchMoviesUiState.AddedToFavorite
////        )
////        viewModel.performAction(SearchMoviesUiAction.AddToFav(isFav, id))
////            .observe(lifecycleOwner, activity.getStateObserver())
////        verify { activity.showToast(message) }
////        verify { activity.hideProgressIndicator() }
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
////                SearchMoviesUiAction.AddToFav(
////                    isFav,
////                    id
////                )
////            )
////        } returns MutableLiveData(
////            SearchMoviesUiState.Exception()
////        )
////        viewModel.performAction(SearchMoviesUiAction.AddToFav(isFav, id))
////            .observe(lifecycleOwner, activity.getStateObserver())
////        verify { activity.showToast(message) }
////        verify { activity.hideProgressIndicator() }
////    }
////
////    @Test
////    fun `ExceptionUiState Search calls showToast(some_error_occurred) and hideProgressIndicator`() {
////        val fromRemote = true
////        val key = "mile"
////        val message = context.getString(R.string.some_error_occurred)
////        every { activity.getString(R.string.some_error_occurred) } returns context.getString(R.string.some_error_occurred)
////        every { activity.showToast(message) } returns Unit
////        every {
////            viewModel.performAction(
////                SearchMoviesUiAction.Search(
////                    key,
////                    fromRemote
////                )
////            )
////        } returns MutableLiveData(
////            SearchMoviesUiState.Exception()
////        )
////        viewModel.performAction(SearchMoviesUiAction.Search(key, fromRemote))
////            .observe(lifecycleOwner, activity.getStateObserver())
////        verify { activity.showToast(message) }
////        verify { activity.hideProgressIndicator() }
////    }
////
////    @Test
////    fun `searchAction calls viewmodel performAction SearchAction`(){
////        val fromRemote = true
////        val key = "mile"
////        activity.searchAction(key,fromRemote)
////        val action = SearchMoviesUiAction.Search(key,fromRemote)
////        verify { viewModel.performAction(action) }
////    }
////    @Test
////    fun `addToFavoriteAction calls viewmodel performAction AddToFavAction`(){
////        val isFav = true
////        val id = 3
////        activity.addToFavoriteAction(isFav,id)
////        val action = SearchMoviesUiAction.AddToFav(isFav,id)
////        verify { viewModel.performAction(action) }
////    }
////    @Test
////    fun `onMessageReceived calls adapter changeItemIsFav`(){
////        val isFav = true
////        val id = 3
////        val favoriteStateChanged = FavoriteStatusChanged(isFav,id)
////        every { adapter.changeItemIsFav(isFav,id) } returns Unit
////        activity.onMessageReceived(favoriteStateChanged)
////        verify { adapter.changeItemIsFav(isFav,id) }
////    }
//}