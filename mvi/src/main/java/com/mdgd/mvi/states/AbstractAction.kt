package com.mdgd.mvi.states

abstract class AbstractAction<T> : ScreenAction<T> {
    var isHandled = false

    override fun visit(screen: T) {
        if (!isHandled) {
            handle(screen);
            isHandled = true
        }
    }

    abstract fun handle(screen: T);
}