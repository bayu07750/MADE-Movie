package com.bayu07750.mademovie.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bayu07750.mademovie.core.domain.model.Movie
import com.bayu07750.mademovie.databinding.ItemMovieGridBinding

class MovieGridAdapter(
    private val onItemClicked: (position: Int, movie: Movie) -> Unit,
) : ListAdapter<Movie, MovieGridViewHolder>(MoviePagingDataAdapter.diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieGridViewHolder {
        return MovieGridViewHolder(
            ItemMovieGridBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onItemClicked
        )
    }

    override fun onBindViewHolder(holder: MovieGridViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }
}