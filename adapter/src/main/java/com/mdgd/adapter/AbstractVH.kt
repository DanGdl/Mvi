package com.mdgd.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class AbstractVH<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    protected var model: T? = null

    open fun onViewAttachedToWindow() {}
    open fun onViewDetachedFromWindow() {}

    fun onBindViewHolder(item: T) {
        if (model == item) {
            return
        }
        model = item
        bind(item)
    }

    abstract fun bind(item: T)

    protected fun hasModel() = model != null
}
