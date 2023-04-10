package com.bayu07750.mademovie.utils

import androidx.recyclerview.widget.DiffUtil
import com.bayu07750.mademovie.core.domain.model.Movie

class MovieItemCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
        oldItem == newItem
}