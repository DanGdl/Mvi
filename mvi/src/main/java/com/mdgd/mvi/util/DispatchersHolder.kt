package com.mdgd.mvi.util

import kotlinx.coroutines.CoroutineDispatcher

interface DispatchersHolder {
    fun getMain(): CoroutineDispatcher

    fun getIO(): CoroutineDispatcher
}
