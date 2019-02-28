package com.cyrillrx.starwarsapi.adapter

import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cyrillrx.logger.Logger
import com.cyrillrx.starwarsapi.R
import com.cyrillrx.starwarsapi.inflate

/**
 * @author Cyril Leroux
 *         Created on 06/04/2018.
 */
class TitleViewHolder(parent: ViewGroup)
    : RecyclerView.ViewHolder(parent.inflate(R.layout.item_title)) {

    private var tvTitle: TextView = itemView.findViewById(R.id.tv_title)

    fun bind(item: Any?) {
        when (item) {
            is String -> tvTitle.text = item
            else -> Logger.error(TAG, "Type $item not supported")
        }
    }

    companion object {
        private val TAG = TitleViewHolder::class.java.simpleName
    }
}
