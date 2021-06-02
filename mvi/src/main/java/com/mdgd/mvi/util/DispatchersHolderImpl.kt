package com.mdgd.mvi.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DispatchersHolderImpl : DispatchersHolder {

    override fun getMain(): CoroutineDispatcher {
        return Dispatchers.Main
    }

    override fun getIO(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}
