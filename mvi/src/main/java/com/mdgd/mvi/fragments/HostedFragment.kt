package com.mdgd.mvi.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.mdgd.mvi.states.ScreenAction
import com.mdgd.mvi.states.ScreenState
import java.lang.reflect.ParameterizedType

abstract class HostedFragment<
        VIEW : FragmentContract.View,
        STATE : ScreenState<VIEW, STATE>,
        ACTION : ScreenAction<VIEW>,
        VIEW_MODEL : FragmentContract.ViewModel<STATE, ACTION>,
        HOST : FragmentContract.Host>
    : Fragment(), FragmentContract.View, Observer<STATE> {

    protected var model: VIEW_MODEL? = null
        private set

    protected var fragmentHost: HOST? = null
        private set

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // keep the call back
        fragmentHost = try {
            context as HOST
        } catch (e: Throwable) {
            val hostClassName = ((javaClass.genericSuperclass as ParameterizedType)
                .actualTypeArguments[1] as Class<*>).canonicalName
            throw RuntimeException(
                "Activity must implement " + hostClassName
                        + " to attach " + this.javaClass.simpleName, e
            )
        }
    }

    override fun onDetach() {
        super.onDetach()
        // release the call back
        fragmentHost = null
    }

    protected fun hasHost(): Boolean {
        return fragmentHost != null
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setModel(createModel())
        if (model != null) {
            lifecycle.addObserver(model!!)
        }
    }

    protected fun setModel(model: VIEW_MODEL) {
        this.model = model
    }

    protected abstract fun createModel(): VIEW_MODEL

    override fun onDestroy() {
        if (model != null) {
            lifecycle.removeObserver(model!!)
        }
        super.onDestroy()
    }


    override fun onStart() {
        super.onStart()
        model!!.getStateObservable().observe(this, this)
        model!!.getActionObservable().observe(this, { action ->
            action.visit(this as VIEW)
        })
    }

    override fun onStop() {
        if (model != null) {
            model!!.getActionObservable().removeObservers(this)
            model!!.getStateObservable().removeObservers(this)
        }
        super.onStop()
    }

    override fun onChanged(screenState: STATE) {
        screenState.visit(this as VIEW)
    }
}
