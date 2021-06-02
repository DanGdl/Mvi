package com.mdgd.mvi.states

interface ScreenAction<T> {
    fun visit(screen: T)
}
