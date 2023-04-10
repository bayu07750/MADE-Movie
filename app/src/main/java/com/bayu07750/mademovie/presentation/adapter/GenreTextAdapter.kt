package com.bayu07750.mademovie.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bayu07750.mademovie.core.domain.model.Genre
import com.bayu07750.mademovie.databinding.ItemGenreTextBinding

class GenreTextAdapter(
    private val genres: List<Genre>,
    private val onItemClicked: (Genre) -> Unit
) : RecyclerView.Adapter<GenreTextAdapter.ViewHolder>() {

    inner class ViewHolder(
        private val binding: ItemGenreTextBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(genre: Genre) {
            binding.genre = genre
            binding.root.setOnClickListener {
                onItemClicked.invoke(genre)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemGenreTextBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = genres.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(genres[position])
}