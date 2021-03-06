package com.nourtayeb.movies_mvi.presentation.common.delegates

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import org.greenrobot.eventbus.EventBus

class EventBusSubscriberImp:EventBusSubscriberDelegate  {
    override fun attachEventBus(lifecycle: Lifecycle, activity: AppCompatActivity) {
        lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_START)
            fun subscribe() {
                if(!EventBus.getDefault().isRegistered(activity)) {
                    EventBus.getDefault().register(activity)
                }
            }
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun unsubscribe() {
                EventBus.getDefault().unregister(activity)
            }
        })
    }

    override fun attachEventBus(lifecycle: Lifecycle, fragment: Fragment) {
        lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_START)
            fun subscribe() {
                if(!EventBus.getDefault().isRegistered(fragment)) {
                    EventBus.getDefault().register(fragment)
                }
            }
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun unsubscribe() {
                EventBus.getDefault().unregister(fragment)
            }
        })
    }
}