package com.nourtayeb.movies_mvi.presentation.common.delegates

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle

interface EventBusSubscriberDelegate  {
    fun attachEventBus(lifecycle: Lifecycle,activity: AppCompatActivity)
}