package com.fappslab.viacep.arch.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding

internal typealias Bind<T> = (ViewBinding, T) -> Unit

class GenericAdapter<T : Any>(
    private val inflateBlock: (ViewGroup) -> ViewBinding,
    private val bindBlock: Bind<T>
) : ListAdapter<T, GenericViewHolder<T>>(GenericDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder<T> =
        GenericViewHolder(inflateBlock(parent), bindBlock)

    override fun onBindViewHolder(holder: GenericViewHolder<T>, position: Int) {
        getItem(position)?.also(holder::bind)
    }
}
