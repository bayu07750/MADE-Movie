package com.bayu07750.mademovie.presentation.category

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bayu07750.mademovie.MainGraphDirections
import com.bayu07750.mademovie.R
import com.bayu07750.mademovie.databinding.FragmentCategoryBinding
import com.bayu07750.mademovie.presentation.adapter.GenreAdapter
import com.bayu07750.mademovie.presentation.base.BaseFragment
import com.bayu07750.mademovie.presentation.discovery.DiscoveryType
import com.bayu07750.mademovie.presentation.util.extension.setupToolbar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoryFragment : BaseFragment<FragmentCategoryBinding>(R.layout.fragment_category) {

    private val viewModel: CategoryViewModel by viewModel()

    private lateinit var genreAdapter: GenreAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        observe()
    }

    override fun onDestroyView() {
        binding.rvGenre.adapter = null
        super.onDestroyView()
    }

    override fun initView() {
        super.initView()

        binding.lToolbar.toolbar.setupToolbar(
            title = getString(R.string.genre)
        )

        genreAdapter = GenreAdapter { genre ->
            findNavController().navigate(
                MainGraphDirections.actionGlobalDiscoveryGraph(
                    title = genre.name,
                    type = DiscoveryType.GENRE,
                    timeWindow = "",
                    genre = genre,
                )
            )
        }
        binding.rvGenre.adapter = genreAdapter
    }

    override fun observe() {
        super.observe()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.movieGenres.collect { result ->
                        genreAdapter.submitList(result)
                    }
                }
            }
        }
    }
}