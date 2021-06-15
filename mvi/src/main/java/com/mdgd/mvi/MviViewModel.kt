package com.mdgd.mvi

import androidx.lifecycle.*
import com.mdgd.mvi.fragments.FragmentContract
import com.mdgd.mvi.states.ScreenState

abstract class MviViewModel<V, S : ScreenState<V, S>, A> : ViewModel(),
    FragmentContract.ViewModel<S, A> {

    // TODO: use StateFlow: val uiState: StateFlow<LatestNewsUiState> = _uiState ?
    private val stateHolder = MutableLiveData<S>()
    private val actionHolder = MutableLiveData<A>()

    override fun getStateObservable(): LiveData<S> {
        return stateHolder
    }

    override fun getActionObservable(): LiveData<A> {
        return actionHolder
    }

    protected fun setState(state: S) {
        if (stateHolder.value != null) {
            state.merge(stateHolder.value as S)
        }
        stateHolder.value = state
    }

    protected fun getState(): S? = stateHolder.value

    protected fun setAction(action: A) {
        actionHolder.value = action
    }

    protected fun getAction(): A? = actionHolder.value

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    protected open fun onAny(owner: LifecycleOwner?, event: Lifecycle.Event) {
    }
}
