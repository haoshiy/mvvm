package com.mvvm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.mvvm.adapter.listener.OnItemClickListener
import com.mvvm.adapter.listener.OnItemLongClickListener

abstract class BaseNormalAdapter<VB : ViewBinding, D> : RecyclerView.Adapter<ViewHolder<VB>>() {

    var data = ArrayList<D>()

    protected var itemClickListener: OnItemClickListener<D>? = null
    protected var itemLongClickListener: OnItemLongClickListener<D>? = null

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<VB> {
        return ViewHolder(getViewBinding(LayoutInflater.from(parent.context), parent))
    }

    override fun onBindViewHolder(
        viewHolder: ViewHolder<VB>,
        position: Int,
        payloads: MutableList<Any>
    ) {
        itemClickListener?.apply {
            viewHolder.itemView.setOnClickListener {
                itemClicked(it, data[position], position)
            }
        }
        bindViewHolder(viewHolder, data[position], position, payloads)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder<VB>, position: Int) {

    }

    protected fun getItem(position: Int) = data[position]

    fun setOnItemClickListener(itemClickListener: OnItemClickListener<D>?) {
        this.itemClickListener = itemClickListener
    }

    fun setOnItemLongClickListener(itemLongClickListener: OnItemLongClickListener<D>?) {
        this.itemLongClickListener = itemLongClickListener
    }

    fun resetData(data: List<D>?) {
        this.data.clear()
        if (data != null && data.isNotEmpty()) {
            this.data.addAll(data)
        }
        notifyDataSetChanged()
    }

    fun addData(data: List<D>?) {
        if (data == null || data.isEmpty()) {
            return
        }

        val size = this.data.size
        this.data.addAll(data)
        notifyItemRangeInserted(size, data.size)
    }

    fun addItem(data: D) {
        val size = this.data.size
        this.data.add(data)
        notifyItemInserted(size)
    }

    fun clear() {
        this.data.clear()
    }

    abstract fun getViewBinding(layoutInflater: LayoutInflater, parent: ViewGroup): VB

    abstract fun bindViewHolder(
        viewHolder: ViewHolder<VB>,
        item: D,
        position: Int,
        payloads: MutableList<Any>
    )
}