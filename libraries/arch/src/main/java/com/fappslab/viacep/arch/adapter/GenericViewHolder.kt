package com.fappslab.viacep.arch.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class GenericViewHolder<T> internal constructor(
    private val binding: ViewBinding,
    private val bind: Bind<T>
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: T) = bind(binding, item)
}
