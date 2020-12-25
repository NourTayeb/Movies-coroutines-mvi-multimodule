package com.nourtayeb.movies_mvi.presentation.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.nourtayeb.ads.presentation.ui.interstitial.InterstitialAdActivity
import com.nourtayeb.movies_mvi.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {

    val viewModel: SplashViewModel by viewModels()
    lateinit var navController: NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        Handler().postDelayed({
            startActivity(Intent(requireActivity(), InterstitialAdActivity::class.java))
            viewModel.performAction(SplashUiAction.Login(2))
        },3000)
        viewModel.state.observe(viewLifecycleOwner,getStateObserver())
    }

    fun getStateObserver(): Observer<SplashMoviesUiState> {
        return Observer {
            when(it) {
                is SplashMoviesUiState.Success -> startMovies()
                is SplashMoviesUiState.Loading -> log("Loading")
                is SplashMoviesUiState.Failed -> showToast(getString(R.string.authorization_failed))
                is SplashMoviesUiState.Exception ->  showToast(getString(R.string.some_error_occurred))
            }
        }
    }

    fun log(string:String){
        Log.e("log",string)
    }
    fun showToast(string:String) {
        Toast.makeText(context,string, Toast.LENGTH_LONG).show()
    }

    fun startMovies(){
        navController.navigate(R.id.action_splashFragment_to_searchMoviesFragment)
    }
}