package com.fappslab.viacep.arch.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding

internal typealias Bind<VB, T> = (VB, T) -> Unit

class GenericAdapter<VB : ViewBinding, T : Any>(
    private val inflateBlock: (ViewGroup) -> VB,
    private val bindBlock: Bind<VB, T>,
) : ListAdapter<T, GenericViewHolder<VB, T>>(GenericDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder<VB, T> =
        GenericViewHolder(inflateBlock(parent), bindBlock)

    override fun onBindViewHolder(holder: GenericViewHolder<VB, T>, position: Int) {
        getItem(position)?.also(holder::bind)
    }
}

