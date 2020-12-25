package com.nourtayeb.ads.presentation.ui.interstitial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.coroutineScope
import com.nourtayeb.ads.R
import com.nourtayeb.movies_mvi.common.utility.loadImageFromUrl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_interstitial_ad.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow


@AndroidEntryPoint
class InterstitialAdActivity : AppCompatActivity() {

    val viewModel: InterstitialAdViewModel by viewModels()
    val channel = Channel<InterstitialAdUiAction>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interstitial_ad)
        close.setOnClickListener { finish() }
        setupClickers()
        val flow: Flow<InterstitialAdUiAction> = channel.consumeAsFlow()
        viewModel.getState(viewModel.reduce(flow)).observe(this, getStateObserver())
    }

    override fun onDestroy() {
        super.onDestroy()
        channel.close()
    }


    fun getStateObserver(): Observer<in InterstitialAdUiState> {
        return Observer {
            //Log.e("ThreadObserve", Thread.currentThread().name)
            when (it) {
                is InterstitialAdUiState.ImageAdLoaded -> {
                    showImageInImageview(image, it.ad.image)
                    hideLoading()
                }
                is InterstitialAdUiState.StringAdLoaded -> {
                    showTextInTextView(textAd,it.ad.name)
                    hideLoading()
                }
                is InterstitialAdUiState.Loading -> {
                    showLoading()
                }
                is InterstitialAdUiState.Exception -> {
                    showToast(it.exception.toString())
                    hideLoading()
                }
            }
        }
    }

    fun showTextInTextView(textView: TextView, string: String) {
        textView.text = string
    }

    fun setupClickers() {
        loadImageAds.setOnClickListener {
            lifecycle.coroutineScope.launchWhenResumed {
                channel.send(InterstitialAdUiAction.ShowImageAds)
            }
        }
        loadTextAds.setOnClickListener {
            lifecycle.coroutineScope.launchWhenResumed {
                channel.send(InterstitialAdUiAction.ShowTextAds)
            }
        }
    }

    fun showImageInImageview(imageView: ImageView, url: String) {
        imageView.loadImageFromUrl(url)
    }

    fun showToast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }

    fun showLoading() {
        loading.visibility = View.VISIBLE
    }

    fun hideLoading() {
        loading.visibility = View.GONE
    }
}