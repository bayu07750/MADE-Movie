package com.bayu07750.mademovie.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bayu07750.mademovie.MainGraphDirections
import com.bayu07750.mademovie.R
import com.bayu07750.mademovie.core.data.cons.Cons
import com.bayu07750.mademovie.databinding.FragmentHomeBinding
import com.bayu07750.mademovie.presentation.MainActivity
import com.bayu07750.mademovie.presentation.adapter.MovieAdapter
import com.bayu07750.mademovie.presentation.base.BaseFragment
import com.bayu07750.mademovie.presentation.discovery.DiscoveryType
import com.bayu07750.mademovie.presentation.util.power_menu.LanguagePowerMenuFactory
import com.bayu07750.mademovie.presentation.util.power_menu.TimeWindowPowerMenuFactory
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModel()

    private lateinit var trendingMovieAdapter: MovieAdapter
    private lateinit var popularMovieAdapter: MovieAdapter
    private lateinit var nowPlayingMovieAdapter: MovieAdapter

    private val timeWindowPowerMenu by lazy {
        context?.let { ctx ->
            TimeWindowPowerMenuFactory.create(ctx, viewLifecycleOwner) { _, item ->
                val selectedTimeWindow = when (item.title.toString()) {
                    getString(com.bayu07750.mademovie.core.R.string.this_week) -> TrendingTimeWindow.WEEK
                    else -> TrendingTimeWindow.DAY
                }
                viewModel.setTrendingTimeWindow(selectedTimeWindow)
            }
        }
    }

    private val languagePowerMenu by lazy {
        context?.let { ctx ->
            LanguagePowerMenuFactory.create(ctx, viewLifecycleOwner) { _, item ->
                val selectedLang = when (item.title) {
                    getString(com.bayu07750.mademovie.core.R.string.language_in) -> Cons.Language.ID
                    getString(com.bayu07750.mademovie.core.R.string.language_ja) -> Cons.Language.JA
                    getString(com.bayu07750.mademovie.core.R.string.language_es) -> Cons.Language.ES
                    getString(com.bayu07750.mademovie.core.R.string.language_ar) -> Cons.Language.AR
                    else -> Cons.Language.EN
                }
                viewModel.setLanguage(selectedLang)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        observe()
        actions()
    }

    override fun onDestroyView() {
        binding.rvTrendingMovie.adapter = null
        binding.rvPopularMovie.adapter = null
        binding.rvNowPlaying.adapter = null
        super.onDestroyView()
    }

    override fun initView() {
        super.initView()
        trendingMovieAdapter = MovieAdapter { _, movie ->
            findNavController().navigate(MainGraphDirections.actionGlobalDetailGraph(movie))
        }
        binding.rvTrendingMovie.adapter = trendingMovieAdapter

        popularMovieAdapter = MovieAdapter { _, movie ->
            findNavController().navigate(MainGraphDirections.actionGlobalDetailGraph(movie))
        }
        binding.rvPopularMovie.adapter = popularMovieAdapter

        nowPlayingMovieAdapter = MovieAdapter { _, movie ->
            findNavController().navigate(MainGraphDirections.actionGlobalDetailGraph(movie))
        }
        binding.rvNowPlaying.adapter = nowPlayingMovieAdapter
    }

    override fun observe() {
        super.observe()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect { state ->
                        binding.isLoading = state.isLoading
                        binding.isError = state.isError

                        if (state.isSuccess) {
                            trendingMovieAdapter.submitList(state.trendingMovies)
                            popularMovieAdapter.submitList(state.popularMovies)
                            nowPlayingMovieAdapter.submitList(state.nowPlayingMovies)

                            binding.rvTrendingMovie.post {
                                binding.rvTrendingMovie.scrollToPosition(0)
                            }
                        }
                    }
                }

                launch {
                    viewModel.trendingTimeWindow.collect { result ->
                        binding.btnTrendingTimeWindow.text = when (result) {
                            TrendingTimeWindow.WEEK -> getString(com.bayu07750.mademovie.core.R.string.this_week)
                            TrendingTimeWindow.DAY -> getString(com.bayu07750.mademovie.core.R.string.today)
                        }
                    }
                }

                launch {
                    viewModel.languageState.collect { result ->
                        if (result) {
                            Snackbar.make(
                                binding.root,
                                getString(com.bayu07750.mademovie.core.R.string.success_change_language),
                                Snackbar.LENGTH_SHORT
                            )
                                .addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                                    override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                                        super.onDismissed(transientBottomBar, event)
                                        context?.let { ctx ->
                                            Intent(ctx, MainActivity::class.java).apply {
                                                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                                ctx.startActivity(this)
                                            }
                                        }
                                    }
                                })
                                .show()
                        }
                    }
                }
            }
        }
    }

    override fun actions() {
        super.actions()

        binding.ivUser.setOnClickListener {
            languagePowerMenu?.showAsDropDown(it)
        }

        binding.lError.btnRetry.setOnClickListener {
            viewModel.getData()
        }

        binding.btnTrendingTimeWindow.setOnClickListener {
            timeWindowPowerMenu?.showAsDropDown(it)
        }

        binding.btnSearch.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToSearchFragment()
            )
        }

        binding.btnTrendingMore.setOnClickListener {
            navigateToMovieDiscovery(
                title = getString(com.bayu07750.mademovie.core.R.string.trending),
                type = DiscoveryType.TRENDING,
                timeWindow = viewModel.trendingTimeWindow.value.timeWindow,
            )
        }

        binding.btnPopularMore.setOnClickListener {
            navigateToMovieDiscovery(
                title = getString(com.bayu07750.mademovie.core.R.string.popular),
                type = DiscoveryType.POPULAR,
                timeWindow = "",
            )
        }

        binding.btnNowPlayingMore.setOnClickListener {
            navigateToMovieDiscovery(
                title = getString(com.bayu07750.mademovie.core.R.string.now_playing),
                type = DiscoveryType.NOW_PLAYING,
                timeWindow = ""
            )
        }
    }

    private fun navigateToMovieDiscovery(
        title: String,
        type: DiscoveryType,
        timeWindow: String,
    ) {
        findNavController().navigate(
            MainGraphDirections.actionGlobalDiscoveryGraph(
                title = title,
                type = type,
                timeWindow = timeWindow,
                genre = null
            )
        )
    }
}