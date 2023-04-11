package com.bayu07750.mademovie.presentation.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bayu07750.mademovie.MainGraphDirections
import com.bayu07750.mademovie.R
import com.bayu07750.mademovie.databinding.FragmentDetailBinding
import com.bayu07750.mademovie.presentation.adapter.GenreTextAdapter
import com.bayu07750.mademovie.presentation.adapter.MovieCastAdapter
import com.bayu07750.mademovie.presentation.base.BaseFragment
import com.bayu07750.mademovie.presentation.discovery.DiscoveryType
import com.bayu07750.mademovie.presentation.util.extension.updatePaddingWithInsets
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : BaseFragment<FragmentDetailBinding>(R.layout.fragment_detail) {

    private val args: DetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by viewModel()

    private lateinit var genreTextAdapter: GenreTextAdapter
    private lateinit var castAdapter: MovieCastAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        observe()
        actions()
    }

    override fun updatePaddingWithInsets() {
        binding.root.updatePaddingWithInsets()
    }

    override fun initView() {
        super.initView()

        castAdapter = MovieCastAdapter()
        binding.rvCasts.adapter = castAdapter
    }

    override fun observe() {
        super.observe()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect { state ->
                        binding.isLoading = state.isLoading
                        binding.isError = state.isError
                        if (state.data != null) {
                            binding.movieDetail = state.data

                            binding.rvGenres.isVisible = state.data.genres.isNotEmpty()
                            binding.rvCasts.isVisible = state.data.casts.isNotEmpty()

                            castAdapter.submitList(state.data.casts)

                            genreTextAdapter = GenreTextAdapter(state.data.genres) { genre ->
                                findNavController().navigate(
                                    MainGraphDirections.actionGlobalDiscoveryGraph(
                                        title = genre.name,
                                        type = DiscoveryType.GENRE,
                                        genre = genre,
                                    )
                                )
                            }
                            binding.rvGenres.adapter = genreTextAdapter
                        }
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

        binding.lError.btnRetry.setOnClickListener {
            viewModel.getDetailMovie(args.movie.id)
        }

        binding.btnBookmark.setOnClickListener {
            viewModel.uiState.value.data ?: run {
                Toast.makeText(requireContext(), getString(R.string.no_data), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.bookmarkMovie(viewModel.uiState.value.data!!)
        }
    }
}