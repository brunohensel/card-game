package com.brunohensel.cardgame.home.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.brunohensel.cardgame.databinding.ItemHomeBinding
import com.brunohensel.cardgame.home.domain.module.AvailableGame
import com.brunohensel.cardgame.home.domain.module.GameType

class HomeAdapter(private val onClick: (GameType) -> Unit) :
    ListAdapter<AvailableGame, HomeAdapter.GameViewHolder>(HomeAdapter) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        return GameViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }

    class GameViewHolder private constructor(
        private val binding: ItemHomeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: AvailableGame, onClick: (GameType) -> Unit) {
            with(binding) {
                imgGameIcon.setOnClickListener { onClick(data.type) }
                txtGameName.text = data.name
                imgGameIcon.alpha = if (data.available) 1f else 0.3f
                imgGameIcon.setImageResource( data.icon)
            }
        }

        companion object {
            fun from(parent: ViewGroup): GameViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = ItemHomeBinding.inflate(layoutInflater, parent, false)
                return GameViewHolder(view)
            }
        }
    }

    private companion object : DiffUtil.ItemCallback<AvailableGame>() {

        override fun areItemsTheSame(oldItem: AvailableGame, newItem: AvailableGame): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: AvailableGame, newItem: AvailableGame): Boolean {
            return oldItem == newItem
        }
    }
}