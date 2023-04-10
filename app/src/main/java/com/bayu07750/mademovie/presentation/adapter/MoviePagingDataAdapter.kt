package com.bayu07750.mademovie.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bayu07750.mademovie.core.domain.model.Movie
import com.bayu07750.mademovie.databinding.ItemMovieGridBinding

class MoviePagingDataAdapter(
    private val onItemClicked: (position: Int, movie: Movie) -> Unit,
) : PagingDataAdapter<Movie, MovieGridViewHolder>(diffUtil) {

    override fun onBindViewHolder(holder: MovieGridViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieGridViewHolder {
        return MovieGridViewHolder(
            ItemMovieGridBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onItemClicked = onItemClicked,
        )
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem
        }
    }
}