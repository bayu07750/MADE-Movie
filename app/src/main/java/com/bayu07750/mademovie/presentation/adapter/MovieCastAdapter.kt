package com.bayu07750.mademovie.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bayu07750.mademovie.core.domain.model.Cast
import com.bayu07750.mademovie.databinding.ItemMovieCastBinding

class MovieCastAdapter : ListAdapter<Cast, MovieCastAdapter.ViewHolder>(diffUtil) {

    class ViewHolder(private val binding: ItemMovieCastBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(cast: Cast) {
            binding.cast = cast
        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<Cast>() {
            override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMovieCastBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}