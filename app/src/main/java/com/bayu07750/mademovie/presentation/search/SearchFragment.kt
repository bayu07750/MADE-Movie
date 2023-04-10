package com.bayu07750.mademovie.presentation.search

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.bayu07750.mademovie.MainGraphDirections
import com.bayu07750.mademovie.R
import com.bayu07750.mademovie.databinding.FragmentSearchBinding
import com.bayu07750.mademovie.presentation.adapter.LoadStateAdapter
import com.bayu07750.mademovie.presentation.adapter.MoviePagingDataAdapter
import com.bayu07750.mademovie.presentation.base.BaseFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private val viewModel: SearchViewModel by viewModel()

    private lateinit var adapter: MoviePagingDataAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        observe()
        actions()
    }

    override fun initView() {
        super.initView()

        adapter = MoviePagingDataAdapter { _, movie ->
            findNavController().navigate(
                MainGraphDirections.actionGlobalDetailGraph(movie)
            )
        }
        binding.rvMovies.adapter = adapter.withLoadStateHeaderAndFooter(
            header = LoadStateAdapter(adapter::retry),
            footer = LoadStateAdapter(adapter::retry),
        )
    }

    override fun observe() {
        super.observe()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.movies.collectLatest {
                adapter.submitData(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    adapter.loadStateFlow.collectLatest { loadState ->
                        binding.isLoading = loadState.source.refresh is LoadState.Loading
                        binding.isError = loadState.source.refresh is LoadState.Error
                    }
                }
            }
        }
    }

    override fun actions() {
        super.actions()

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.etSearch.doAfterTextChanged {
            searchMovie()
        }

        binding.lError.btnRetry.setOnClickListener {
            searchMovie()
        }
    }

    private fun searchMovie() {
        viewModel.setQuery(binding.etSearch.text.toString())
    }
}