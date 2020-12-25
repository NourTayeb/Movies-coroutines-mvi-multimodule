package com.nourtayeb.movies_mvi.presentation.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.nourtayeb.movies_mvi.R
import com.nourtayeb.movies_mvi.common.utility.loadImageFromUrl
import com.nourtayeb.movies_mvi.common.utility.shareBitmap
import com.nourtayeb.movies_mvi.presentation.common.adapter.MoviesAdapter
import com.nourtayeb.movies_mvi.presentation.common.messages.FavoriteStatusChanged
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.android.synthetic.main.fragment_favorite.back
import kotlinx.android.synthetic.main.fragment_favorite.noResult
import kotlinx.android.synthetic.main.fragment_favorite.progressIndicator
import kotlinx.android.synthetic.main.fragment_favorite.recyclerView
import org.greenrobot.eventbus.EventBus

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    lateinit var navController: NavController
    lateinit var currentSearch: CurrentSearch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentSearch = requireArguments().getParcelable("currentSearch")!!
        moviesAdapter = MoviesAdapter()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        back.setOnClickListener {
            navController.popBackStack()
        }
        searchingCurrently.text = "Searching for: "+currentSearch.searchKey
        viewModel.state.observe(viewLifecycleOwner,getStateObserver())
        setUpRecyclerView()
    }


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
                    viewModel.performAction(FavoriteMoviesUiAction.ShowAds)
                }
                is FavoriteMoviesUiState.Failed -> {
                    showNoResultText()
                    hideProgressIndicator()
                }
                is FavoriteMoviesUiState.AdLoaded -> {
                    hideProgressIndicator()
                    banner.loadImageFromUrl(it.ad.image)
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
            context,
            string,
            Toast.LENGTH_LONG
        ).show()
    }

    val viewModel: FavoriteViewModel by viewModels()

    lateinit var moviesAdapter : MoviesAdapter
    private fun setUpRecyclerView() {
        with(recyclerView){
            adapter = moviesAdapter
            layoutManager = androidx.recyclerview.widget.StaggeredGridLayoutManager(
                1,
                androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
            )
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
        viewModel.performAction(FavoriteMoviesUiAction.LoadFavorite)
    }


    fun addToFavorite(isFav: Boolean, id: Int) {
        viewModel.performAction(FavoriteMoviesUiAction.AddToFav(isFav,id))
    }

}