package com.fappslab.viacep.arch.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class GenericViewHolder<VB : ViewBinding, T> internal constructor(
    private val binding: VB,
    private val bind: Bind<VB, T>,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: T, position: Int) = bind(binding, item, position)
}
