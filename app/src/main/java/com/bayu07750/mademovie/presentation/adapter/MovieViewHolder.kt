package com.bayu07750.mademovie.presentation.adapter

import com.bayu07750.mademovie.core.domain.model.Movie
import com.bayu07750.mademovie.databinding.ItemMovieBinding

class MovieViewHolder(
    private val binding: ItemMovieBinding,
    private val onItemClicked: (position: Int, movie: Movie) -> Unit,
) : androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie) {
        binding.movie = movie

        binding.root.setOnClickListener {
            onItemClicked.invoke(bindingAdapterPosition, movie)
        }
    }
}