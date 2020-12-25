package com.nourtayeb.movies_mvi.presentation.ui.searchmovies

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.nourtayeb.movies_mvi.R
import com.nourtayeb.movies_mvi.common.utility.OnQueryTextListener
import com.nourtayeb.movies_mvi.common.utility.shareBitmap
import com.nourtayeb.movies_mvi.presentation.common.adapter.MoviesAdapter
import com.nourtayeb.movies_mvi.presentation.common.delegates.EventBusSubscriberDelegate
import com.nourtayeb.movies_mvi.presentation.common.delegates.EventBusSubscriberImp
import com.nourtayeb.movies_mvi.presentation.common.messages.FavoriteStatusChanged
import com.nourtayeb.movies_mvi.presentation.ui.favorite.CurrentSearch
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search_movies.*
import kotlinx.android.synthetic.main.fragment_search_movies.favorite
import kotlinx.android.synthetic.main.fragment_search_movies.searchView
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

@AndroidEntryPoint
class SearchMoviesFragment : Fragment()
    , EventBusSubscriberDelegate by EventBusSubscriberImp()
{
    lateinit var navController:NavController
    val viewModel: SearchMovieViewModel by viewModels()
    lateinit var moviesAdapter : MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_movies, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        moviesAdapter = MoviesAdapter()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        favorite.setOnClickListener{
            val bundle= bundleOf("currentSearch" to CurrentSearch(searchView.query.toString())            )
            navController.navigate(R.id.action_searchMoviesFragment_to_favoriteFragment,bundle)
        }
        attachEventBus(lifecycle, this)
        setUpRecyclerViewAdapters()
        searchView.OnQueryTextListener {
            searchAction(it)
        }
        viewModel.state.observe(viewLifecycleOwner, getStateObserver())
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
            requireContext(),
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
            layoutManager = androidx.recyclerview.widget.StaggeredGridLayoutManager(
                1,
                androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
            )
        }
        with(moviesAdapter) {
            onFavoriteClicked = { isFav, id -> addToFavoriteAction(isFav, id) }
            onShareClicked = { shareBitmap(it) }
        }
    }

    fun searchAction(key: String, firstTime: Boolean = true) {
        viewModel.performAction(SearchMoviesUiAction.Search(key, firstTime))

    }

    fun addToFavoriteAction(isFav: Boolean, id: Int) {
        viewModel.performAction(SearchMoviesUiAction.AddToFav(isFav, id))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if(searchView!=null) {
            outState.putString("key", searchView.query.toString())
        }
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.getString("key")?.let {
            if(!it.equals("")&&(moviesAdapter.itemCount==0)) {
                searchAction(it, false)
            }
        }
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageReceived(event: FavoriteStatusChanged) {
        moviesAdapter.changeItemIsFav(event.isFav, event.id)
    }


}