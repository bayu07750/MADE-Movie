package com.bayu07750.mademovie.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.bayu07750.mademovie.databinding.ItemLoadStateBinding

class LoadStateAdapter(
    private val retry: () -> Unit,
) : androidx.paging.LoadStateAdapter<LoadStateAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        return ViewHolder(
            ItemLoadStateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class ViewHolder(
        private val binding: ItemLoadStateBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnRetry.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) {
            binding.apply {
                pbLoading.isVisible = loadState is LoadState.Loading
                tvError.isVisible = loadState is LoadState.Error
                btnRetry.isVisible = loadState is LoadState.Error
            }
        }
    }
}