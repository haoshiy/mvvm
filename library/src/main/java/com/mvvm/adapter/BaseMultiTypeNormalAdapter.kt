package com.mvvm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.collection.SparseArrayCompat
import androidx.viewbinding.ViewBinding
import com.mvvm.databinding.EmptyBinding

open class BaseMultiTypeNormalAdapter<D> :
    BaseNormalAdapter<ViewBinding, D>() {

    private val delegates: SparseArrayCompat<ItemViewDelegate<out ViewBinding, D>> by lazy { SparseArrayCompat() }

    fun addDelegate(delegate: ItemViewDelegate<out ViewBinding, D>) {
        val viewType = delegates.size()
        delegates.put(viewType, delegate)
    }

    override fun getItemViewType(position: Int): Int {
        val size = delegates.size()
        for (index in 0 until size) {
            val itemViewDelegate = delegates[index]
            if (true == itemViewDelegate?.isViewType(getItem(position)!!, position)) {
                return delegates.keyAt(index)
            }
        }
        throw IllegalArgumentException("No ItemViewDelegate added that matches position= $position in data source")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<ViewBinding> {
        return ViewHolder(
            delegates.get(viewType)!!.getViewBinding(LayoutInflater.from(parent.context), parent)
        )
    }

    override fun bindViewHolder(
        viewHolder: ViewHolder<ViewBinding>,
        item: D,
        position: Int,
        payloads: MutableList<Any>
    ) {
        delegates.get(viewHolder.itemViewType)?.bind(
            viewHolder,
            getItem(position)!!,
            position,
            payloads
        )
    }

    override fun getViewBinding(layoutInflater: LayoutInflater, parent: ViewGroup): ViewBinding {
        return EmptyBinding.inflate(layoutInflater)
    }
}