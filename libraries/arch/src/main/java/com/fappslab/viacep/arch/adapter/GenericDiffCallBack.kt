package com.fappslab.viacep.arch.adapter

import androidx.recyclerview.widget.DiffUtil

class GenericDiffCallBack<T : Any> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
        areItemsTheSame(oldItem, newItem)
}
