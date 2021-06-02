package com.mdgd.mvi.fragments

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData

class FragmentContract {
    interface ViewModel<S, A> : LifecycleObserver {
        fun getStateObservable(): LiveData<S>
        fun getActionObservable(): LiveData<A>
    }

    interface View
    interface Host
}
