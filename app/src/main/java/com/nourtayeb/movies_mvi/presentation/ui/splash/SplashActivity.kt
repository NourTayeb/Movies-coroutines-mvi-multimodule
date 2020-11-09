package com.nourtayeb.movies_mvi.presentation.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.nourtayeb.movies_mvi.R
import com.nourtayeb.movies_mvi.presentation.ui.searchmovies.SearchMoviesActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity :AppCompatActivity(){

    val viewModel: SplashViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acttivity_splash)
        Handler().postDelayed({
            viewModel.performAction(SplashUiAction.Login(2)).observe(this,getStateObserver())
        },1500)
    }
    fun getStateObserver(): Observer<SplashMoviesUiState> {
        return Observer {
            when(it) {
                is SplashMoviesUiState.Success -> startMovies()
                is SplashMoviesUiState.Failed -> showToast(getString(R.string.authorization_failed))
                is SplashMoviesUiState.Exception ->  showToast(getString(R.string.some_error_occurred))
            }
        }
    }
    fun showToast(string:String){
        Toast.makeText(this,string, Toast.LENGTH_LONG).show()
    }

    fun startMovies(){
        startActivity(Intent(this,SearchMoviesActivity::class.java))
        finish()
    }
}