package com.nourtayeb.movies_mvi.presentation.ui.searchmovies

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.nourtayeb.movies_mvi.R
import com.nourtayeb.movies_mvi.common.utility.shareBitmap
import com.nourtayeb.movies_mvi.presentation.common.adapter.MoviesAdapter
import com.nourtayeb.movies_mvi.presentation.common.delegates.EventBusSubscriberDelegate
import com.nourtayeb.movies_mvi.presentation.common.delegates.EventBusSubscriberImp
import com.nourtayeb.movies_mvi.presentation.ui.favorite.FavoriteActivity
import com.nourtayeb.movies_mvi.presentation.common.messages.FavoriteStatusChanged
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_search.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

import com.nourtayeb.movies_mvi.common.utility.OnQueryTextListener

@AndroidEntryPoint
class SearchMoviesActivity : AppCompatActivity(),
    EventBusSubscriberDelegate by EventBusSubscriberImp() {

    val viewModel: SearchMovieViewModel by viewModels()
   lateinit var moviesAdapter :MoviesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        moviesAdapter = MoviesAdapter()
        attachEventBus(lifecycle, this)
        setUpRecyclerViewAdapters()
        searchView.OnQueryTextListener {
            searchAction(it)
        }
//        search.clicks()
//            .onEach {  }
//            .launchIn(MainScope())
    }

    fun showProgressIndicator(){
        progressIndicator.show()
    }
    fun hideProgressIndicator(){
        progressIndicator.hide()
    }

    fun showNoResultText(){
        noResult.text = getString(R.string.no_result_for_search)
    }
    fun showToast(string:String){
        Toast.makeText(
            SearchMovieViewModel@ this,
            string,
            Toast.LENGTH_LONG
        ).show()
    }
    fun getStateObserver(): Observer<SearchMoviesUiState> {
        return Observer {
            noResult.text = ""
            when (it) {
                is SearchMoviesUiState.Loading -> {
                    showProgressIndicator()
                }
                is SearchMoviesUiState.Searched -> {
                    hideProgressIndicator()
                    moviesAdapter.addData(it.data)
                }
                is SearchMoviesUiState.Failed -> {
                    when {
                        it.status.equals("Search") -> {
                            showNoResultText()
                            moviesAdapter.clearData()
                        }
                        it.status.equals("Add") -> {
                            showToast(getString(R.string.error_add_to_fav))
                        }
                    }
                    hideProgressIndicator()
                }
                is SearchMoviesUiState.AddedToFavorite -> {
                    showToast(getString(R.string.favorite_state_changed))
                    hideProgressIndicator()
                }
                is SearchMoviesUiState.Exception -> {
                    showToast(getString(R.string.some_error_occurred))
                    hideProgressIndicator()
                }
            }
        }
    }


     fun setUpRecyclerViewAdapters() {
        with(recyclerView) {
            adapter = moviesAdapter
            layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        }
        with(moviesAdapter) {
            onFavoriteClicked = { isFav, id -> addToFavoriteAction(isFav, id) }
            onShareClicked = { shareBitmap(it) }
        }
        favorite.setOnClickListener {
            startActivity(Intent(this, FavoriteActivity::class.java))
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageReceived(event: FavoriteStatusChanged) {
        moviesAdapter.changeItemIsFav(event.isFav, event.id)
    }

    fun searchAction(key: String, firstTime: Boolean = true) {
        viewModel.performAction(SearchMoviesUiAction.Search(key, firstTime))
            .observe(this, getStateObserver())
    }

    fun addToFavoriteAction(isFav: Boolean, id: Int) {
        viewModel.performAction(SearchMoviesUiAction.AddToFav(isFav, id))
            .observe(this, getStateObserver())
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("key", searchView.query.toString())
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.getString("key")?.let { searchAction(it, false) }
    }

}
