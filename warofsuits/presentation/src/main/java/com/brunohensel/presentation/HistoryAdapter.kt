package com.brunohensel.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.brunohensel.getDrawableFromRes
import com.brunohensel.model.Hand
import com.brunohensel.presentation.databinding.ItemHistoryBinding
import com.brunohensel.presentation.HistoryAdapter.HistoryViewHolder

class HistoryAdapter : ListAdapter<Hand, HistoryViewHolder>(HistoryAdapter) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class HistoryViewHolder private constructor(private val  binding: ItemHistoryBinding) : ViewHolder(binding.root) {

        fun bind(data: Hand){
            with(binding){
                txtHistoryWinnerName.text = data.winner
                txtHistoryLoserName.text = data.loser
                imgCardPlayerOne.setImageDrawable(root.context.getDrawableFromRes(data.playedHands.first.front))
                imgCardPlayerTwo.setImageDrawable(root.context.getDrawableFromRes(data.playedHands.second.front))
            }
        }

        companion object {
            fun from(parent: ViewGroup): HistoryViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = ItemHistoryBinding.inflate(layoutInflater, parent, false)
                return HistoryViewHolder(view)
            }
        }
    }

    private companion object : DiffUtil.ItemCallback<Hand>() {
        override fun areItemsTheSame(oldItem: Hand, newItem: Hand): Boolean {
            return oldItem.playedHands == newItem.playedHands
        }

        override fun areContentsTheSame(oldItem: Hand, newItem: Hand): Boolean {
            return oldItem == newItem
        }
    }
}