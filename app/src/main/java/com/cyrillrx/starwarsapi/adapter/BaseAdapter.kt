package com.cyrillrx.starwarsapi.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.cyrillrx.logger.Logger

/**
 * @author Cyril Leroux
 *         Created on 06/04/2018.
 */
class BaseAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items: ArrayList<Any> = ArrayList()

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = when (items[position]) {
        is String -> TYPE_TITLE
        else -> TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        TYPE_TITLE -> TitleViewHolder(parent)
        TYPE_ITEM -> LabelViewHolder(parent)
        else -> LabelViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TitleViewHolder -> holder.bind(items[position])
            is LabelViewHolder -> holder.bind(items[position])
            else -> Logger.error(TAG, "Type $holder not supported")
        }
    }

    fun add(title: CharSequence?) {
        if (title == null) return

        items.add(title)
        notifyItemInserted(items.size - 1)
    }

    fun addAll(newItem: Collection<Any>) {

        val startPos = items.size
        items.addAll(newItem)
        notifyItemRangeInserted(startPos, newItem.size)
    }

    companion object {
        private const val TYPE_ITEM = 0
        private const val TYPE_TITLE = 1

        private val TAG = BaseAdapter::class.java.simpleName
    }
}