package com.mdgd.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

abstract class AbstractListAdapter<T : ViewHolderDataItem, VH_PARAMS, CLICK>
    (diffCallback: DiffUtil.ItemCallback<T>) : ListAdapter<T, AbstractVH<T>>(diffCallback) {

    private val factories: Map<Int, ViewHolderFactory<VH_PARAMS, T>> = createViewHolderFactories()
    protected val clicksFlow =
        MutableSharedFlow<CLICK>(onBufferOverflow = BufferOverflow.DROP_OLDEST)

    open fun getClicksFlow(): Flow<CLICK> {
        return clicksFlow
    }

    abstract fun createViewHolderFactories(): Map<Int, ViewHolderFactory<VH_PARAMS, T>>

    abstract fun getViewHolderParams(parent: ViewGroup, viewType: Int): VH_PARAMS

    override fun getItemViewType(position: Int): Int {
        return getItem(position).getViewHolderType(position)
    }

    protected fun inflate(parent: ViewGroup, layoutResId: Int): View {
        return LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
    }

    override fun onViewAttachedToWindow(holder: AbstractVH<T>) {
        super.onViewAttachedToWindow(holder)
        holder.onViewAttachedToWindow()
    }

    override fun onViewDetachedFromWindow(holder: AbstractVH<T>) {
        holder.onViewDetachedFromWindow()
        super.onViewDetachedFromWindow(holder)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractVH<T> {
        return factories[viewType]?.createViewHolder(getViewHolderParams(parent, viewType))
            ?: factories[getDefaultViewType()]?.createViewHolder(
                getViewHolderParams(parent, viewType)
            )!!
    }

    /**
     * exist for NewsFlashAdapter, where viewType is item's position in list
     */
    protected open fun getDefaultViewType() = 0

    override fun onBindViewHolder(holder: AbstractVH<T>, position: Int) {
        holder.onBindViewHolder(getItem(position))
    }
}
