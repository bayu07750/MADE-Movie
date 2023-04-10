package com.bayu07750.mademovie.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bayu07750.mademovie.core.domain.model.Genre
import com.bayu07750.mademovie.databinding.ItemGenreBinding

class GenreAdapter(
    private val onItemClicked: (genre: Genre) -> Unit,
) : ListAdapter<Genre, GenreAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(
        private val binding: ItemGenreBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(genre: Genre) {
            binding.genre = genre
            binding.root.setOnClickListener {
                onItemClicked.invoke(genre)
            }
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Genre>() {
            override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemGenreBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }
}