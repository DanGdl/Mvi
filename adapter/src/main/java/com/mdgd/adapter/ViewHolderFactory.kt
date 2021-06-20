package com.mdgd.adapter

interface ViewHolderFactory<PARAM, ITEM> {
    fun createViewHolder(params: PARAM): AbstractVH<ITEM>
}
