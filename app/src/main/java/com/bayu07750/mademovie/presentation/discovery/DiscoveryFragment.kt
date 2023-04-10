package com.bayu07750.mademovie.presentation.discovery

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import com.bayu07750.mademovie.MainGraphDirections
import com.bayu07750.mademovie.R
import com.bayu07750.mademovie.databinding.FragmentDiscoveryBinding
import com.bayu07750.mademovie.presentation.adapter.LoadStateAdapter
import com.bayu07750.mademovie.presentation.adapter.MoviePagingDataAdapter
import com.bayu07750.mademovie.presentation.base.BaseFragment
import com.bayu07750.mademovie.presentation.util.extension.setupToolbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DiscoveryFragment : BaseFragment<FragmentDiscoveryBinding>(R.layout.fragment_discovery) {

    private val args: DiscoveryFragmentArgs by navArgs()
    private val viewModel: DiscoveryViewModel by viewModel()

    private lateinit var adapter: MoviePagingDataAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        observe()
        actions()
    }

    override fun onDestroyView() {
        binding.rvMovies.adapter = null
        super.onDestroyView()
    }

    override fun initView() {
        super.initView()

        binding.lToolbar.toolbar.setupToolbar(
            title = args.title,
            titleCenter = true,
            iconBack = true,
        )

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
                    adapter.loadStateFlow.collect { loadState ->
                        binding.isLoading = loadState.source.refresh is LoadState.Loading
                        binding.isError = loadState.source.refresh is LoadState.Error
                    }
                }
            }
        }
    }

    override fun actions() {
        super.actions()

        binding.lToolbar.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.lError.btnRetry.setOnClickListener {
            adapter.refresh()
        }
    }
}