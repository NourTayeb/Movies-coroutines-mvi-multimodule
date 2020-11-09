package com.nourtayeb.movies_mvi.presentation.ui.favorite

import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.nourtayeb.movies_mvi.R
import com.nourtayeb.movies_mvi.common.utility.shareBitmap
import com.nourtayeb.movies_mvi.presentation.common.adapter.MoviesAdapter
import com.nourtayeb.movies_mvi.presentation.common.messages.FavoriteStatusChanged
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.android.synthetic.main.activity_search.noResult
import kotlinx.android.synthetic.main.activity_search.progressIndicator
import kotlinx.android.synthetic.main.activity_search.recyclerView
import org.greenrobot.eventbus.EventBus


@AndroidEntryPoint
class FavoriteActivity : AppCompatActivity() {

    fun getStateObserver(): Observer<FavoriteMoviesUiState> {
        return Observer {
            noResult.text = ""
            when(it) {
                is FavoriteMoviesUiState.Loading -> {
                    showProgressIndicator()
                }
                is FavoriteMoviesUiState.FavoriteLoaded -> {
                    hideProgressIndicator()
                    moviesAdapter.addData(it.data)
                }
                is FavoriteMoviesUiState.Failed -> {
                    showNoResultText()
                    hideProgressIndicator()
                }
                is FavoriteMoviesUiState.AddedToFavorite -> {
                    showToast(getString(R.string.favorite_state_changed))
                    hideProgressIndicator()
                    broadcast(it)
                }
                is FavoriteMoviesUiState.Exception -> {
                    showToast(getString(R.string.some_error_occurred))
                    hideProgressIndicator()
                }
            }
        }
    }
    fun showProgressIndicator(){
        progressIndicator.show()
    }
    fun hideProgressIndicator(){
        progressIndicator.hide()
    }

    fun showNoResultText(){
        noResult.text = getString(R.string.your_favorite_is_empty)
    }
    fun showToast(string:String){
        Toast.makeText(
            SearchMovieViewModel@ this,
            string,
            Toast.LENGTH_LONG
        ).show()
    }

     val viewModel: FavoriteViewModel by viewModels()

    lateinit var moviesAdapter : MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        moviesAdapter = MoviesAdapter()
        setUpRecyclerView()
        back.setOnClickListener {
            finish()
        }
        Handler().postDelayed({
            loadFavorite()
        },1500)
    }

    private fun setUpRecyclerView() {
        with(recyclerView){
            adapter = moviesAdapter
            layoutManager = StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL)
        }
        with(moviesAdapter){
            onFavoriteClicked = { isFav, id -> addToFavorite(isFav,id) }
            onShareClicked = { shareBitmap(it) }
        }
    }


    fun broadcast(addedToFavorite:FavoriteMoviesUiState.AddedToFavorite){
        EventBus.getDefault().post(FavoriteStatusChanged(addedToFavorite.isFav,addedToFavorite.id))
    }


    fun loadFavorite(){
        viewModel.performAction(FavoriteMoviesUiAction.LoadFavorite).observe(this,getStateObserver())
    }


    fun addToFavorite(isFav: Boolean, id: Int) {
        viewModel.performAction(FavoriteMoviesUiAction.AddToFav(isFav,id)).observe(this,getStateObserver())
    }

}
