package com.nourtayeb.movies_mvi.presentation.common.delegates

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle

interface EventBusSubscriberDelegate  {
    fun attachEventBus(lifecycle: Lifecycle,activity: AppCompatActivity)
    fun attachEventBus(lifecycle: Lifecycle,fragment: Fragment)
}